package cn.anton.reservesystem.response;

import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.entity.VisitEntity;
import cn.anton.reservesystem.request.VisitInfoRequest;
import lombok.Data;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/8 19:56
 */
@Data
public class ReserveProcessResponse {

    /*
        行人预约信息
     */
    private ReserveEntity reserveEntity;
    /*
        车辆预约信息
     */
    private CatEntity catEntity;
    /*
        被访人信息
     */
    private VisitInfoRequest visitInfoRequest;

}
