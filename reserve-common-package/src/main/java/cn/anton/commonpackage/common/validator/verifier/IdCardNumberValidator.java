package cn.anton.commonpackage.common.validator.verifier;

import cn.anton.commonpackage.common.validator.myinterface.IdCardNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 类说明: 身份证校验器
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 12:10
 */
public class IdCardNumberValidator implements ConstraintValidator<IdCardNumber, String> {

    // 权重因子
    private static final int[] WEIGHTS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 校验码字符表
    private static final char[] CHECK_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};


    @Override
    public void initialize(IdCardNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (s == null || !Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)").matcher(s).matches()) {
            return false; // 格式不正确
        }

        // 身份证号如果是15位，转换为18位再校验
        if (s.length() == 15) {
            s = s.substring(0, 6) + "19" + s.substring(6) + calculateCheckCode(s);
        }

        // 验证18位身份证的校验码
        if (s.length() == 18) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                sum += Character.getNumericValue(s.charAt(i)) * WEIGHTS[i];
            }
            int mod = sum % 11;
            char checkCode = s.charAt(17);
            if (checkCode != CHECK_CODES[mod]) {
                // 校验码不匹配
                return false;
            }
        }

        return true;
    }

    // 计算18位身份证的校验码
    private String calculateCheckCode(String idCardNumber) {
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Character.getNumericValue(idCardNumber.charAt(i)) * WEIGHTS[i];
        }
        int mod = sum % 11;
        return String.valueOf(CHECK_CODES[mod]);
    }
}
