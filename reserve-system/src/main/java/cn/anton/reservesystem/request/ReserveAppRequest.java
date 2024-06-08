package cn.anton.reservesystem.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 预约申请参数
 */
@Data
@ToString
public class ReserveAppRequest {

    /**
     * 个人/单位(预约)
     */
    private Boolean reserveGroup;
    /**
     * 预约人姓名
     */
    private String reserveName;
    /**
     * 性别
     */
    private Boolean reserveSex;
    /**
     * 手机号
     */
    private String reservePhone;
    /**
     * 身份证
     */
    private String reserveCard;
    /**
     * 人像图
     */
    private String portrait;
    /**
     * 预约校门
     */
    private Integer reserveDoor;
    /**
     * 预约开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDatetime;
    /**
     * 预约结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDatetime;

    /**
     * 申请人姓名
     */
    private String personName;
    /**
     * 申请人手机号
     */
    private String personPhone;

    /**
     * 车牌号
     */
    private String catId;

    /**
     * 进出校门
     */
    private Integer entrance;

    /**
     * 被访信息(选填)
     */
    private VisitInfoRequest visitInfo;;


}
