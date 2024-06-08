package cn.anton.commonpackage.common.constant;

/**
 * 类说明: 预约常量
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/8 16:39
 */
public class ReserveConstant {

    /**
     * 预约状态 0等待受理 1通过 2过期 3拒绝
     */
    public static final Integer RESERVE_STANDBY_ACCEPTANCE = 0;
    public static final Integer RESERVE_PASSED = 1;
    public static final Integer RESERVE_END = 2;
    public static final Integer RESERVE_REFUSED = 3;

    /**
     * 预约类型
     */
    public static final String RESERVE_TYPE_PERSON = "person";
    public static final String RESERVE_TYPE_CAT = "cat";

}
