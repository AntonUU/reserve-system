package cn.anton.reservesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@Data
@TableName("tab_reserve")
public class ReserveEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long reserveId;
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
	 * 预约状态 0等待受理 1通过 2过期 3拒绝
	 */
	private Integer reserveStatus;
	/**
	 * 预约开始时间
	 */
	private Date startDatetime;
	/**
	 * 预约结束时间
	 */
	private Date endDatetime;
	/**
	 * 更新日志
	 */
	private Date updateDatetime;

}
