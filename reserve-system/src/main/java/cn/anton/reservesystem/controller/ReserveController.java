package cn.anton.reservesystem.controller;

import java.util.Arrays;
import java.util.Map;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.service.ReserveService;



/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@RestController
@RequestMapping("reservesystem/reserve")
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = reserveService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{reserveId}")
    public R info(@PathVariable("reserveId") Long reserveId){
		ReserveEntity reserve = reserveService.getById(reserveId);

        return R.ok().put("reserve", reserve);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ReserveEntity reserve){
		reserveService.save(reserve);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ReserveEntity reserve){
		reserveService.updateById(reserve);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] reserveIds){
		reserveService.removeByIds(Arrays.asList(reserveIds));

        return R.ok();
    }

}
