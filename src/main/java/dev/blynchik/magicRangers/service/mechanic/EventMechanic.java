package dev.blynchik.magicRangers.service.mechanic;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.exception.ExceptionMessage;
import dev.blynchik.magicRangers.model.storage.AppAttributes;
import dev.blynchik.magicRangers.model.storage.AppEventOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class EventMechanic {

    /**
     * Метод возвращает вариант, который соответствует атрибуту и описанию среди других вариантов
     */
    public AppEventOption getSelectedOptionFromCurrentEvent(AppAttributes attribute, String descr, List<AppEventOption> options) {
        log.info("Start to get proper option from current event with {} {}", attribute, descr);
        return options.stream()
                .filter(o -> o.getDescr().equals(descr) && o.getAttribute().equals(attribute))
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionMessage.NOT_FOUND.formatted(descr + " " + attribute), NOT_FOUND));
    }
}
