package cn.anton.reservesystem.service;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.ReserveInfoListRequest;
import cn.anton.reservesystem.request.ReserveSearchRequest;
import cn.anton.reservesystem.response.ReserveProcessResponse;
import cn.anton.reservesystem.response.ReserveSearchResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.anton.reservesystem.entity.ReserveEntity;

import java.util.Date;
import java.util.Map;

/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
public interface ReserveService extends IService<ReserveEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R reserveAppRequest(ReserveAppRequest requestBody);

    ReserveProcessResponse savePersonReserve(ReserveAppRequest request, Date date);

    void cancellationOfAuthor(Long reserveId);

    R search(ReserveSearchRequest requestBody);

    R queryPageReserveLimit10(Integer nextPage);
}

