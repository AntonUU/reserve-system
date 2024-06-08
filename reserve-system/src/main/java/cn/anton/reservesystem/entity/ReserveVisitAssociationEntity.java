package cn.anton.reservesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 
 * 
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024-06-08 16:47:37
 */
@Data
@TableName("tab_reserve_visit_association")
public class ReserveVisitAssociationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long associationId;
	/**
	 * 预约id
	 */
	private Long reserveId;
	/**
	 * 被访id
	 */
	private Long visitId;

	/**
	 * 车辆表id
	 */
	private Long catId;

	/**
	 * 预约类型 1person 2cat
	 */
	private Boolean reserveType;

	/**
	 * 更新时间
	 */
	private Date updateDatetime;

}
