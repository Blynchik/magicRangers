package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.CountAttemptsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация проверки, что количество оставшихся попыток всех вариантов равно общему количеству попыток на событие
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountAttemptsValidator.class)
@Documented
public @interface CountAttempts {

    String message() default "{event.constraint.message.attemptsMissMatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
