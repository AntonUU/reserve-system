package cn.anton.reservesystem.controller;

import java.util.Arrays;
import java.util.Map;

import cn.anton.commonpackage.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.anton.reservesystem.entity.VisitEntity;
import cn.anton.reservesystem.service.VisitService;
import cn.anton.commonpackage.common.utils.PageUtils;



/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@RestController
@RequestMapping("reservesystem/visit")
public class VisitController {
    @Autowired
    private VisitService visitService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = visitService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{visitId}")
    public R info(@PathVariable("visitId") Long visitId){
		VisitEntity visit = visitService.getById(visitId);

        return R.ok().put("visit", visit);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody VisitEntity visit){
		visitService.save(visit);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody VisitEntity visit){
		visitService.updateById(visit);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] visitIds){
		visitService.removeByIds(Arrays.asList(visitIds));

        return R.ok();
    }

}
