package dev.blynchik.magicRangers.model.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Класс для вероятных результатов варианта события.
 * Например, результат на выбор может быть только из списка вероятных результатов
 * Событие: Игра в кости
 * Вероятные результаты для минимальной сложности: Выпало 1, Выпало 2 и т.д, все с одинаковой вероятностью
 * Если в списке вероятных результатов только один результат, то он считается единственно возможным
 * Вероятность для нескольких можно регулировать.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppProbableResult {

    private Double probabilityPercent;
    private String descr;
    private Boolean isFinal;
}
