package cn.anton.reservesystem.service;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.reservesystem.entity.ReserveVisitAssociationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
public interface ReserveVisitAssociationService extends IService<ReserveVisitAssociationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    ReserveVisitAssociationEntity saveAssociationInfo(Long visitId, Long reserveId, String reserveType);
}

