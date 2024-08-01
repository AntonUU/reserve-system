package cn.anton.reservesystem.controller;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import cn.anton.commonpackage.common.annotation.SysLog;
import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.R;
import cn.anton.commonpackage.common.validator.group.PersonGroup;
import cn.anton.reservesystem.request.ReserveAppRequest;
import cn.anton.reservesystem.request.ReserveSearchRequest;
import cn.anton.reservesystem.util.AliOSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.service.ReserveService;
import org.springframework.web.multipart.MultipartFile;

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
@Slf4j
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private AliOSSUtil ossUtil;

    /**
     * 行人预约
     * @param requestBody
     * @return
     */
    @PostMapping(value = "/reserve")
    public R reserveAppRequest(
            @Validated({PersonGroup.class})
            @RequestBody ReserveAppRequest requestBody) {
        log.info("【行人预约】 预约中ing... name = {}, phone = {}", requestBody.getReserveName(), requestBody.getReservePhone());
        // 2、 将信息打成JSON，使用Mq进行消费
        R result = reserveService.reserveAppRequest(requestBody);
        log.info("【行人预约】 预约over  name = {}, phone = {}", requestBody.getReserveName(), requestBody.getReservePhone());
        //3、 给用户展示预约等待审核页面
        return result;
    }

    /**
     * 预约查询
     * @param requestBody
     * @return
     */
    @RequestMapping("/search")
    public R reserveSearch(@Validated
                           @RequestBody ReserveSearchRequest requestBody){
        log.info("【预约查询】查询ing... name:{} phone: {}", requestBody.getReserveName(), requestBody.getReservePhone());
        R result =  reserveService.search(requestBody);
        log.info("【预约查询】查询over  name:{} phone: {}", requestBody.getReserveName(), requestBody.getReservePhone());
        return result;
    }


    /**
     * 人脸上传
     * @return 人脸上传
     */
    @PostMapping("/face_upload")
    public R faceUpload(@RequestParam("file") MultipartFile file){
        log.info("【人脸上传】 上传ing... fileName = {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            return R.error("空文件无法上传");
        }
        try {
            // 校验
            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.split("\\.")[1];
            boolean imageSuffix = isImageSuffix(fileSuffix);
            if (!imageSuffix) return R.error("只允许上传jpg、png等格式图片");

            // 上传文件
            InputStream inputStream = file.getInputStream();
            String objName = UUID.randomUUID().toString() + "." + fileSuffix;
            String portraitUri = ossUtil.putFileByStream(objName, inputStream);

            // 将人像uri返回给用户， 用户再重新提交给我
//            log.info("【人脸上传】 上传over fileName = {}, objName = {}", file.getOriginalFilename(), objName);
            return R.ok(portraitUri);
        } catch (Exception e) {
            log.info("【人脸上传】 error = {}, fileName = {}", e.getMessage(), file.getOriginalFilename());
            return R.error("人像上传失败");
        }
    }

    private boolean isImageSuffix(String fileSuffix) {
        String[] imageSuffix = {"jpg", "png"};
        for (String suffix : imageSuffix)
            if (suffix.equals(fileSuffix)) return true;
        return false;
    }


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
