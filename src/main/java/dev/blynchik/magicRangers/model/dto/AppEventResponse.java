package dev.blynchik.magicRangers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class AppEventResponse {

    private String title;
    private String descr;
    private Set<AppEventOptionResponse> options;
}