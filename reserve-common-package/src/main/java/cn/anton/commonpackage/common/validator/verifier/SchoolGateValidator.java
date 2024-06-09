package cn.anton.commonpackage.common.validator.verifier;

import cn.anton.commonpackage.common.validator.myinterface.SchoolGate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/9 12:19
 */
public class SchoolGateValidator implements ConstraintValidator<SchoolGate, Integer> {

    private List<Integer> doors;

    @Override
    public void initialize(SchoolGate constraintAnnotation) {
        this.doors = new ArrayList<Integer>();
        for (int val : constraintAnnotation.values()) {
            this.doors.add(val);
        }
    }

    @Override
    public boolean isValid(Integer val, ConstraintValidatorContext constraintValidatorContext) {
        return val != null && doors.contains(val);
    }
}
