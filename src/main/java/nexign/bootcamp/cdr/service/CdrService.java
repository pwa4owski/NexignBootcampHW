package nexign.bootcamp.cdr.service;

import lombok.extern.slf4j.Slf4j;
import nexign.bootcamp.brt.service.BrtService;
import nexign.bootcamp.cdr.model.CDR;
import nexign.bootcamp.crm.dto.AbonentTarrificationResponse;
import nexign.bootcamp.crm.entity.Abonent;
import nexign.bootcamp.crm.repository.AbonentRepo;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@EnableRabbit
@Slf4j
public class CdrService {

    @Value("${cdr.path}")
    private String pathToCdr;

    @Value("${cdr.calls.amount}")
    private Integer callsAmount;

    // диапазон начальной и конечной даты в пределах которых будет генерироваться начало и конец
    // звонка задается в application.properties в формате YYYYMMDDHH24MMSS
    @Value("${cdr.start.date}")
    private String startDate;

    @Value("${cdr.end.date}")
    private String endDate;

    // создаем объекты класса SimpleDateFormat для парсинга даты и форматирования вывода
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private final SimpleDateFormat outputSdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private final long millisecondsInOneHour = 60*60*1000;
    private final long millisecondsInOneDay = 24*millisecondsInOneHour;

    private final BrtService brtService;

    // поля с информацией про абонентов нужны исключительно
    // для генерации записей о звонках пользователей находящихся в данный момент в базе
    private final AbonentRepo abonentRepo;
    private List<String> abonentNumbers = new ArrayList<>();
    @RabbitListener(queues = "abonentCache")
    private void processMessage(String message) {
        //если кэш протух, то очищаем его
        if (message.equals("expired")) {
            log.atInfo().log("cache cleared {}", message);
            abonentNumbers.clear();
            return;
        }
        //если пришел номер телефона, то добавим нового абонента в кэш
        if (!message.matches("\\d{11}")) {
            log.atWarn().log("unexpected message received: {}", message);
            return;
        }
        abonentNumbers.add(message);
        log.atInfo().log("cache updated with number {}", message);
    }


    public CdrService(BrtService brtService, AbonentRepo abonentRepo) {
        this.brtService = brtService;
        this.abonentRepo = abonentRepo;
    }

    private void generate() {
        Random randomize = new Random();
        String formattedDateStart = null, formattedDateEnd=null, callType, phoneNumber;
        ArrayList<CDR> cdrList = new ArrayList<>();


        for(int i=0; i < callsAmount; i++) {

            // генерируем случайное число от 0 до 1
            int randomNumber = randomize.nextInt(2);

            // если случайное число равно 0, то выбираем значение "01", иначе выбираем "02"
            if (randomNumber == 0) {
                callType = "01";
            } else {
                callType = "02";
            }
            //если кэш пуст, идем в БД
            if(abonentNumbers.size() == 0){
                abonentNumbers = new ArrayList<>(abonentRepo.findAll().stream()
                        .map(Abonent::getPhoneNumber).toList());
            }
            // генерируем индекс абонента, номер телефона которого используем.
            int abonentId = randomize.nextInt(abonentNumbers.size() * 4 / 3 +1);

            // если индекс превышает число абонентов "Ромашки", то сгенерим случайный номер
            // другого оператора
            if(abonentId >= abonentNumbers.size()){
                // создаем переменную для региона телефона
                StringBuilder phoneNumberBuilder = new StringBuilder("7");

                // генерируем 10 случайных цифр от 0 до 9 и добавляем их к строке
                for (int j = 0; j < 10; j++) {
                    int randomPhone = randomize.nextInt(10);
                    phoneNumberBuilder.append(randomPhone);
                }
                phoneNumber = phoneNumberBuilder.toString();
            }
            // иначе - выбираем номер существующего абонента
            // вероятность этого варианта около 75%
            else{
                phoneNumber = abonentNumbers.get(abonentId);
            }

            try {
                // парсим даты в формате YYYYMMDDHH24MMSS
                Date start = sdf.parse(startDate);
                Date end = sdf.parse(endDate);

                // генерируем случайное число между начальной и конечной датой в миллисекундах,
                // при это возьмем за данность, что разговор длился не больше суток
                // и вероятность разговора длительностью более часа - 5%
                long randomStart = ThreadLocalRandom.current().nextLong(start.getTime(), end.getTime() + 1);
                int n = randomize.nextInt(100);
                long rightBoarder = randomStart + (n >= 95 ? millisecondsInOneDay : millisecondsInOneHour);
                long randomEnd = ThreadLocalRandom.current().nextLong(randomStart, Long.min(rightBoarder, end.getTime()) + 1);

                // форматируем
                formattedDateStart = outputSdf.format(new Date(randomStart));
                formattedDateEnd = outputSdf.format(new Date(randomEnd));

            } catch (Exception ex) {
                log.atError().log("CDR: " + ex.getMessage());
            }

            var cdrRecord = CDR.builder()
                    .typeOfCall(callType)
                    .number(phoneNumber)
                    .startCall(formattedDateStart)
                    .endCall(formattedDateEnd)
                    .build();

            cdrList.add(cdrRecord);
        }

        // производим запись в файл
        File cdrFile = new File(pathToCdr);
        try (
                FileWriter writer = new FileWriter(cdrFile)
        )
        {
            for (CDR cdr : cdrList) {
                writer.write(cdr.toString() + "\n");
                log.atInfo().log("call added: " + cdr + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AbonentTarrificationResponse> processTariffication(){
        generate();
        return brtService.processTariffication();
    }

}
