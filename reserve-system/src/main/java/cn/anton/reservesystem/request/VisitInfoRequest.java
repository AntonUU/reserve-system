package cn.anton.reservesystem.request;

import cn.anton.commonpackage.common.validator.group.CatGroup;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 类说明: 被访人信息请求参数
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/7 09:16
 */
@Data
@ToString
public class VisitInfoRequest {

    /**
     * 被拜访人姓名
     */
    @NotBlank(groups = {CatGroup.class})
    private String visitName;

    /**
     * 拜访部门
     */
    @NotBlank(groups = {CatGroup.class})
    private String visitUnit;

    /**
     * 随行人员
     */
    @NotBlank(groups = {CatGroup.class})
    private Integer accompanyingNum;

    /**
     * 拜访事由
     */
    @NotBlank(groups = {CatGroup.class})
    private String visitContext;


}
