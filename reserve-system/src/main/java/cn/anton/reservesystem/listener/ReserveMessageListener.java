package cn.anton.reservesystem.listener;

import cn.anton.commonpackage.common.constant.KafkaConstant;
import cn.anton.commonpackage.common.constant.ReserveConstant;
import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.entity.ReserveVisitAssociationEntity;
import cn.anton.reservesystem.entity.VisitEntity;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.VisitInfoRequest;
import cn.anton.reservesystem.response.ReserveProcessResponse;
import cn.anton.reservesystem.service.CatService;
import cn.anton.reservesystem.service.ReserveService;
import cn.anton.reservesystem.service.ReserveVisitAssociationService;
import cn.anton.reservesystem.service.VisitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/7 17:08
 */
@Component
public class ReserveMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ReserveMessageListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private CatService catService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private ReserveVisitAssociationService reserveVisitAssociationService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 将预约消息发送给管理员与后台
     * 这里可以设置一个auto通过的process, 由于本项目为作业没有author暂且不做
     * @param records
     */
    @KafkaListener(topics = {KafkaConstant.RESERVE_SYSTEM_TOPIC_NAME}, groupId = KafkaConstant.RESERVE_SYSTEM_GROUP_ID)
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(ConsumerRecords<String, String> records) {

        for (ConsumerRecord<String, String> record : records) {
            // 将json数据转成对象
            String reserveType = record.key().split("-")[0];
            String json = record.value();
            ReserveAppRequest request = null;
            Date date = new Date();

            try {
                request = objectMapper.readValue(json, ReserveAppRequest.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("字符串转实体类转换失败...");
            }

            if (request == null) {
                continue;
            }

            VisitInfoRequest visitInfo = null; // 被访人信息
            ReserveEntity reserveEntity = null; // 行人预约信息
            CatEntity catEntity = null; // 车辆预约信息
            ReserveProcessResponse resultProcess = null;

            // 将数据保存到mysql中  并返回被访人信息
            if (ReserveConstant.RESERVE_TYPE_PERSON.equals(reserveType)) {
                resultProcess = reserveService.savePersonReserve(request, date);
                visitInfo = resultProcess.getVisitInfoRequest();
                reserveEntity = resultProcess.getReserveEntity();
            } else {
                resultProcess = catService.savePersonReserve(request, date);
                visitInfo = resultProcess.getVisitInfoRequest();
                catEntity = resultProcess.getCatEntity();
            }

            // 行人情况下 被访人不是必填项
            VisitEntity visitEntity = null;
            if (!(ReserveConstant.RESERVE_TYPE_PERSON.equals(reserveType) && visitInfo == null)) {
                visitEntity = visitService.saveVisitInfo(visitInfo, date);

                reserveVisitAssociationService.saveAssociationInfo(
                        visitEntity.getVisitId(),
                        ReserveConstant.RESERVE_TYPE_PERSON.equals(reserveType) ? reserveEntity.getReserveId() : catEntity.getTabId(),
                        reserveType);
            }

            // 将数据保存到Redis中 并设置过期时间
            RedisKeyInfo redisKeyInfo = new RedisKeyInfo(resultProcess, reserveType);
            // key = 预约类型 + 预约id + 预约信息(车牌或手机) + 过期时间
            String key = redisKeyInfo.reserveType +
                    "-" +
                    redisKeyInfo.typeId +
                    "-" +
                    redisKeyInfo.reserveInfo +
                    "-" +
                    redisKeyInfo.ttl;

            redisTemplate.opsForValue()
                    .set(key, json, redisKeyInfo.ttl, TimeUnit.SECONDS);
            logger.info("预约信息上传redis成功.... 预约类型: {}, 预约id: {}", redisKeyInfo.reserveType, redisKeyInfo.typeId);

            /*
                行人自动审核
                    方式一: 通过人脸照 身份证id 姓名去第三方云中验证(推荐)安全度高
                    方式二: 通过人像识别算法  只要符合人脸均可通过
                ps: 这里建议, 为了提高效率批阅效率与解耦 发送到新的主题中, 让别的消费者进行消费
             */
        }
    }

    private static class RedisKeyInfo {
        long ttl;
        Long typeId; // 车辆id 或 行人id
        String reserveType;  // 预约类型
        String reserveInfo;  // 车牌号 或 手机号

        /**
         * 处理redis key需要的信息
         */
        RedisKeyInfo(ReserveProcessResponse resultProcess, String reserveType){
            CatEntity catEntity = resultProcess.getCatEntity();
            ReserveEntity reserveEntity = resultProcess.getReserveEntity();
            long rStartTime = -1L;
            long rEndTime = -1L;

            if (ReserveConstant.RESERVE_TYPE_PERSON.equals(reserveType)) {
                this.reserveInfo = reserveEntity.getReservePhone();
                this.typeId = reserveEntity.getReserveId();
                rStartTime = reserveEntity.getStartDatetime().getTime();
                rEndTime = reserveEntity.getEndDatetime().getTime();
            } else {
                this.reserveInfo = catEntity.getCatId();
                this.typeId = catEntity.getTabId();
                rStartTime = catEntity.getStartDatetime().getTime();
                rEndTime = catEntity.getEndDatetime().getTime();
            }
            this.reserveType = reserveType;
            this.ttl = (rEndTime - rStartTime) / 1000L;
        }

    }


}
