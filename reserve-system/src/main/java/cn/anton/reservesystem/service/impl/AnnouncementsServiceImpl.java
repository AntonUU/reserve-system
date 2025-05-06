package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.dao.AnnouncementsDao;
import cn.anton.reservesystem.dao.CatDao;
import cn.anton.reservesystem.entity.AnnouncementsEntity;
import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.service.AnnouncementsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2025/4/8 20:50
 */
@Service
public class AnnouncementsServiceImpl extends ServiceImpl<AnnouncementsDao, AnnouncementsEntity> implements AnnouncementsService {

    private Random r = new Random();

    /**
     * 获取列表
     * @return
     */
    @Override
    public R all() {
        List<AnnouncementsEntity> announcementsList = baseMapper.selectList(null);
        return R.ok().put("data", announcementsList);
    }

    @Override
    public R get() {
        List<AnnouncementsEntity> announcementsEntities = baseMapper.selectList(new QueryWrapper<AnnouncementsEntity>()
                .eq("status", 0));
        int size = announcementsEntities.size();
        if (size == 0) return R.ok();
        int result = r.nextInt(size);
        AnnouncementsEntity entity = announcementsEntities.get(result);

        return R.ok().put("data", entity);
    }

    @Override
    public R update(AnnouncementsEntity announcementsEntity) {
        if (ObjectUtils.isEmpty(announcementsEntity)) return R.error(-1, "更新失败, 请联系开发检查!");
        if (announcementsEntity.getId() == null) return R.error(-1, "指定更新失败, 请联系开发检查!");

        List<AnnouncementsEntity> announcementsEntities = baseMapper.selectList(new QueryWrapper<AnnouncementsEntity>().eq("id", announcementsEntity.getId()));
        if (CollectionUtils.isEmpty(announcementsEntities)) return R.error(-1, "更新失败, 请联系开发检查!");

        baseMapper.update(announcementsEntity, new QueryWrapper<AnnouncementsEntity>()
                .eq("id", announcementsEntity.getId()));
        return R.ok();
    }

    @Override
    public R del(Long id) {
        List<AnnouncementsEntity> announcementsEntities = baseMapper.selectList(new QueryWrapper<AnnouncementsEntity>().eq("id", id));
        if (CollectionUtils.isEmpty(announcementsEntities) || announcementsEntities.size() != 1) return R.error(-1, "更新失败, 请联系开发检查!");
        AnnouncementsEntity entity = announcementsEntities.get(0);
        entity.setStatus(1);
        baseMapper.update(entity, new QueryWrapper<AnnouncementsEntity>().eq("id", id));
        return R.ok();
    }

    @Override
    public R saveEntity(AnnouncementsEntity announcementsEntity) {
        if (announcementsEntity == null || !(announcementsEntity.getId() == null)) return R.error(-1, "保存, 请联系开发检查!");
        announcementsEntity.setStatus(0);
        announcementsEntity.setPublishDate(LocalDateTime.now());
        announcementsEntity.setExpiryDate(LocalDateTime.now());
        boolean save = super.save(announcementsEntity);
        return R.ok();
    }

}
