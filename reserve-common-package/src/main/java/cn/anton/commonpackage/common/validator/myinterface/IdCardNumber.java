package cn.anton.commonpackage.common.validator.myinterface;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 12:10
 */
import cn.anton.commonpackage.common.validator.verifier.IdCardNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardNumberValidator.class)
@Documented
public @interface IdCardNumber {

    String message() default "身份证号码格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
