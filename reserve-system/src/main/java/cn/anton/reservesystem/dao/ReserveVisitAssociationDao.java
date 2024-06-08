package cn.anton.reservesystem.dao;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/8 16:48
 */

import cn.anton.reservesystem.entity.ReserveVisitAssociationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReserveVisitAssociationDao extends BaseMapper<ReserveVisitAssociationEntity> {
}
