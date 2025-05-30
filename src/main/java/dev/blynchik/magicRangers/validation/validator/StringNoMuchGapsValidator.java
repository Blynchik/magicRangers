package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class StringNoMuchGapsValidator implements ConstraintValidator<ValidStringNoMuchGaps, String> {

    private String message;

    @Override
    public void initialize(ValidStringNoMuchGaps constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        boolean hasInvalidSpacing = value.matches(".*\\s{2,}.*");
        if (hasInvalidSpacing) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
