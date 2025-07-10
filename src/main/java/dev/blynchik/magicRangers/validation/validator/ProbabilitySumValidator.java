package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.ProbableResultRequest;
import dev.blynchik.magicRangers.validation.annotaion.ProbabilitySum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProbabilitySumValidator implements ConstraintValidator<ProbabilitySum, List<ProbableResultRequest>> {

    private double inaccuracy;

    @Override
    public void initialize(ProbabilitySum constraintAnnotation) {
        this.inaccuracy = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<ProbableResultRequest> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        double probabilitySum = value.stream()
                .filter(p -> p != null && p.getProbabilityPercent() != null)
                .mapToDouble(ProbableResultRequest::getProbabilityPercent)
                .sum();
        return Math.abs(probabilitySum - 100.0) < inaccuracy;
    }
}
