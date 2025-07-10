package dev.blynchik.magicRangers.model.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Класс для списка вероятных результатов при минимальной сложности
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOptionResultSet {

    /**
     * Минимальная сложность, при которой становятся доступные вероятные результаты
     */
    private int minDifficulty;

    /**
     * Список вероятных результатов.
     * Для повышения вероятности ее можно изменять.
     */
    private Set<ProbableResult> probableResults;
}
