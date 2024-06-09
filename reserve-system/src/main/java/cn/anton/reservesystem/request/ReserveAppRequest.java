package cn.anton.reservesystem.request;

import cn.anton.commonpackage.common.validator.group.CatGroup;
import cn.anton.commonpackage.common.validator.group.PersonGroup;
import cn.anton.commonpackage.common.validator.myinterface.IdCardNumber;
import cn.anton.commonpackage.common.validator.myinterface.Phone;
import cn.anton.commonpackage.common.validator.myinterface.SchoolGate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 预约申请参数
 */
@Data
public class ReserveAppRequest {

    /**
     * 个人/单位(预约)
     */
    private Boolean reserveGroup;
    /**
     * 预约人姓名
     */
    @NotBlank(groups = {PersonGroup.class}, message = "申请人姓名不能为空")
    private String reserveName;
    /**
     * 性别
     */
    private Boolean reserveSex;
    /**
     * 手机号
     */
    @Phone(groups = {PersonGroup.class})
    private String reservePhone;
    /**
     * 预约校门
     */
    @SchoolGate(values = {1, 2, 3}, groups = {PersonGroup.class})
    private Integer reserveDoor;
    /**
     * 身份证
     */
    @IdCardNumber(groups = {PersonGroup.class})
    private String reserveCard;
    /**
     * 人像图
     */
    private String portrait;
    /**
     * 预约开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "开始错时间错误...")
    private Date startDatetime;
    /**
     * 预约结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "结束时间错误....")
    private Date endDatetime;

    /**
     * 申请人姓名
     */
    @NotBlank(groups = {CatGroup.class}, message = "车辆申请人姓名不能为空")
    private String personName;
    /**
     * 申请人手机号
     */
    @Phone(groups = {CatGroup.class}, message = "车辆申请人手机号不能为空")
    private String personPhone;

    /**
     * 车牌号
     */
    @NotBlank(groups = {CatGroup.class}, message = "车牌号不能为空")
    private String catId;

    /**
     * 进出校门
     */
    @SchoolGate(values = {1, 2, 3}, groups = {CatGroup.class})
    private Integer entrance;

    /**
     * 被访信息(选填)
     */
    @NotBlank(groups = {CatGroup.class})
    private VisitInfoRequest visitInfo;;


}
