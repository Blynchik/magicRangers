package dev.blynchik.magicRangers.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AppEventOptionResultResponse {

    private String title;
    private String attribute;
    private String selectedOption;
    private Integer minDifficulty;
    private Integer rolledValue;
    private Integer attributeConstraint;
    private String resultDescr;
}
