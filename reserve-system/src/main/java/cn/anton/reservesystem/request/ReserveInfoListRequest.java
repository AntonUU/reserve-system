package cn.anton.reservesystem.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 22:25
 */
@Data
public class ReserveInfoListRequest {

    @Min(value = 1L, message = "参数错误....")
    @Max(value = 999L, message = "参数错误....")
    private Integer nextPage;

}
