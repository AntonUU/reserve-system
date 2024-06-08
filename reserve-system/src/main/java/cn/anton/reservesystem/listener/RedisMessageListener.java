package cn.anton.reservesystem.listener;


import cn.anton.commonpackage.common.constant.ReserveConstant;
import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.service.CatService;
import cn.anton.reservesystem.service.ReserveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 类说明: 预约时间到期监听
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/8 17:15
 */
@Component
public class RedisMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessageListener.class);

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private CatService catService;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        String s = new String(body);
        String[] split = s.split("-");
        Long reserveId = Long.parseLong(split[1]);
        String reserveType = split[0];
        // 1、修改库中的预约信息
        try {
            logger.info("预约过期....正在取消门禁授权, 预约id: {}", reserveId);
            if (ReserveConstant.RESERVE_TYPE_PERSON.equals(reserveType)) {
                reserveService.cancellationOfAuthor(reserveId);
            } else {
                catService.cancellationOfAuthor(reserveId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{}预约id: {} 取消授权失败, 请及时处理", reserveType,reserveId);
        }

        // 2、将人脸从库中删除或修改状态码, 这里没有真实人脸暂且做不了

    }

}
