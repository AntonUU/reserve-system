package cn.anton.reservesystem.dao;

import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.request.ReserveSearchRequest;
import cn.anton.reservesystem.response.ReservePageResponse;
import cn.anton.reservesystem.response.ReserveSearchResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@Mapper
public interface ReserveDao extends BaseMapper<ReserveEntity> {

    ReserveSearchResponse reserveSearch(ReserveSearchRequest request);

    List<ReserveEntity> queryPageReserveLimit10(Integer startLimit);

    Integer viewWaitAcceptance();

}
