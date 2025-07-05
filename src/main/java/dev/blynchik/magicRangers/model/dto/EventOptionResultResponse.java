package dev.blynchik.magicRangers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventOptionResultResponse {

    private String title;
    private String selectedOption;
    private int minDifficulty;
    private String resultDescr;
}
