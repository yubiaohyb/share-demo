package com.yubiaohyb.sharedemo.validator;

import com.yubiaohyb.sharedemo.annotation.NotNullLabel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  非空标签注解校验器
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
public class NotNullLabelAnnotationValidator implements ConstraintValidator<NotNullLabel, Object> {

    private NotNullLabel constraintAnnotation;

    @Override
    public void initialize(NotNullLabel constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        if (null == value) {
            valid = false;
            String message = constraintAnnotation.message();
            context.disableDefaultConstraintViolation();
            if (!"".equals(message)) {
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            } else {
                String label = constraintAnnotation.label();
                context.buildConstraintViolationWithTemplate(String.format("%s不允许为空", label)).addConstraintViolation();
            }
        }
        return valid;
    }

}
