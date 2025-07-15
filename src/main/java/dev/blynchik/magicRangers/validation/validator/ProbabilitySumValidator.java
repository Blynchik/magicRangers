package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.AppProbableResultRequest;
import dev.blynchik.magicRangers.validation.annotaion.ProbabilitySum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProbabilitySumValidator implements ConstraintValidator<ProbabilitySum, List<AppProbableResultRequest>> {

    private double inaccuracy;

    @Override
    public void initialize(ProbabilitySum constraintAnnotation) {
        this.inaccuracy = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<AppProbableResultRequest> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        double probabilitySum = value.stream()
                .filter(p -> p != null && p.getProbabilityPercent() != null)
                .mapToDouble(AppProbableResultRequest::getProbabilityPercent)
                .sum();
        return Math.abs(probabilitySum - 100.0) < inaccuracy;
    }
}
