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
@TableName("tab_cat")
public class CatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
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
	 * 
	 */
	private Date updateDatetime;

}
