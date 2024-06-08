package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.constant.ReserveConstant;
import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;
import cn.anton.reservesystem.dao.CatDao;
import cn.anton.reservesystem.dao.ReserveVisitAssociationDao;
import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.entity.ReserveVisitAssociationEntity;
import cn.anton.reservesystem.listener.ReserveMessageListener;
import cn.anton.reservesystem.service.CatService;
import cn.anton.reservesystem.service.ReserveVisitAssociationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service("reserveVisitAssociationService")
public class ReserveVisitAssociationServiceImpl extends ServiceImpl<ReserveVisitAssociationDao, ReserveVisitAssociationEntity> implements ReserveVisitAssociationService {

    private static final Logger logger = LoggerFactory.getLogger(ReserveVisitAssociationServiceImpl.class);

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReserveVisitAssociationEntity> page = this.page(
                new Query<ReserveVisitAssociationEntity>().getPage(params),
                new QueryWrapper<ReserveVisitAssociationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public ReserveVisitAssociationEntity saveAssociationInfo(Long visitId, Long reserveId, String reserveType) {
        ReserveVisitAssociationEntity entity = new ReserveVisitAssociationEntity();
        if (ReserveConstant.RESERVE_TYPE_PERSON.equals(reserveType)) {
            entity.setReserveId(reserveId);
            entity.setReserveType(true);
        } else {
            entity.setCatId(reserveId);
            entity.setReserveType(false);
        }
        entity.setVisitId(visitId);
        entity.setUpdateDatetime(new Date());
        save(entity);
        logger.info("拜访信息关联成功 [类型: {}, 预约id: {}, 拜访id: {}, 关联id: {}]",
                reserveType,
                reserveId,
                visitId,
                entity.getAssociationId());
        return entity;
    }

}