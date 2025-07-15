package dev.blynchik.magicRangers.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AppEventOptionResultListResponse {

    private String title;
    private String selectedOption;
    private int minDifficulty;
    private String resultDescr;
}
