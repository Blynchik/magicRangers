package dev.blynchik.magicRangers.config;

import dev.blynchik.magicRangers.model.storage.Character;
import dev.blynchik.magicRangers.model.storage.Event;
import dev.blynchik.magicRangers.model.storage.EventOption;
import dev.blynchik.magicRangers.model.storage.EventOptionResult;
import dev.blynchik.magicRangers.service.model.CharacterService;
import dev.blynchik.magicRangers.service.model.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static dev.blynchik.magicRangers.model.storage.Attributes.*;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {
    private final CharacterService characterService;
    private final EventService eventService;

    @Autowired
    public DevDataInitializer(CharacterService characterService,
                              EventService eventService) {
        this.characterService = characterService;
        this.eventService = eventService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        characterService.save(new Character(1L, "Барон фон Сладкорыльцев", 100, 100, 100, LocalDateTime.now()));
        eventService.save(new Event("Конкурс талантов",
                """
                                                Вы попали на большой деревенский праздник.
                                                Сельчане готовят большое соревнование, где каждый может проявить себя с лучшей стороны.
                                                Вы решили поучаствовать. Чем удивите публику?
                        """,
                Set.of(
                        new EventOption(STR, "Покажу им свою силу",
                                List.of(
                                        new EventOptionResult(70, "Энергичное выступление из акробатических трюков, прыжков, жонглирования гирями привело публику в восторг. Такого удалого молодца это село еще не видело."),
                                        new EventOptionResult(50, "Поднятие гирь, бочек и даже целой кобылы впечатлило публику. Вы стали примером для многих деревенских мальчишек."),
                                        new EventOptionResult(30, "Поднятие гирь и других тяжелых предметов понравилось публике, но на празднике вы не один такой."),
                                        new EventOptionResult(10, "Демонстрация пресса и бицепсов не впечатлила публику. К тому же лопнувшие штаны сделали вас посмешищем.")
                                )
                        ),
                        new EventOption(INTL, "Расскажу о загадках и секретах Вселенной",
                                List.of(
                                        new EventOptionResult(70, "Интересно рассказанные научные теории понравились сельчанам. Возможно, кто-то из них даже решится получить образование."),
                                        new EventOptionResult(35, "Рассказ о рецептах лечащих отваров и мазей заинтересовало сельчан. Вас явно стали уважать больше."),
                                        new EventOptionResult(10, "Лекция \"О пользе уринотерапии\" заинтересовала слушателей. Вы часто слышали смешки и видели улыбки. Но в деревне вас стали считать чудаком.")
                                )
                        ),
                        new EventOption(CHA, "Спою романтическую балладу",
                                List.of(
                                        new EventOptionResult(40, "Баллада тронула чувства деревенских жителей. А вы теперь являетесь предметом воздыхания юных сельчанок."),
                                        new EventOptionResult(20, "Хорошая плаксивая песня всегда в почете в каком-нибудь кабаке. Вам даже бесплатно налили кружку пива."),
                                        new EventOptionResult(5, "Выйдя на всеобщее обозрение, вы просто забыли слова баллады. Немного потоптавшись, вы сделали вид, что попали сюда случайно. Кажется, на вас даже не обратили внимания.")
                                )
                        )
                ),
                LocalDateTime.now()));
        eventService.save(new Event("Разбойники!",
                """
                                                На большом торговом тракте на вас напал разбойник! Действовать нужно быстро!
                        """,
                Set.of(
                        new EventOption(STR, "Покажу разбойнику, кто тут главный!",
                                List.of(
                                        new EventOptionResult(51, "Выхватив меч, ловким движением вы уклонились от удара ножа, а взмахом меча прекратили грешную жизнь грабителя."),
                                        new EventOptionResult(50, "Не успев вовремя достать меч из ножен, вы и не заметили, как холодное лезвие ножа прикоснулось к вашей шее. Что теперь важнее? Жизнь или кошелек?")
                                )
                        ),
                        new EventOption(INTL, "Попробую подметить слабые места разбойника",
                                List.of(
                                        new EventOptionResult(50, "Разбойник явно надеется поживиться деньгами. Рассказав ему расположение несуществующего клада в обмен на свободу, вы поспешно удалились."),
                                        new EventOptionResult(35, "Разбойник явно надеется поживиться деньгами. Вы попытались рассказать о сокровищах и кладах, но услышав заумные речи, разбойник понимает, что у ученых больших денег никогда не водилось. Вы потеряли лишь, то что заметил грабитель."),
                                        new EventOptionResult(10, "Разбойник явно надеется поживиться деньгами. Вы попытались рассказать о сокровищах, но услышав какую-то несвязную чушь, разбойник решил, что это заразно и поспешно скрылся в тенях. Ошарашенный, вы продолжили свой путь.")
                                )
                        ),
                        new EventOption(CHA, "Попробую убедить разбойника",
                                List.of(
                                        new EventOptionResult(90, "Вы убедили разбойника в том, что вас отправили ему на подмогу сообщники, т.к. следом идет богатый торговец. Как только разбойник отвлекся, вы сбежали от него."),
                                        new EventOptionResult(50, "Вы сделали вид, что приняли разбойника за благородного патрульного. Наградили его парой монет за честный труд и, заговорив ему зубы, оставили позади."),
                                        new EventOptionResult(30, "Вы притворились, что болеете ужасной неизлечимой чумой. Любое ваше прикосновение и даже взгляд может заразить окружающих! Ваши кривляния так напугали разбойника, что он бежал, бросив свой кинжал."),
                                        new EventOptionResult(15, "Ваши мольбы, аргументы, стенания и рыдания не убедили разбойника. Он обобрал вас до нитки.")
                                )
                        )
                ),
                LocalDateTime.now()));
    }
}
