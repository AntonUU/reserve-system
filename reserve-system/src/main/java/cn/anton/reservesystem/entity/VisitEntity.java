package cn.anton.reservesystem.entity;

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
@TableName("tab_visit")
public class VisitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long visitId;
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
	/**
	 * 
	 */
	private Date updateDatetime;

}
