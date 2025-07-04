package dev.blynchik.magicRangers.model.dto;

import dev.blynchik.magicRangers.validation.annotaion.ValidStringNoMuchGaps;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

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

    @Valid
    @Size(min = 1, max = 10, message = "{constraint.message.size}")
    @UniqueElements(message = "{constraint.message.notUniqueElements}")
    //Добавить проверку на неодинаковость описаний в EventOptionRequest
    private List<@NotNull(message = "{constraint.message.notBlank}") EventOptionRequest> optionRequests;
}
