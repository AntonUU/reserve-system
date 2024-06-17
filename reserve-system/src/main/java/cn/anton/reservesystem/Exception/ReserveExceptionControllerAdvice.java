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
        BindingResult bindingResult = e.getBindingResult();
        printException(e.getMessage(), e.getClass(), e, bindingResult);

        StringBuilder sb = new StringBuilder();
        assert bindingResult != null;
        bindingResult.getFieldErrors().forEach(error -> {
            sb.append(error.getDefaultMessage()).append("\n");
        });

        return R.error(HttpStatus.SC_CONFLICT, sb.toString());
    }

    @ExceptionHandler(value = {Throwable.class})
    public R handleException(Throwable e){
        printException(e.getMessage(), e.getClass(), e, null);
        return R.error(HttpStatus.SC_CONFLICT, "参数错误...");
    }

    private void printException(String message, Class<?> aclass, Throwable throwable, BindingResult bindingResult){
        logger.error("服务器运行出错:{}, 异常:{}", message, aclass, throwable);
        if (bindingResult != null) {
            bindingResult.getFieldErrors().forEach(error -> {
                logger.error("字段错误: 字段名: {}, 错误信息: {}", error.getField(), error.getDefaultMessage());
            });
        }
    }
}
