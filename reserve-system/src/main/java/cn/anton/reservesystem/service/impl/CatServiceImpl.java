package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.constant.KafkaConstant;
import cn.anton.commonpackage.common.constant.ReserveConstant;
import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.dao.ReserveDao;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.request.CatReserveAppRequest;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.ReserveSearchRequest;
import cn.anton.reservesystem.request.VisitInfoRequest;
import cn.anton.reservesystem.response.ReserveProcessResponse;
import cn.anton.reservesystem.response.ReserveSearchResponse;
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

import cn.anton.reservesystem.dao.CatDao;
import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.service.CatService;


@Service("catService")
public class CatServiceImpl extends ServiceImpl<CatDao, CatEntity> implements CatService {

    private static final Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private CatDao catDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CatEntity> page = this.page(
                new Query<CatEntity>().getPage(params),
                new QueryWrapper<CatEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R reserveAppRequest(ReserveAppRequest requestBody) {
        try {
            String json = objectMapper.writeValueAsString(requestBody);
            kafkaTemplate.send(KafkaConstant.RESERVE_SYSTEM_TOPIC_NAME,
                    ReserveConstant.RESERVE_TYPE_CAT+ "-"
                            + requestBody.getPersonName() + System.currentTimeMillis(),
                    json);
            logger.info("预约车辆: {}, 申请人: {}, 已进入队列中....",
                    requestBody.getCatId(), requestBody.getPersonName());
        } catch (JsonProcessingException e) {
            logger.info("预约车辆: {}, 申请人: {}, 类型转换异常....",
                    requestBody.getCatId(), requestBody.getPersonName());
            throw new RuntimeException("消息发送失败, 类型转换异常....");
        }
        return R.ok("已预约, 请等待受理");
    }

    @Override
    public ReserveProcessResponse savePersonReserve(ReserveAppRequest request, Date date) {
        CatEntity catEntity = new CatEntity();
        BeanUtils.copyProperties(request, catEntity);
        catEntity.setUpdateDatetime(date);
        // 车辆自动审批
        catEntity.setReserveStatus(ReserveConstant.RESERVE_PASSED);

        VisitInfoRequest visitInfo = request.getVisitInfo();
        save(catEntity);
        ReserveProcessResponse result = new ReserveProcessResponse();
        result.setCatEntity(catEntity);
        result.setVisitInfoRequest(visitInfo);

        logger.info("已添加预约车辆信息id: {}, 车牌号: {}", catEntity.getTabId(),
                catEntity.getCatId());
        return result;
    }

    @Override
    public void cancellationOfAuthor(Long reserveId) {
        CatEntity entity = new CatEntity();
        entity.setTabId(reserveId);
        entity.setReserveStatus(ReserveConstant.RESERVE_END);
        entity.setUpdateDatetime(new Date());
        updateById(entity);
        logger.info("车辆预约id: {} 授权取消成功", reserveId);
    }

    @Override
    public R search(ReserveSearchRequest requestBody) {
        ReserveSearchResponse response = catDao.reserveSearch(requestBody);

        // 没有结果
        if (response == null) {
            return R.ok("未能查询到您的预约信息");
        }

        // 超过结束通行时间
        long endTime = response.getEndDateTime().getTime();
        long currentTime = System.currentTimeMillis();
        if (endTime < currentTime) {
            return R.ok("未能查询到您的预约信息");
        }

        return R.ok().put("data", response);
    }

}