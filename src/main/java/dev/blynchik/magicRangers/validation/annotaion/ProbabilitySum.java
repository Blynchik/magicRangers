package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.ProbabilitySumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для проверки суммы вероятностей результатов
 */
@Documented
@Constraint(validatedBy = ProbabilitySumValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProbabilitySum {

    double value();

    String message() default "{event.constraint.message.probabilitySum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
