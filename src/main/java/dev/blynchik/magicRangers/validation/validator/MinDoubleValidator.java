package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.validation.annotaion.MinDouble;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinDoubleValidator implements ConstraintValidator<MinDouble, Double> {
    private double minValue;

    @Override
    public void initialize(MinDouble constraintAnnotation) {
        this.minValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value == null || value >= minValue;
    }
}
