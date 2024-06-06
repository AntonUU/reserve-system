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
@TableName("tab_admin")
public class AdminEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer adminId;
	/**
	 * 姓名
	 */
	private String adminName;
	/**
	 * 手机号
	 */
	private String adminPhone;
	/**
	 * 权限（111/读写改/11/读写/1/读）
	 */
	private String permissions;
	/**
	 * 登录用户名
	 */
	private String adminUsername;
	/**
	 * 登录密码
	 */
	private String adminPasswd;

}
