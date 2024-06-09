package cn.anton.reservesystem.Exception;

import cn.anton.commonpackage.common.utils.R;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 类说明: 请求参数异常的捕抓
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 11:37
 */
@RestControllerAdvice(basePackages = "cn.anton.reservesystem.controller")
public class ReserveExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ReserveExceptionControllerAdvice.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handleValidException(MethodArgumentNotValidException e) {
        printException(e.getMessage(), e.getClass());

        // 可以通过MethodArgumentNotValidException来获取异常的详细信息, 这里我就不做了

        return R.error(HttpStatus.SC_CONFLICT, "参数错误...");
    }

    @ExceptionHandler(value = {Throwable.class})
    public R handleException(Throwable e){
        printException(e.getMessage(), e.getClass());
        return R.error(HttpStatus.SC_CONFLICT, "参数错误...");
    }

    private void printException(String message, Class<?> aclass){
        logger.error("数据校验出现问题:{}, 异常:{}", message, aclass);
    }

}
