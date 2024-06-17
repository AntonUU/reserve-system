package cn.anton.reservesystem.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 类说明: 预约查询返回结果
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/8 23:15
 */
@Data
public class ReserveSearchResponse {

    private String reserveName; // 预约人姓名
    private String reservePhone; // 预约人手机号
    private Boolean reserveSex; // 预约人性别
    private Integer reserveStatus; // 预约状态
    private String portrait; // 人脸图
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;  // 预约创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDateTime; // 通行开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDateTime; //  结束通行时间

}
