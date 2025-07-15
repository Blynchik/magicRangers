package dev.blynchik.magicRangers.model.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс для вариантов решения события
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppEventOption {

    /**
     * Аттрибут (STR, INTL, CHA) с помощью которого решается событие
     */
    private AppAttributes attribute;

    /**
     * Описание решения
     */
    private String descr;

    /**
     * Вероятности результатов, если пройдена проверка сложности на атрибут
     * Например, проверка на STR. Вероятности результатов станут доступны при >= EventOptionResult.minDifficulty
     * Вариант: Показать силу (STR)
     * Результат 1: минимальная сложность 50, при x >= 50 станут доступны вероятные результаты
     * Результат 2: минимальная сложность 30, при 50 > x >= 30 станут доступны вероятные результаты
     * Результат 3: минимальная сложность 10, при 30 > x станут доступны вероятные результаты
     * Последний результат будет справедлив для всех проверок, даже если они будут не пройдены.
     * Например, в списке только один результат.
     * Результат 1: минимальная сложность 100, он сработает, даже если проверка будет ниже (например бросок проверки 1),
     * т.к. это единственный возможный результат на событие.
     */
    private List<AppEventOptionResultSet> results;
}
