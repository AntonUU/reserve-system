package cn.anton.reservesystem.request;

import cn.anton.commonpackage.common.validator.group.CatGroup;
import cn.anton.commonpackage.common.validator.group.PersonGroup;
import cn.anton.commonpackage.common.validator.myinterface.Phone;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/8 23:04
 */
@Data
public class ReserveSearchRequest {

    @NotBlank(message = "来访人姓名不能为空")
    private String reserveName; // 来访人姓名
    @Phone(message = "来访人手机号不能为空")
    private String reservePhone; // 来访人手机号
    @NotBlank(message = "来访人身份证后四位不能为空", groups = {PersonGroup.class})
    private String theLastFourId; // 来访人身份证后四位
    @NotBlank(message = "车牌号不能为空", groups = {CatGroup.class})
    private String catId; // 车牌号

}
