package dev.blynchik.magicRangers.config;

import dev.blynchik.magicRangers.model.storage.Character;
import dev.blynchik.magicRangers.model.storage.*;
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
        characterService.save(new Character(1L, "Барон фон Сладкорыльцефф", 100, 100, 100, LocalDateTime.now()));
        eventService.save(new Event("Конкурс талантов",
                """
                        Вы попали на большой деревенский праздник.
                        Сельчане готовят большое соревнование, где каждый может проявить себя с лучшей стороны.
                        Вы решили поучаствовать. Чем удивите публику?
                        """,
                Set.of(
                        new EventOption(STR, "Покажу им свою силу",
                                List.of(
                                        new EventOptionResultSet(70,
                                                Set.of(
                                                        new ProbableResult(100.0, "Энергичное выступление из акробатических трюков, прыжков, жонглирования гирями привело публику в восторг. Такого удалого молодца это село еще не видело.")
                                                )
                                        ),
                                        new EventOptionResultSet(50,
                                                Set.of(
                                                        new ProbableResult(100.0, "Поднятие гирь, бочек и даже целой кобылы впечатлило публику. Вы стали примером для многих деревенских мальчишек.")
                                                )
                                        ),
                                        new EventOptionResultSet(30,
                                                Set.of(
                                                        new ProbableResult(100.0, "Поднятие гирь и других тяжелых предметов понравилось публике, но на празднике вы не один такой.")
                                                )
                                        ),
                                        new EventOptionResultSet(10,
                                                Set.of(
                                                        new ProbableResult(100.0, "Демонстрация пресса и бицепсов не впечатлила публику. К тому же лопнувшие штаны сделали вас посмешищем.")
                                                )
                                        )
                                )
                        ),
                        new EventOption(INTL, "Расскажу о загадках и секретах Вселенной",
                                List.of(
                                        new EventOptionResultSet(70,
                                                Set.of(
                                                        new ProbableResult(100.0, "Интересно рассказанные научные теории понравились сельчанам. Возможно, кто-то из них даже решится получить образование.")
                                                )
                                        ),
                                        new EventOptionResultSet(35,
                                                Set.of(
                                                        new ProbableResult(100.0, "Рассказ о рецептах лечащих отваров и мазей заинтересовало сельчан. Вас явно стали уважать больше.")
                                                )
                                        ),
                                        new EventOptionResultSet(10,
                                                Set.of(
                                                        new ProbableResult(100.0, "Лекция \"О пользе уринотерапии\" заинтересовала слушателей. Вы часто слышали смешки и видели улыбки. Но в деревне вас стали считать чудаком.")
                                                )
                                        )
                                )
                        ),
                        new EventOption(CHA, "Спою романтическую балладу",
                                List.of(
                                        new EventOptionResultSet(40,
                                                Set.of(
                                                        new ProbableResult(100.0, "Баллада тронула чувства деревенских жителей. А вы теперь являетесь предметом воздыхания юных сельчанок.")
                                                )
                                        ),
                                        new EventOptionResultSet(20,
                                                Set.of(
                                                        new ProbableResult(100.0, "Хорошая плаксивая песня всегда в почете в каком-нибудь кабаке. Вам даже бесплатно налили кружку пива.")
                                                )
                                        ),
                                        new EventOptionResultSet(5,
                                                Set.of(
                                                        new ProbableResult(100.0, "Выйдя на всеобщее обозрение, вы просто забыли слова баллады. Немного потоптавшись, вы сделали вид, что попали сюда случайно. Кажется, на вас даже не обратили внимания.")
                                                )
                                        )
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
                                        new EventOptionResultSet(51,
                                                Set.of(
                                                        new ProbableResult(100.0, "Выхватив меч, ловким движением вы уклонились от удара ножа, а взмахом меча прекратили грешную жизнь грабителя.")
                                                )
                                        ),
                                        new EventOptionResultSet(50,
                                                Set.of(
                                                        new ProbableResult(100.0, "Не успев вовремя достать меч из ножен, вы и не заметили, как холодное лезвие ножа прикоснулось к вашей шее. Что теперь важнее? Жизнь или кошелек?")
                                                )
                                        )
                                )
                        ),
                        new EventOption(INTL, "Попробую подметить слабые места разбойника",
                                List.of(
                                        new EventOptionResultSet(50,
                                                Set.of(
                                                        new ProbableResult(100.0, "Разбойник явно надеется поживиться деньгами. Рассказав ему расположение несуществующего клада в обмен на свободу, вы поспешно удалились.")
                                                )
                                        ),
                                        new EventOptionResultSet(35,
                                                Set.of(
                                                        new ProbableResult(100.0, "Разбойник явно надеется поживиться деньгами. Вы попытались рассказать о сокровищах и кладах, но услышав заумные речи, разбойник понимает, что у ученых больших денег никогда не водилось. Вы потеряли лишь, то что заметил грабитель.")
                                                )
                                        ),
                                        new EventOptionResultSet(10,
                                                Set.of(
                                                        new ProbableResult(100.0, "Разбойник явно надеется поживиться деньгами. Вы попытались рассказать о сокровищах, но услышав какую-то несвязную чушь, разбойник решил, что это заразно и поспешно скрылся в тенях. Ошарашенный, вы продолжили свой путь.")
                                                )
                                        )
                                )
                        ),
                        new EventOption(CHA, "Попробую убедить разбойника",
                                List.of(
                                        new EventOptionResultSet(90,
                                                Set.of(
                                                        new ProbableResult(100.0, "Вы убедили разбойника в том, что вас отправили ему на подмогу сообщники, т.к. следом идет богатый торговец. Как только разбойник отвлекся, вы сбежали от него.")
                                                )
                                        ),
                                        new EventOptionResultSet(50,
                                                Set.of(
                                                        new ProbableResult(100.0, "Вы сделали вид, что приняли разбойника за благородного патрульного. Наградили его парой монет за честный труд и, заговорив ему зубы, оставили позади.")
                                                )
                                        ),
                                        new EventOptionResultSet(30,
                                                Set.of(
                                                        new ProbableResult(100.0, "Вы притворились, что болеете ужасной неизлечимой чумой. Любое ваше прикосновение и даже взгляд может заразить окружающих! Ваши кривляния так напугали разбойника, что он бежал, бросив свой кинжал.")
                                                )
                                        ),
                                        new EventOptionResultSet(15,
                                                Set.of(
                                                        new ProbableResult(100.0, "Ваши мольбы, аргументы, стенания и рыдания не убедили разбойника. Он обобрал вас до нитки.")
                                                )
                                        )
                                )
                        )
                ),
                LocalDateTime.now()));
        eventService.save(new Event("Игра в загадки",
                """
                        В поисках сокровищ в горных пещерах, вы попали в плен к подземному чудовищу.
                        Что бы разбавить свое одиночество, оно предложило вам сыграть в загадки.
                        Если вы победите, то вам будет позволено отсюда уйти. Иначе - от вас останутся одни косточки!
                        """,
                Set.of(
                        new EventOption(INTL, "Вспомню все загадки, услышанные за жизнь, и задам самую сложную",
                                List.of(
                                        new EventOptionResultSet(50,
                                                Set.of(
                                                        new ProbableResult(100.0, "\"Там есть города, но нет домов. Есть горы, но нет деревьев. Есть вода, но в ней нет рыбы. Что это?\". Чудовище не смогло ответить на эту загадку. Но и вы не уверены, что знаете ответ. Благо, правила игры позволяют не раскрывать тайну.")
                                                )
                                        ),
                                        new EventOptionResultSet(40,
                                                Set.of(
                                                        new ProbableResult(100.0, "\"Пятеро братьев всегда находятся рядом. Стоят не по росту. У каждого свое имя.\". Чудовище с трудом посчитало до пяти на пальцах. Внезапно его задумчивое выражение морды сменилось на злобную ухмылку. Кажется, вам конец.")
                                                )
                                        ),
                                        new EventOptionResultSet(20,
                                                Set.of(
                                                        new ProbableResult(100.0, "\"Что нельзя съесть на завтрак?\" В ответ на загадку чудовище ответило, что на обед и на ужин оно вас точно не оставит. Вашей последней мыслью было, что вы не поняли каламбура.")
                                                )
                                        )
                                )
                        ),
                        new EventOption(INTL, "Сам придумаю все загадки, чтобы чудовище случайно не угадало ответ",
                                List.of(
                                        new EventOptionResultSet(90,
                                                Set.of(
                                                        new ProbableResult(100.0,
                                                                """
                                                                        "Тридцать белых коней
                                                                        На двух красных холмах —
                                                                        Разбегутся, сшибутся
                                                                        И снова затихнут впотьмах."
                                                                        Чудовище задумалось, но не смогло дать ответ. Через час вы уже улыбались во весь рот на открытом свежем воздухе.
                                                                        """)
                                                )
                                        ),
                                        new EventOptionResultSet(70,
                                                Set.of(
                                                        new ProbableResult(100.0,
                                                                """
                                                                        "Открылся глаз на зелёном лице,
                                                                        Подмигнул глазу на синем лице:
                                                                        «Мы с тобою похожи, как братья,
                                                                        Только ты высоко, не добраться»."
                                                                        Чудовище задумалось, но не смогло дать ответ. Через час вы уже наслаждались теплом утреннего солнца и вдыхали аромат ромашек.
                                                                        """)
                                                )
                                        ),
                                        new EventOptionResultSet(50,
                                                Set.of(
                                                        new ProbableResult(100.0,
                                                                """
                                                                        "Без замка, без крышки
                                                                        Сделан сундучок,
                                                                        А внутри хранится
                                                                        Золота кусок."
                                                                        Чудовище заявило, что это кекс с начинкой. Но вы заявили, что начинка в кексе ни при каких обстоятельствах не может быть золотой. Вскоре вы уже были на свободе.
                                                                        """)
                                                )
                                        ),
                                        new EventOptionResultSet(30,
                                                Set.of(
                                                        new ProbableResult(100.0,
                                                                """
                                                                        "Две ноги
                                                                        На трёх ногах
                                                                        Вдруг четыре прибежали
                                                                        И с безногой убежали"
                                                                        Вы долго придумывали загадку. Чудовище уже начало вас торопить. И вы придумали то, что придумали. Вас спасло только то, что чудовище никогда не слышало подобной загадки.
                                                                        """)
                                                )
                                        ),
                                        new EventOptionResultSet(10,
                                                Set.of(
                                                        new ProbableResult(100.0,
                                                                """
                                                                        "А что у меня в кармашке?", - ляпнули вы, нервно перебирая безделицы в кармане. Чудовище решило, что это загадка, но так и не смогло дать верный ответ. Потому что ваши пальцы вовремя сложились в фигу.
                                                                        """)
                                                )
                                        )
                                )
                        )
                ),
                LocalDateTime.now()));
        eventService.save(new Event("Казино \"Три топора Азиноя\"",
                """
                        Кто такой Азиной? И почему у него три топора? Отбросив глупые вопросы, вы решили испытать свою удачу в игре в рулетку!
                        """,
                Set.of(
                        new EventOption(CHA, "Ставлю на черное!",
                                List.of(
                                        new EventOptionResultSet(5,
                                                Set.of(
                                                        new ProbableResult(18.0 / 37.0, "Вы выиграли!"),
                                                        new ProbableResult(19.0 / 37.0, "Вы проиграли!")
                                                )
                                        )
                                )
                        ),
                        new EventOption(CHA, "Ставлю на красное!",
                                List.of(
                                        new EventOptionResultSet(4,
                                                Set.of(
                                                        new ProbableResult(18.0 / 37.0, "Вы выиграли!"),
                                                        new ProbableResult(19.0 / 37.0, "Вы проиграли!")
                                                )
                                        )
                                )
                        ),
                        new EventOption(CHA, "Ставлю на зеро!",
                                List.of(
                                        new EventOptionResultSet(4,
                                                Set.of(
                                                        new ProbableResult(1.0 / 37.0, "Вы сорвали куш!"),
                                                        new ProbableResult(36.0 / 37.0, "Вы проиграли!")
                                                )
                                        )
                                )
                        ),
                        new EventOption(STR, "Сделаю ставку и обнесу карманы участников, пока они раззявили рты",
                                List.of(
                                        new EventOptionResultSet(80,
                                                Set.of(
                                                        new ProbableResult(85.0, "Ха! Вы обнесли этих простаков!"),
                                                        new ProbableResult(10.0, "Служба безопасности раскрыла ваши планы. Вас избили и выбросили на улицу. Вход в казино теперь вам заказан."),
                                                        new ProbableResult(5.0, "Один из игроков поймал вас на краже! Служба безопасности задала вам хорошую взбучку. Вход в казино теперь вам заказан.")
                                                )
                                        ),
                                        new EventOptionResultSet(5,
                                                Set.of(
                                                        new ProbableResult(100.0, "Служба безопасности раскрыла ваши планы. Вас избили и выбросили на улицу. Вход в казино теперь вам заказан."))
                                        )
                                )
                        ),
                        new EventOption(INTL, "Просчитаю вероятности и сделаю ставку",
                                List.of(
                                        new EventOptionResultSet(99,
                                                Set.of(
                                                        new ProbableResult(100.0, "Теория вероятности, острый ум и зоркий глаз сделали свое дело! Теперь вы очень богаты!")
                                                )
                                        ),
                                        new EventOptionResultSet(5,
                                                Set.of(
                                                        new ProbableResult(100.0, "Служба безопасности раскрыла ваши планы, когда вы считали вероятности на пальцах. Вас избили и выбросили на улицу. Вход в казино теперь вам заказан."))
                                        )
                                )
                        )
                ),
                LocalDateTime.now()));
        eventService.save(new Event("Крепкий сон",
                """
                        Вы решили исследовать древний замок. Старожилы говорят, что в нем когда-то обитал вампир. В одной из комнат вы обнаружили богато украшенный гроб. Вскрыв его, вы обнаружили мирно спящего вампира. Как же повезло, что вы прихватили с собой осиновый кол!
                        """,
                Set.of(
                        new EventOption(STR, "Со всей силы ударю вампира колом в сердце!",
                                List.of(
                                        new EventOptionResultSet(60,
                                                Set.of(
                                                        new ProbableResult(80.0, "Издав истошный вопль, вампир обратился в прах. Теперь все сокровища вампира ваши!"),
                                                        new ProbableResult(20.0, "Вы не обратили внимание на то, что вампир одет в кирасу. Кол соскользнул с доспеха, а вампир, открыв глаза, моментально потянулся к вашей шее. Теперь ваша участь вечное служение злу.")
                                                )
                                        )
                                )
                        ),
                        new EventOption(STR, "Просто ударю вампира в сердце без фанатизма",
                                List.of(
                                        new EventOptionResultSet(40,
                                                Set.of(
                                                        new ProbableResult(80.0, "Вы не обратили внимание на то, что вампир одет в кирасу. Кол соскользнул с доспеха, а вампир, открыв глаза, моментально потянулся к вашей шее. Теперь ваша участь вечное служение злу."),
                                                        new ProbableResult(20.0, "Вы не обратили внимание на то, что вампир одет в кирасу. Поэтому вампир ощутил лишь легкий толчок. Открыв глаза он только спросило текущем годе, но, не получив ожидаемого ответа, в весьма грубой форме послал вас восвояси. Опозоренный, вы не решились доводить дело до конца.")
                                                )
                                        )
                                )
                        ),
                        new EventOption(STR, "Растолкаю вампира",
                                List.of(
                                        new EventOptionResultSet(20,
                                                Set.of(
                                                        new ProbableResult(100.0, "Продрав глаза, вампир одарил вас презрительным взглядом с ног до головы. А затем в весьма грубой форме вы были посланы восвояси. Опозоренный, вы не решились доводить дело до конца.")
                                                )
                                        )
                                )
                        )
                ),
                LocalDateTime.now()));
    }
}
