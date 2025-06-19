package dev.blynchik.magicRangers.model.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOptionResult {

    private int minDifficulty;
    private String descr;
}
