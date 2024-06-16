package cn.anton.reservesystem.controller;

import java.util.Arrays;
import java.util.Map;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.commonpackage.common.validator.group.PersonGroup;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.ReserveSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.service.ReserveService;

import javax.validation.Valid;


/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@RestController
@RequestMapping("/reserve/api")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;


    @PostMapping(value = "/reserve")
    public R reserveAppRequest(
            @Validated({PersonGroup.class})
            @RequestBody ReserveAppRequest requestBody) {
        // 2、 将信息打成JSON，使用Mq进行消费
        R result = reserveService.reserveAppRequest(requestBody);
        //3、 给用户展示预约等待审核页面
        return result;
    }

    @RequestMapping("/search")
    public R reserveSearch(@Validated
                               @RequestBody ReserveSearchRequest requestBody){
        R result =  reserveService.search(requestBody);
        return result;
    }




    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println("被访问了...");
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
