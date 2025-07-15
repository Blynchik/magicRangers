package dev.blynchik.magicRangers.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class AppCharacterResponse {

    private String name;
    private Integer str;
    private Integer intl;
    private Integer cha;
    private LocalDateTime createdAt;
}
