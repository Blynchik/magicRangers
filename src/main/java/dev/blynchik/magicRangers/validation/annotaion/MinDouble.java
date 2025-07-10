package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.MinDoubleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для проверки минимального значения для вещественных чисел
 */
@Documented
@Constraint(validatedBy = MinDoubleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDouble {
    double value();

    String message() default "{constraint.message.minNum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
