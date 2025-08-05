package dev.blynchik.magicRangers.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardResponse {

    private String attribute;
    private Integer value;
}
