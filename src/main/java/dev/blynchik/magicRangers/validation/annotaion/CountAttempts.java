package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.CountAttemptsValidator;
import dev.blynchik.magicRangers.validation.validator.UniqueAttributeDescrCombinationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountAttemptsValidator.class)
@Documented
public @interface CountAttempts {

    String message() default "{event.constraint.message.attemptsMissMatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
