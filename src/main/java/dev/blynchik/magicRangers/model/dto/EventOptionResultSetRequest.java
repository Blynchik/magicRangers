package dev.blynchik.magicRangers.model.dto;

import dev.blynchik.magicRangers.validation.annotaion.ProbabilitySum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOptionResultSetRequest {

    @PositiveOrZero(message = "{constraint.message.shouldBePositive}")
    @Max(value = 400, message = "{constraint.message.maxNum}")
    @NotNull(message = "{constraint.message.notBlank}")
    private Integer minDifficulty;

    @Valid
    @Size(min = 1, max = 20, message = "{constraint.message.size}")
    @NotNull(message = "{constraint.message.notBlank}")
    @UniqueElements(message = "{constraint.message.notUniqueElements}")
    @ProbabilitySum(message = "{event.constraint.message.probabilitySum}", value = 0.0000001)
    private List<@NotNull(message = "{constraint.message.notBlank}") ProbableResultRequest> probableResults;
}
