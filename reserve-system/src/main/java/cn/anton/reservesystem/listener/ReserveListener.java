package cn.anton.reservesystem.listener;

import cn.anton.commonpackage.common.constant.KafkaConstant;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.entity.VisitEntity;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.service.ReserveService;
import cn.anton.reservesystem.service.VisitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/7 17:08
 */
@Component
public class ReserveListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @KafkaListener(topics = {KafkaConstant.RESERVE_SYSTEM_TOPIC_NAME}, groupId = KafkaConstant.RESERVE_SYSTEM_GROUP_ID)
    public void onMessage(ConsumerRecords<String, String> records) {
        Iterator<ConsumerRecord<String, String>> iterator =
                records.iterator();
        while (iterator.hasNext()) {
            ConsumerRecord<String, String> record = iterator.next();
            // 将json数据转成对象
            String json = record.value();
            ReserveAppRequest request = null;
            try {
                request = objectMapper.readValue(json, ReserveAppRequest.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("字符串转实体类转换失败...");
            }
            if (request == null) {
                continue;
            }
            // 将数据保存到mysql中
            ReserveEntity reserveEntity = new ReserveEntity();
            VisitEntity visitEntity = new VisitEntity();
            BeanUtils.copyProperties(request, reserveEntity);
            BeanUtils.copyProperties(request.getVisitInfo(), visitEntity);
            Date date = new Date();
            reserveEntity.setUpdateDatetime(date);
            visitEntity.setUpdateDatetime(date);
            reserveService.save(reserveEntity);
            visitService.save(visitEntity);

            // TODO 联表

            // 将数据保存到Redis中 并设置过期时间
            // key = reserveEntity.id - reserveEntity-phone - reserveEntity.startDatetime
            StringBuilder key = new StringBuilder();
            long rStartTime = reserveEntity.getStartDatetime().getTime();
            long rEndTime = reserveEntity.getEndDatetime().getTime();
            long sTTL = (rEndTime - rStartTime) / 1000;
            key.append(reserveEntity.getReserveId())
                    .append("-")
                    .append(reserveEntity.getReservePhone())
                    .append("-")
                    .append(rStartTime);
            key.append(reserveEntity.getReserveId());
            key.append(reserveEntity.getReserveId());

            redisTemplate.opsForValue()
                    .set(key.toString(), json, sTTL, TimeUnit.SECONDS);

        }
    }

}
