package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.constant.KafkaConstant;
import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.request.ReserveAppRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.anton.reservesystem.dao.ReserveDao;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.service.ReserveService;


@Service("reserveService")
public class ReserveServiceImpl extends ServiceImpl<ReserveDao, ReserveEntity> implements ReserveService {

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
                    requestBody.getReserveName() + System.currentTimeMillis(),
                    json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("消息发送失败, 类型转换异常....");
        }
        return R.ok("已预约, 请等待受理");
    }

}