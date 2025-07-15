package dev.blynchik.magicRangers.model.dto;

import dev.blynchik.magicRangers.validation.annotaion.MinDouble;
import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppProbableResultRequest {

    @NotNull(message = "{constraint.message.notBlank}")
    @PositiveOrZero(message = "{constraint.message.shouldBePositive}")
    @Max(value = 100, message = "{constraint.message.maxNum}")
    @MinDouble(message = "{constraint.message.minNum}", value = 0.000001)
    private Double probabilityPercent;

    @Size(min = 1, max = 1000, message = "{constraint.message.size}")
    @ValidStringNoMuchGaps(message = "{constraint.message.noMuchGaps}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @NotNull(message = "{constraint.message.notBlank}")
    private String descr;
}
