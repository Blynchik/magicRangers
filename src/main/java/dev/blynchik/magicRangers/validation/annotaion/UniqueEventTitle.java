package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.UniqueEventTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для проверки уникальности названия события
 */
@Documented
@Constraint(validatedBy = UniqueEventTitleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEventTitle {

    String message() default "{event.constraint.message.notUniqueTitle}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
