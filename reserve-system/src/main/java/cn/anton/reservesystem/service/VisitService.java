package cn.anton.reservesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.anton.reservesystem.entity.VisitEntity;
import cn.anton.commonpackage.common.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
public interface VisitService extends IService<VisitEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

