package cn.anton.reservesystem.response;

import cn.anton.reservesystem.entity.ReserveEntity;
import lombok.Data;

import java.util.List;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 16:59
 */
@Data
public class ReservePageResponse {

    private List<ReserveEntity> reserves;
    private int currentPage; // 当前页
    private int totalPage; // 总页数
    private int totalCount; // 总条数

}
