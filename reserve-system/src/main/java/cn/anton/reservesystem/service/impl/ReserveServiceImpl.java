package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.constant.KafkaConstant;
import cn.anton.commonpackage.common.constant.ReserveConstant;
import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.listener.ReserveMessageListener;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.ReserveInfoListRequest;
import cn.anton.reservesystem.request.ReserveSearchRequest;
import cn.anton.reservesystem.request.VisitInfoRequest;
import cn.anton.reservesystem.response.ReservePageResponse;
import cn.anton.reservesystem.response.ReserveProcessResponse;
import cn.anton.reservesystem.response.ReserveSearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Autowired
    private ReserveDao reserveDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReserveEntity> page = this.page(
                new Query<ReserveEntity>().getPage(params),
                new QueryWrapper<ReserveEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 将预约申请信息发送给KafkaBroker
     * @param requestBody
     * @return
     */
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

    /**
     * 添加预约申请
     * @param request
     * @param date
     * @return
     */
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

    /*
        预约信息查询
     */
    @Override
    public R search(ReserveSearchRequest requestBody) {
        ReserveSearchResponse response = reserveDao.reserveSearch(requestBody);

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

    /**
     * 分页查询
     * @return
     */
    @Override
    public R queryPageReserveLimit10(Integer nextPage) {
        int currentPage = nextPage;
        nextPage = ( nextPage - 1) * 10;
        Integer count = reserveDao.viewWaitAcceptance();
        if (nextPage > count) {
            return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器异常.....");
        }

        List<ReserveEntity> reserves =
                reserveDao.queryPageReserveLimit10(nextPage);
        ReservePageResponse reservePageResponse = new ReservePageResponse();
        reservePageResponse.setReserves(reserves);
        reservePageResponse.setTotalCount(count);
        int totalPage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        reservePageResponse.setTotalPage(totalPage);
        reservePageResponse.setCurrentPage(currentPage);

        return R.ok().put("data", reservePageResponse);
    }

    /*
        预约通过  由于没有技术含量 只做简单处理
     */
    @Override
    public R reserveProcess(Long reserveId, Integer reserveStatus) {
        ReserveEntity entity = new ReserveEntity();
        entity.setReserveStatus(reserveStatus);
        entity.setReserveId(reserveId);
        entity.setUpdateDatetime(new Date());
        updateById(entity);
        return R.ok();
    }



}