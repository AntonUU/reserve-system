package cn.anton.commonpackage.common.validator.verifier;

import cn.anton.commonpackage.common.validator.myinterface.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 类说明: 手机号校验器
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 11:18
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    @Override
    public void initialize(Phone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return PHONE_PATTERN.matcher(s).matches();
    }
}
