package dev.blynchik.magicRangers.model.dto;

import dev.blynchik.magicRangers.model.storage.Attributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventOptionRequest {

    private Attributes attribute;
    private String descr;
    private List<EventOptionResultRequest> results;
}
