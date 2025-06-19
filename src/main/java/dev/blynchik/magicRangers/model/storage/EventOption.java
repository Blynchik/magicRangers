package dev.blynchik.magicRangers.model.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOption {

    private Attributes attribute;
    private String descr;
    private List<EventOptionResult> results;
}
