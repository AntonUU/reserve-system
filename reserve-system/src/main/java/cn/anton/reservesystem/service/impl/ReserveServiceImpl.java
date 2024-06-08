package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.constant.KafkaConstant;
import cn.anton.commonpackage.common.constant.ReserveConstant;
import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.listener.ReserveMessageListener;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.VisitInfoRequest;
import cn.anton.reservesystem.response.ReserveProcessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.anton.reservesystem.dao.ReserveDao;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.service.ReserveService;


@Service("reserveService")
public class ReserveServiceImpl extends ServiceImpl<ReserveDao, ReserveEntity> implements ReserveService {

    private static final Logger logger = LoggerFactory.getLogger(ReserveServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReserveEntity> page = this.page(
                new Query<ReserveEntity>().getPage(params),
                new QueryWrapper<ReserveEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R reserveAppRequest(ReserveAppRequest requestBody) {
        try {
            String json = objectMapper.writeValueAsString(requestBody);
            kafkaTemplate.send(KafkaConstant.RESERVE_SYSTEM_TOPIC_NAME,
                    ReserveConstant.RESERVE_TYPE_PERSON+ "-" + requestBody.getReserveName() + System.currentTimeMillis(),
                    json);
            logger.info("预约申请人: {}, 已进入队列中....", requestBody.getReserveName());
        } catch (JsonProcessingException e) {
            logger.error("预约申请人: {}, 信息转换失败....", requestBody.getReserveName());
            throw new RuntimeException("消息发送失败, 类型转换异常....");
        }
        return R.ok("已预约, 请等待受理");
    }

    @Override
    public ReserveProcessResponse savePersonReserve(ReserveAppRequest request, Date date){
        ReserveEntity reserveEntity = new ReserveEntity();
        BeanUtils.copyProperties(request, reserveEntity);
        reserveEntity.setUpdateDatetime(date);
        reserveEntity.setReserveStatus(ReserveConstant.RESERVE_STANDBY_ACCEPTANCE);

        VisitInfoRequest visitInfo = request.getVisitInfo();
        save(reserveEntity);
        ReserveProcessResponse result = new ReserveProcessResponse();
        result.setReserveEntity(reserveEntity);
        result.setVisitInfoRequest(visitInfo);

        logger.info("已添加预约信息id: {}, 是否有拜访人: {}", reserveEntity.getReserveId(),
                visitInfo == null ? "否" : "是");
        return result;
    }

    /**
     * 取消预约授权
     * @param reserveId 预约id
     */
    @Override
    public void cancellationOfAuthor(Long reserveId) {
        ReserveEntity entity = new ReserveEntity();
        entity.setReserveId(reserveId);
        entity.setReserveStatus(ReserveConstant.RESERVE_END);
        entity.setUpdateDatetime(new Date());
        updateById(entity);
        logger.info("行人预约id: {} 授权取消成功", reserveId);
    }

}