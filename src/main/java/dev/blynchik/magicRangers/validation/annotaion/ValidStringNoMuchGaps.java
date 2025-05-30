package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.StringNoMuchGapsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для валидации строки на отсутствие нескольких пробелов подряд.
 * Может применяться к полям класса и параметрам методов.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringNoMuchGapsValidator.class)
@Documented
public @interface ValidStringNoMuchGaps {
    String message() default "{constraint.message.noMuchGaps}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
