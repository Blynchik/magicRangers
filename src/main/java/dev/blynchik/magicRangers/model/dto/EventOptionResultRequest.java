package dev.blynchik.magicRangers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOptionResultRequest {

    private int minDifficulty;
    private String descr;
}
