package cn.anton.reservesystem.controller;

import java.util.Arrays;
import java.util.Map;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.commonpackage.common.validator.group.CatGroup;
import cn.anton.reservesystem.request.CatReserveAppRequest;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.ReserveSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.service.CatService;



/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@RestController
@RequestMapping("/cat/api")
public class CatController {
    @Autowired
    private CatService catService;

    @PostMapping("/reserve")
    public R reserveAppRequest(
            @Validated({CatGroup.class})
            @RequestBody ReserveAppRequest requestBody) {
        // 2、 将信息打成JSON，使用Mq进行消费
        R result = catService.reserveAppRequest(requestBody);
        //3、 给用户展示预约等待审核页面
        return result;
    }

    @PostMapping("/search")
    public R reserveCatSearch(@Validated({CatGroup.class})
                            @RequestBody ReserveSearchRequest requestBody){
        R result =  catService.search(requestBody);
        return result;
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = catService.queryPage(params);

        return R.ok().put("page", page);
    }

}
