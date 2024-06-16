package cn.anton.reservesystem.Exception;

import cn.anton.commonpackage.common.utils.R;
import cn.anton.commonpackage.common.validator.group.CatGroup;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 类说明: 请求参数异常的捕抓
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 11:37
 */
@RestControllerAdvice
public class ReserveExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ReserveExceptionControllerAdvice.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handleValidException(MethodArgumentNotValidException e) {
        printException(e.getMessage(), e.getClass(), e);

        StringBuilder sb = new StringBuilder();
        assert e.getBindingResult() != null;
        e.getBindingResult().getFieldErrors().forEach(error -> {
            sb.append(error.getDefaultMessage()).append("\n");
        });

        return R.error(HttpStatus.SC_CONFLICT, sb.toString());
    }

    @ExceptionHandler(value = {Throwable.class})
    public R handleException(Throwable e){
        printException(e.getMessage(), e.getClass(), e);
        return R.error(HttpStatus.SC_CONFLICT, "参数错误...");
    }

    private void printException(String message, Class<?> aclass, Throwable throwable){
        logger.error("服务器运行出错:{}, 异常:{}, 堆栈信息:", message, aclass, throwable);
    }

}
