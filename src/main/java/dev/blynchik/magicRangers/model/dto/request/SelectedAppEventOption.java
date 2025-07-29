package dev.blynchik.magicRangers.model.dto.request;

import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedAppEventOption {

    @Pattern(regexp = "STR|INTL|CHA|TEXT|DECLINE", message = "{constraint.message.type}")
    private String attribute;

    @Size(min = 1, max = 500, message = "{constraint.message.size}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @ValidStringNoMuchGaps(message = "{constraint.message.noMuchGaps}")
    private String descr;

    @PositiveOrZero(message = "{constraint.message.shouldBePositive}")
    @Max(value = 400, message = "{constraint.message.maxNum}")
    @NotNull(message = "{constraint.message.notBlank}")
    private Integer attributeConstraint;
}
