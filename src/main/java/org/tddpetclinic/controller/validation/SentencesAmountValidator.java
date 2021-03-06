package org.tddpetclinic.controller.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SentencesAmountValidator implements ConstraintValidator<SentencesAmount, String> {

    private int max;
    private int min;

    @Override
    public void initialize(SentencesAmount constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(s)) {
            return false;
        }
        int length =  s.split("\\.").length;
        return s.contains(".") && length >= min && length <= max;
    }
}
