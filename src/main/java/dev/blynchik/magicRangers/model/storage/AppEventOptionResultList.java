package dev.blynchik.magicRangers.model.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс для списка вероятных результатов при минимальной сложности
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppEventOptionResultList {

    /**
     * Минимальная сложность, при которой становятся доступные вероятные результаты
     */
    private int minDifficulty;

    /**
     * Список вероятных результатов.
     * Для повышения вероятности ее можно изменять.
     */
    private List<AppProbableResult> probableResults;
}
