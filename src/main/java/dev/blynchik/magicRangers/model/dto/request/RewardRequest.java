package dev.blynchik.magicRangers.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardRequest {

    @Pattern(regexp = "STR|INTL|CHA", message = "{constraint.message.type}")
    public String type;

    @Max(value = 400, message = "{constraint.message.maxNum}")
    @Min(value = -400, message = "{constraint.message.minNum}")
    @NotNull(message = "{constraint.message.notBlank}")
    public Integer value;
}
