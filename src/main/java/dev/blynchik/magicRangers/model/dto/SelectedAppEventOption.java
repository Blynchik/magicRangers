package dev.blynchik.magicRangers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedAppEventOption {
    private String attribute;
    private String descr;
}
