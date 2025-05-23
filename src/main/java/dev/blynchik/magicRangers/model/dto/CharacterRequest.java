package dev.blynchik.magicRangers.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static dev.blynchik.magicRangers.model.dto.DtoValidationMessage.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CharacterRequest {

    @Size(min = 1, max = 255, message = AT_LEAST_LETTERS + "1")
    @NotBlank(message = NO_BLANK)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = ONLY_LATIN_OR_RUSSIAN)
    private String name;

    @Min(value = 70, message = AT_LEAST_NUM + "70")
    @Max(value = 180, message = NO_MORE_NUM + "180")
    @NotNull(message = AT_LEAST_NUM + "70")
    private Integer str;

    @Min(value = 70, message = AT_LEAST_NUM + "70")
    @Max(value = 180, message = NO_MORE_NUM + "180")
    @NotNull(message = AT_LEAST_NUM + "70")
    private Integer intl;

    @Min(value = 70, message = AT_LEAST_NUM + "70")
    @Max(value = 180, message = NO_MORE_NUM + "180")
    @NotNull(message = AT_LEAST_NUM + "70")
    private Integer cha;
}
