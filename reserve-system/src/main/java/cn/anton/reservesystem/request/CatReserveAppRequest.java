package cn.anton.reservesystem.request;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 预约申请参数
 */
@Data
@ToString
@Deprecated
public class CatReserveAppRequest {

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
     * 预约开始时间
     */
    private Date startDatetime;

    /**
     * 预约结束时间
     */
    private Date endDatetime;

    /**
     * 进出校门
     */
    private Boolean entrance;

    /**
     * 被访人信息(必填)
     */
    private VisitInfoRequest visitInfoRequest;


}
