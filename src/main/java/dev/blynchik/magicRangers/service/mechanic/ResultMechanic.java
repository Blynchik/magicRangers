package dev.blynchik.magicRangers.service.mechanic;

import dev.blynchik.magicRangers.model.dto.request.SelectedAppEventOption;
import dev.blynchik.magicRangers.model.storage.AppEventOption;
import dev.blynchik.magicRangers.model.storage.AppEventOptionResultList;
import dev.blynchik.magicRangers.model.storage.AppProbableResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static dev.blynchik.magicRangers.model.storage.AppAttributes.DECLINE;
import static dev.blynchik.magicRangers.model.storage.AppAttributes.TEXT;

@Service
@Slf4j
public class ResultMechanic {

    private final RollMechanic rollMechanic;

    @Autowired
    public ResultMechanic(RollMechanic rollMechanic) {
        this.rollMechanic = rollMechanic;
    }

    /**
     * Возвращает один из возможных результатов, определенных процентом возможности
     */
    public AppProbableResult getResult(AppEventOptionResultList optionResultList) {
        log.info("Start to define result from {}", optionResultList);
        List<AppProbableResult> probableResults = optionResultList.getProbableResults();
        if (probableResults.size() == 1) {
            return probableResults.get(0);
        }
        // сделать привязку с проверкой при создании события
        int randomValue = rollMechanic.roll(0, 1_000_000);
        // суммируем все вероятности
        double totalProbability = probableResults.stream()
                .mapToDouble(r -> r.getProbabilityPercent() != null ? r.getProbabilityPercent() : 0.0)
                .sum();
        // делим на максимальную точность
        double scale = 1_000_000.0 / totalProbability;
        int currentUpperBound = 0;
        for (AppProbableResult result : probableResults) {
            double probability = result.getProbabilityPercent() != null ? result.getProbabilityPercent() : 0.0;
            // получаем ранги для каждого результата
            int range = (int) (probability * scale);
            // если число попало в ранг, то возвращаем этот результат
            if (randomValue < currentUpperBound + range) {
                return result;
            }
            // иначе переходим к следующему рангу
            currentUpperBound += range;
        }
        // возвращаем самое последнее, если не попало в ранг
        return probableResults.get(probableResults.size() - 1);
    }

    /**
     * Метод возвращает возможные результаты, которые могут быть после выбора варианта.
     * Возвращает тот, который будет первым в отсортированном списке (<=) по сложности
     * Если таких нет, то вернет самый простой из всех.
     * Уменьшает количество попыток у варианта
     */
    public AppEventOptionResultList getResultList(AppEventOption eventOption, SelectedAppEventOption selectedOption, Integer rolledValue) {
        log.info("Start to select probable results from {} with {} and rolled value {}", eventOption, selectedOption, rolledValue);
        AppEventOptionResultList appEventOptionResultList;
        // если бросок считать нужно
        if (!selectedOption.getAttribute().equals(TEXT.name()) && !selectedOption.getAttribute().equals(DECLINE.name()) && rolledValue != null) {
            // сортируем результаты по сложности
            List<AppEventOptionResultList> list = eventOption.getResults().stream()
                    .sorted(Comparator.comparingInt(AppEventOptionResultList::getMinDifficulty).reversed())
                    .toList();
            appEventOptionResultList = list.stream()
                    // оставляем только те, которые меньше или равны выброшенному значению
                    .filter(result -> result.getMinDifficulty() <= rolledValue)
                    // выбираем самый первый результат
                    .max(Comparator.comparingInt(AppEventOptionResultList::getMinDifficulty))
                    // если бросок оказался меньше, ем минимальная сложность для получения результата, то возвращаем самое легкое из списка
                    .orElse(list.get(list.size() - 1));
        } else {
            // если не нужно, то возвращаем самый первый результат
            appEventOptionResultList = eventOption.getResults().get(0);
        }
        // уменьшаем количество попыток на 1 для выбранного варианта
        eventOption.setRemainingAttempts(eventOption.getRemainingAttempts() - 1);
        return appEventOptionResultList;
    }
}
