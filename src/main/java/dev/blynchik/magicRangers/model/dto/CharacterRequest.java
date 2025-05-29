package dev.blynchik.magicRangers.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CharacterRequest {

    @Size(min = 1, max = 255, message = "{constraint.message.size}")
    @NotBlank(message = "{constraint.message.notBlank}")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "{constraint.message.onlyLatinOrCyrillic}")
    private String name;

    @Min(value = 70, message = "{constraint.message.minNum}")
    @Max(value = 180, message = "{constraint.message.maxNum}")
    @NotNull(message = "{constraint.message.notBlank}")
    private Integer str;

    @Min(value = 70, message = "{constraint.message.minNum}")
    @Max(value = 180, message = "{constraint.message.maxNum}")
    @NotNull(message = "{constraint.message.notBlank}")
    private Integer intl;

    @Min(value = 70, message = "{constraint.message.minNum}")
    @Max(value = 180, message = "{constraint.message.maxNum}")
    @NotNull(message = "{constraint.message.notBlank}")
    private Integer cha;
}
