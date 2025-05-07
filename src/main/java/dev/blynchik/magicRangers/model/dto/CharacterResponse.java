package dev.blynchik.magicRangers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CharacterResponse {

    private String name;
    private Integer str;
    private Integer intl;
    private Integer cha;
    private LocalDateTime createdAt;
}
