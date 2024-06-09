package cn.anton.commonpackage.common.validator.myinterface;

import cn.anton.commonpackage.common.validator.verifier.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类说明: 手机号校验注解
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 11:15
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface Phone {

    String message() default "手机号格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
