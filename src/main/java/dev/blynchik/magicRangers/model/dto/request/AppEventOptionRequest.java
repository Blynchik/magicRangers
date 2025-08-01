package dev.blynchik.magicRangers.model.dto.request;

import dev.blynchik.magicRangers.validation.annotaion.UniqueResultMinDifficulty;
import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppEventOptionRequest {

    @Pattern(regexp = "STR|INTL|CHA|TEXT|DECLINE", message = "{constraint.message.type}")
    private String attribute;

    @Size(min = 1, max = 500, message = "{constraint.message.size}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @ValidStringNoMuchGaps(message = "{constraint.message.noMuchGaps}")
    private String descr;

    @Min(value = 1, message = "{constraint.message.minNum}")
    @Max(value = Integer.MAX_VALUE, message = "{constraint.message.maxNum}")
    private int remainingAttempts;

    @Valid
    @Size(min = 1, max = 10, message = "{constraint.message.size}")
    @UniqueElements(message = "{constraint.message.notUniqueElements}")
    @NotNull(message = "{constraint.message.notBlank}")
    @UniqueResultMinDifficulty(message = "{event.constraint.message.notUniqueMinDifficulty}")
    //при TEXT или DECLINE возможен только один элемент
    private List<@NotNull(message = "{constraint.message.notBlank}") AppEventOptionResultListRequest> results;
}
