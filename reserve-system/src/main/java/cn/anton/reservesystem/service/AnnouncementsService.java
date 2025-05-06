package cn.anton.reservesystem.service;

import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.entity.AnnouncementsEntity;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2025/4/8 20:50
 */
public interface AnnouncementsService {

    R all();

    R get();

    R update(AnnouncementsEntity announcementsEntity);

    R del(Long id);

    R saveEntity(AnnouncementsEntity announcementsEntity);

}
