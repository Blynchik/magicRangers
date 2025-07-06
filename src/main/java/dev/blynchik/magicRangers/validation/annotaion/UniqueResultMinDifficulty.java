package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.UniqueResultMinDifficultyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для проверки уникальности минимальной сложности для получения результата.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueResultMinDifficultyValidator.class)
@Documented
public @interface UniqueResultMinDifficulty {

    String message() default "{event.constraint.message.notUniqueMinDifficulty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
