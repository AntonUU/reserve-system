package cn.anton.reservesystem.controller;

import java.util.Arrays;

import cn.anton.commonpackage.common.utils.R;
import cn.anton.reservesystem.request.ReserveInfoListRequest;
import cn.anton.reservesystem.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.anton.reservesystem.entity.AdminEntity;
import cn.anton.reservesystem.service.AdminService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * 
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@RestController
@RequestMapping("/reservesystem/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReserveService reserveService;

    /**
     * 信息
     */
    @RequestMapping("/info/{adminId}")
    public R info(@PathVariable("adminId") Integer adminId){
		AdminEntity admin = adminService.getById(adminId);

        return R.ok().put("admin", admin);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AdminEntity admin){
		adminService.save(admin);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AdminEntity admin){
		adminService.updateById(admin);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] adminIds){
		adminService.removeByIds(Arrays.asList(adminIds));

        return R.ok();
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public R reserveList(@Validated
                         @RequestParam
                         @Min(value = 1L, message = "参数错误....")
                         @Max(value = 999L, message= "参数错误....")
                         Integer nextPage ){
        R result = reserveService.queryPageReserveLimit10(nextPage);

        return result;
    }
}
