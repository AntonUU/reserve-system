package cn.anton.reservesystem.service;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.request.CatReserveAppRequest;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.response.ReserveProcessResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.anton.reservesystem.entity.CatEntity;

import java.util.Date;
import java.util.Map;

/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
public interface CatService extends IService<CatEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R reserveAppRequest(ReserveAppRequest requestBody);

    ReserveProcessResponse savePersonReserve(ReserveAppRequest request, Date date);

    void cancellationOfAuthor(Long reserveId);
}

