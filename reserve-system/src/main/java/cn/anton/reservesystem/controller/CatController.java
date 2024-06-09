package cn.anton.reservesystem.controller;

import java.util.Arrays;
import java.util.Map;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.commonpackage.common.validator.group.CatGroup;
import cn.anton.reservesystem.request.CatReserveAppRequest;
import cn.anton.reservesystem.request.ReserveAppRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/reserve")
    public R reserveAppRequest(
            @Validated({CatGroup.class})
            @RequestBody ReserveAppRequest requestBody) {
        // 2、 将信息打成JSON，使用Mq进行消费
        R result = catService.reserveAppRequest(requestBody);
        //3、 给用户展示预约等待审核页面
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


    /**
     * 信息
     */
    @RequestMapping("/info/{tabId}")
    public R info(@PathVariable("tabId") Long tabId){
		CatEntity cat = catService.getById(tabId);

        return R.ok().put("cat", cat);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CatEntity cat){
		catService.save(cat);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CatEntity cat){
		catService.updateById(cat);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] tabIds){
		catService.removeByIds(Arrays.asList(tabIds));

        return R.ok();
    }

}
