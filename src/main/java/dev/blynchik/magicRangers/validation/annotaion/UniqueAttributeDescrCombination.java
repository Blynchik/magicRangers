package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.UniqueAttributeDescrCombinationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для проверки уникальности комбинации описания варианта выбора и атрибутом.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueAttributeDescrCombinationValidator.class)
@Documented
public @interface UniqueAttributeDescrCombination {

    String message() default "{event.constraint.message.notUniqueAttrDescrComb}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
