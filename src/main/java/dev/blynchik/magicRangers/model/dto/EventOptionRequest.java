package dev.blynchik.magicRangers.model.dto;

import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventOptionRequest {

    @Pattern(regexp = "STR|INTL|CHA", message = "{constraint.message.type}")
    private String attribute;
    @Size(min = 1, max = 500, message = "{constraint.message.size}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @ValidStringNoMuchGaps(message = "{constraint.message.noMuchGaps}")
    //Добавить проверку на неодинаковость описаний
    private String descr;

    @Valid
    @Size(min = 1, max = 10, message = "{constraint.message.size}")
    @UniqueElements(message = "{constraint.message.notUniqueElements}")
    @NotNull(message = "{constraint.message.notBlank}")
    private List<@NotNull(message = "{constraint.message.notBlank}") EventOptionResultRequest> results;
}
