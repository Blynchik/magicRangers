package dev.blynchik.magicRangers.config;

import dev.blynchik.magicRangers.model.storage.*;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
import dev.blynchik.magicRangers.service.model.AppEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.blynchik.magicRangers.model.storage.AppAttributes.*;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {
    private final AppCharacterService characterService;
    private final AppEventService eventService;

    @Autowired
    public DevDataInitializer(AppCharacterService characterService,
                              AppEventService eventService) {
        this.characterService = characterService;
        this.eventService = eventService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        characterService.save(new AppCharacter(1L, "Барон фон Сладкорыльцефф", 100, 100, 100));
        eventService.save(new AppEvent("Конкурс талантов",
                """
                        Вы попали на большой деревенский праздник.
                        Сельчане готовят большое соревнование, где каждый может проявить себя с лучшей стороны.
                        Вы решили поучаствовать. Чем удивите публику?
                        """,
                List.of(
                        new AppEventOption(STR, "Покажу им свою силу",
                                List.of(
                                        new AppEventOptionResultList(70,
                                                List.of(
                                                        new AppProbableResult(100.0, "Энергичное выступление из акробатических трюков, прыжков, жонглирования гирями привело публику в восторг. Такого удалого молодца это село еще не видело.")
                                                )
                                        ),
                                        new AppEventOptionResultList(50,
                                                List.of(
                                                        new AppProbableResult(100.0, "Поднятие гирь, бочек и даже целой кобылы впечатлило публику. Вы стали примером для многих деревенских мальчишек.")
                                                )
                                        ),
                                        new AppEventOptionResultList(30,
                                                List.of(
                                                        new AppProbableResult(100.0, "Поднятие гирь и других тяжелых предметов понравилось публике, но на празднике вы не один такой.")
                                                )
                                        ),
                                        new AppEventOptionResultList(10,
                                                List.of(
                                                        new AppProbableResult(100.0, "Демонстрация пресса и бицепсов не впечатлила публику. К тому же лопнувшие штаны сделали вас посмешищем.")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(INTL, "Расскажу о загадках и секретах Вселенной",
                                List.of(
                                        new AppEventOptionResultList(70,
                                                List.of(
                                                        new AppProbableResult(100.0, "Интересно рассказанные научные теории понравились сельчанам. Возможно, кто-то из них даже решится получить образование.")
                                                )
                                        ),
                                        new AppEventOptionResultList(35,
                                                List.of(
                                                        new AppProbableResult(100.0, "Рассказ о рецептах лечащих отваров и мазей заинтересовало сельчан. Вас явно стали уважать больше.")
                                                )
                                        ),
                                        new AppEventOptionResultList(10,
                                                List.of(
                                                        new AppProbableResult(100.0, "Лекция \"О пользе уринотерапии\" заинтересовала слушателей. Вы часто слышали смешки и видели улыбки. Но в деревне вас стали считать чудаком.")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(CHA, "Спою романтическую балладу",
                                List.of(
                                        new AppEventOptionResultList(40,
                                                List.of(
                                                        new AppProbableResult(100.0, "Баллада тронула чувства деревенских жителей. А вы теперь являетесь предметом воздыхания юных сельчанок.")
                                                )
                                        ),
                                        new AppEventOptionResultList(20,
                                                List.of(
                                                        new AppProbableResult(100.0, "Хорошая плаксивая песня всегда в почете в каком-нибудь кабаке. Вам даже бесплатно налили кружку пива.")
                                                )
                                        ),
                                        new AppEventOptionResultList(5,
                                                List.of(
                                                        new AppProbableResult(100.0, "Выйдя на всеобщее обозрение, вы просто забыли слова баллады. Немного потоптавшись, вы сделали вид, что попали сюда случайно. Кажется, на вас даже не обратили внимания.")
                                                )
                                        )
                                )
                        )
                )));
        eventService.save(new AppEvent("Разбойники!",
                """
                        На большом торговом тракте на вас напал разбойник! Действовать нужно быстро!
                        """,
                List.of(
                        new AppEventOption(STR, "Покажу разбойнику, кто тут главный!",
                                List.of(
                                        new AppEventOptionResultList(51,
                                                List.of(
                                                        new AppProbableResult(100.0, "Выхватив меч, ловким движением вы уклонились от удара ножа, а взмахом меча прекратили грешную жизнь грабителя.")
                                                )
                                        ),
                                        new AppEventOptionResultList(50,
                                                List.of(
                                                        new AppProbableResult(100.0, "Не успев вовремя достать меч из ножен, вы и не заметили, как холодное лезвие ножа прикоснулось к вашей шее. Что теперь важнее? Жизнь или кошелек?")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(INTL, "Попробую подметить слабые места разбойника",
                                List.of(
                                        new AppEventOptionResultList(50,
                                                List.of(
                                                        new AppProbableResult(100.0, "Разбойник явно надеется поживиться деньгами. Рассказав ему расположение несуществующего клада в обмен на свободу, вы поспешно удалились.")
                                                )
                                        ),
                                        new AppEventOptionResultList(35,
                                                List.of(
                                                        new AppProbableResult(100.0, "Разбойник явно надеется поживиться деньгами. Вы попытались рассказать о сокровищах и кладах, но услышав заумные речи, разбойник понимает, что у ученых больших денег никогда не водилось. Вы потеряли лишь, то что заметил грабитель.")
                                                )
                                        ),
                                        new AppEventOptionResultList(10,
                                                List.of(
                                                        new AppProbableResult(100.0, "Разбойник явно надеется поживиться деньгами. Вы попытались рассказать о сокровищах, но услышав какую-то несвязную чушь, разбойник решил, что это заразно и поспешно скрылся в тенях. Ошарашенный, вы продолжили свой путь.")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(CHA, "Попробую убедить разбойника",
                                List.of(
                                        new AppEventOptionResultList(90,
                                                List.of(
                                                        new AppProbableResult(100.0, "Вы убедили разбойника в том, что вас отправили ему на подмогу сообщники, т.к. следом идет богатый торговец. Как только разбойник отвлекся, вы сбежали от него.")
                                                )
                                        ),
                                        new AppEventOptionResultList(50,
                                                List.of(
                                                        new AppProbableResult(100.0, "Вы сделали вид, что приняли разбойника за благородного патрульного. Наградили его парой монет за честный труд и, заговорив ему зубы, оставили позади.")
                                                )
                                        ),
                                        new AppEventOptionResultList(30,
                                                List.of(
                                                        new AppProbableResult(100.0, "Вы притворились, что болеете ужасной неизлечимой чумой. Любое ваше прикосновение и даже взгляд может заразить окружающих! Ваши кривляния так напугали разбойника, что он бежал, бросив свой кинжал.")
                                                )
                                        ),
                                        new AppEventOptionResultList(15,
                                                List.of(
                                                        new AppProbableResult(100.0, "Ваши мольбы, аргументы, стенания и рыдания не убедили разбойника. Он обобрал вас до нитки.")
                                                )
                                        )
                                )
                        )
                )));
        eventService.save(new AppEvent("Игра в загадки",
                """
                        В поисках сокровищ в горных пещерах, вы попали в плен к подземному чудовищу.
                        Что бы разбавить свое одиночество, оно предложило вам сыграть в загадки.
                        Если вы победите, то вам будет позволено отсюда уйти. Иначе - от вас останутся одни косточки!
                        """,
                List.of(
                        new AppEventOption(INTL, "Вспомню все загадки, услышанные за жизнь, и задам самую сложную",
                                List.of(
                                        new AppEventOptionResultList(50,
                                                List.of(
                                                        new AppProbableResult(100.0, "\"Там есть города, но нет домов. Есть горы, но нет деревьев. Есть вода, но в ней нет рыбы. Что это?\". Чудовище не смогло ответить на эту загадку. Но и вы не уверены, что знаете ответ. Благо, правила игры позволяют не раскрывать тайну.")
                                                )
                                        ),
                                        new AppEventOptionResultList(40,
                                                List.of(
                                                        new AppProbableResult(100.0, "\"Пятеро братьев всегда находятся рядом. Стоят не по росту. У каждого свое имя.\". Чудовище с трудом посчитало до пяти на пальцах. Внезапно его задумчивое выражение морды сменилось на злобную ухмылку. Кажется, вам конец.")
                                                )
                                        ),
                                        new AppEventOptionResultList(20,
                                                List.of(
                                                        new AppProbableResult(100.0, "\"Что нельзя съесть на завтрак?\" В ответ на загадку чудовище ответило, что на обед и на ужин оно вас точно не оставит. Вашей последней мыслью было, что вы не поняли каламбура.")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(INTL, "Сам придумаю все загадки, чтобы чудовище случайно не угадало ответ",
                                List.of(
                                        new AppEventOptionResultList(90,
                                                List.of(
                                                        new AppProbableResult(100.0,
                                                                """
                                                                        "Тридцать белых коней
                                                                        На двух красных холмах —
                                                                        Разбегутся, сшибутся
                                                                        И снова затихнут впотьмах."
                                                                        Чудовище задумалось, но не смогло дать ответ. Через час вы уже улыбались во весь рот на открытом свежем воздухе.
                                                                        """)
                                                )
                                        ),
                                        new AppEventOptionResultList(70,
                                                List.of(
                                                        new AppProbableResult(100.0,
                                                                """
                                                                        "Открылся глаз на зелёном лице,
                                                                        Подмигнул глазу на синем лице:
                                                                        «Мы с тобою похожи, как братья,
                                                                        Только ты высоко, не добраться»."
                                                                        Чудовище задумалось, но не смогло дать ответ. Через час вы уже наслаждались теплом утреннего солнца и вдыхали аромат ромашек.
                                                                        """)
                                                )
                                        ),
                                        new AppEventOptionResultList(50,
                                                List.of(
                                                        new AppProbableResult(100.0,
                                                                """
                                                                        "Без замка, без крышки
                                                                        Сделан сундучок,
                                                                        А внутри хранится
                                                                        Золота кусок."
                                                                        Чудовище заявило, что это кекс с начинкой. Но вы заявили, что начинка в кексе ни при каких обстоятельствах не может быть золотой. Вскоре вы уже были на свободе.
                                                                        """)
                                                )
                                        ),
                                        new AppEventOptionResultList(30,
                                                List.of(
                                                        new AppProbableResult(100.0,
                                                                """
                                                                        "Две ноги
                                                                        На трёх ногах
                                                                        Вдруг четыре прибежали
                                                                        И с безногой убежали"
                                                                        Вы долго придумывали загадку. Чудовище уже начало вас торопить. И вы придумали то, что придумали. Вас спасло только то, что чудовище никогда не слышало подобной загадки.
                                                                        """)
                                                )
                                        ),
                                        new AppEventOptionResultList(10,
                                                List.of(
                                                        new AppProbableResult(100.0,
                                                                """
                                                                        "А что у меня в кармашке?", - ляпнули вы, нервно перебирая безделицы в кармане. Чудовище решило, что это загадка, но так и не смогло дать верный ответ. Потому что ваши пальцы вовремя сложились в фигу.
                                                                        """)
                                                )
                                        )
                                )
                        )
                )));
        eventService.save(new AppEvent("Казино \"Три топора Азиноя\"",
                """
                        Кто такой Азиной? И почему у него три топора? Отбросив глупые вопросы, вы решили испытать свою удачу в игре в рулетку!
                        """,
                List.of(
                        new AppEventOption(CHA, "Ставлю на черное!",
                                List.of(
                                        new AppEventOptionResultList(5,
                                                List.of(
                                                        new AppProbableResult(18.0 / 37.0, "Вы выиграли!"),
                                                        new AppProbableResult(19.0 / 37.0, "Вы проиграли!")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(CHA, "Ставлю на красное!",
                                List.of(
                                        new AppEventOptionResultList(4,
                                                List.of(
                                                        new AppProbableResult(18.0 / 37.0, "Вы выиграли!"),
                                                        new AppProbableResult(19.0 / 37.0, "Вы проиграли!")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(CHA, "Ставлю на зеро!",
                                List.of(
                                        new AppEventOptionResultList(4,
                                                List.of(
                                                        new AppProbableResult(1.0 / 37.0, "Вы сорвали куш!"),
                                                        new AppProbableResult(36.0 / 37.0, "Вы проиграли!")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(STR, "Сделаю ставку и обнесу карманы участников, пока они раззявили рты",
                                List.of(
                                        new AppEventOptionResultList(80,
                                                List.of(
                                                        new AppProbableResult(85.0, "Ха! Вы обнесли этих простаков!"),
                                                        new AppProbableResult(10.0, "Служба безопасности раскрыла ваши планы. Вас избили и выбросили на улицу. Вход в казино теперь вам заказан."),
                                                        new AppProbableResult(5.0, "Один из игроков поймал вас на краже! Служба безопасности задала вам хорошую взбучку. Вход в казино теперь вам заказан.")
                                                )
                                        ),
                                        new AppEventOptionResultList(5,
                                                List.of(
                                                        new AppProbableResult(100.0, "Служба безопасности раскрыла ваши планы. Вас избили и выбросили на улицу. Вход в казино теперь вам заказан."))
                                        )
                                )
                        ),
                        new AppEventOption(INTL, "Просчитаю вероятности и сделаю ставку",
                                List.of(
                                        new AppEventOptionResultList(99,
                                                List.of(
                                                        new AppProbableResult(100.0, "Теория вероятности, острый ум и зоркий глаз сделали свое дело! Теперь вы очень богаты!")
                                                )
                                        ),
                                        new AppEventOptionResultList(5,
                                                List.of(
                                                        new AppProbableResult(100.0, "Служба безопасности раскрыла ваши планы, когда вы считали вероятности на пальцах. Вас избили и выбросили на улицу. Вход в казино теперь вам заказан."))
                                        )
                                )
                        )
                )));
        eventService.save(new AppEvent("Крепкий сон",
                """
                        Вы решили исследовать древний замок. Старожилы говорят, что в нем когда-то обитал вампир. В одной из комнат вы обнаружили богато украшенный гроб. Вскрыв его, вы обнаружили мирно спящего вампира. Как же повезло, что вы прихватили с собой осиновый кол!
                        """,
                List.of(
                        new AppEventOption(STR, "Со всей силы ударю вампира колом в сердце!",
                                List.of(
                                        new AppEventOptionResultList(60,
                                                List.of(
                                                        new AppProbableResult(80.0, "Издав истошный вопль, вампир обратился в прах. Теперь все сокровища вампира ваши!"),
                                                        new AppProbableResult(20.0, "Вы не обратили внимание на то, что вампир одет в кирасу. Кол соскользнул с доспеха, а вампир, открыв глаза, моментально потянулся к вашей шее. Теперь ваша участь вечное служение злу.")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(STR, "Просто ударю вампира в сердце без фанатизма",
                                List.of(
                                        new AppEventOptionResultList(40,
                                                List.of(
                                                        new AppProbableResult(80.0, "Вы не обратили внимание на то, что вампир одет в кирасу. Кол соскользнул с доспеха, а вампир, открыв глаза, моментально потянулся к вашей шее. Теперь ваша участь - вечное служение злу."),
                                                        new AppProbableResult(20.0, "Вы не обратили внимание на то, что вампир одет в кирасу. Поэтому вампир ощутил лишь легкий толчок. Открыв глаза он только спросило текущем годе, но, не получив ожидаемого ответа, в весьма грубой форме послал вас восвояси. Опозоренный, вы не решились доводить дело до конца.")
                                                )
                                        )
                                )
                        ),
                        new AppEventOption(STR, "Растолкаю вампира",
                                List.of(
                                        new AppEventOptionResultList(20,
                                                List.of(
                                                        new AppProbableResult(100.0, "Продрав глаза, вампир одарил вас презрительным взглядом с ног до головы. А затем в весьма грубой форме вы были посланы восвояси. Опозоренный, вы не решились доводить дело до конца.")
                                                )
                                        )
                                )
                        )
                )));
    }
}
