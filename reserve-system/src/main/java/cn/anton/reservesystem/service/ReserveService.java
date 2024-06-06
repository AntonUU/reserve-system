package cn.anton.reservesystem.service;

import cn.anton.commonpackage.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.anton.reservesystem.entity.ReserveEntity;

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
}

