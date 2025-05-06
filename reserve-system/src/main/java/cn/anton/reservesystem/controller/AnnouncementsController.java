package cn.anton.reservesystem.controller;

import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.entity.AnnouncementsEntity;
import cn.anton.reservesystem.service.AnnouncementsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2025/4/8 20:52
 */
@RestController
@RequestMapping("/announcements")
public class AnnouncementsController {

    @Resource
    private AnnouncementsService announcementsService;

    @RequestMapping("/all")
    public R all() {
        return announcementsService.all();
    }

    @RequestMapping("/get")
    public R get() {
        return announcementsService.get();
    }

    @RequestMapping("/update")
    public R update(@RequestBody AnnouncementsEntity announcementsEntity) {
        return announcementsService.update(announcementsEntity);
    }

    @RequestMapping("/del/{id}")
    public R del(@PathVariable Long id) {
        return announcementsService.del(id);
    }

    @RequestMapping("/save")
    public R save(@RequestBody AnnouncementsEntity announcementsEntity) {
        return announcementsService.saveEntity(announcementsEntity);
    }

}
