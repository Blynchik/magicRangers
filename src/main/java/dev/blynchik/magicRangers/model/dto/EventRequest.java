package dev.blynchik.magicRangers.model.dto;

import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventRequest {

    @ValidStringNoMuchGaps(message = "{constraint.message.noMuchGaps}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @Size(min = 1, max = 255, message = "{constraint.message.size}")
    private String title;

    @ValidStringNoMuchGaps(message = "{constraint.message.noMuchGaps}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @Size(min = 1, max = 1000, message = "{constraint.message.size}")
    private String descr;
}
