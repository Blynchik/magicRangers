package dev.blynchik.magicRangers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventOptionResultSetResponse {

    private String title;
    private String selectedOption;
    private int minDifficulty;
    private String resultDescr;
}
