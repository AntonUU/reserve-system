package cn.anton.reservesystem.request;

import lombok.Data;
import lombok.ToString;

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
    private String visitName;

    /**
     * 拜访部门
     */
    private String visitUnit;

    /**
     * 随行人员
     */
    private Boolean accompanyingNum;

    /**
     * 拜访事由
     */
    private String visitContext;
}
