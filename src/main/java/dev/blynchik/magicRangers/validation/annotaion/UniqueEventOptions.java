package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.UniqueEventOptionsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEventOptionsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEventOptions {
    String message() default "{constraint.message.notUniqueElements}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
