package cn.anton.reservesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类说明:
 *      公告表
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2025/4/8 20:47
 */
@Data
@TableName("ted_announcements")
public class AnnouncementsEntity {

    private Long id;
    private String title;
    private String content;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 设置时间格式化
    private LocalDateTime publishDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 设置时间格式化
    private LocalDateTime expiryDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 设置时间格式化
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 设置时间格式化
    private LocalDateTime updatedAt;

}
