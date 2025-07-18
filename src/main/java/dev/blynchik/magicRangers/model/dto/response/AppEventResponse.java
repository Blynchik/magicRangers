package dev.blynchik.magicRangers.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class AppEventResponse {

    private String title;
    private String descr;
    private int commonAttempts;
    private Set<AppEventOptionResponse> options;
}