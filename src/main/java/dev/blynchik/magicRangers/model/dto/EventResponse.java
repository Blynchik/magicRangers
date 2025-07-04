package dev.blynchik.magicRangers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class EventResponse {

    private String title;
    private String descr;
    private Set<EventOptionResponse> options;
}