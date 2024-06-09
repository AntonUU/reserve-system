package cn.anton.commonpackage.common.validator.myinterface;

import cn.anton.commonpackage.common.validator.verifier.SchoolGateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类说明: 校门验证注解
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 12:18
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SchoolGateValidator.class)
@Documented
public @interface SchoolGate {

    int[] values();
    String message() default "school gate error";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
