package cn.anton.reservesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-06 23:37:37
 */
@Data
@TableName("tab_cat")
public class CatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long tabId;
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
	 * 预约状态 0等待受理 1通过 2过期 3拒绝
	 */
	private Integer reserveStatus;

	/**
	 * 预约开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startDatetime;
	/**
	 * 预约结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endDatetime;
	/**
	 * 进出校门
	 */
	private Integer entrance;
	/**
	 * 
	 */
	private Date updateDatetime;

}
