package dev.blynchik.magicRangers.model.dto;

import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOptionResultRequest {

    @PositiveOrZero(message = "{constraint.message.shouldBePositive}")
    @Max(value = 400, message = "{constraint.message.maxNum}")
    @NotNull(message = "{constraint.message.notBlank}")
    private Integer minDifficulty;

    @Size(min = 1, max = 1000, message = "{constraint.message.size}")
    @ValidStringNoMuchGaps(message = "{constraint.message.noMuchGaps}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @NotNull(message = "{constraint.message.notBlank}")
    private String descr;
}
