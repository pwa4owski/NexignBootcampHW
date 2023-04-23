package nexign.bootcamp.cdr.service;

import nexign.bootcamp.brt.service.BrtService;
import nexign.bootcamp.cdr.model.CDR;
import nexign.bootcamp.crm.dto.AbonentTarrificationResponse;
import nexign.bootcamp.crm.entity.Abonent;
import nexign.bootcamp.crm.repository.AbonentRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CdrService {

    @Value("${cdr.path}")
    private String pathToCdr;

    private final long millisecondsInOneDay = 24*60*60*1000;

    private final BrtService brtService;

    // поля с информацией про абонентов нужны исключительно
    // для генерации записей о звонках пользователей находящихся в данный момент в базе
    private final AbonentRepo abonentRepo;

    private List<Abonent> abonents;

    public CdrService(BrtService brtService, AbonentRepo abonentRepo) {
        this.brtService = brtService;
        this.abonentRepo = abonentRepo;
    }

    private void generate() {
        this.abonents = abonentRepo.findAll();
        Random randomize = new Random();
        String formattedDateStart = null, formattedDateEnd=null, phoneCall;
        ArrayList<CDR> cdrList = new ArrayList<>();
        // задаем количество записей
        int amount=50;
        // задаем диапазон начальной и конечной даты в пределах которых будет генерироваться начало и конец звонка
        // в формате YYYYMMDDHH24MMSS
        String startDate = "20220101000000";
        String endDate = "20221231235959";

        for(int i=0; i < amount; i++) {

            // генерируем случайное число от 0 до 1
            int randomNumber = randomize.nextInt(2);

            // если случайное число равно 0, то выбираем значение "01", иначе выбираем "02"
            if (randomNumber == 0) {
                phoneCall = "01";
            } else {
                phoneCall = "02";
            }
            // генерируем индекс абонента, номер телефона которого используем.
            int abonentId = randomize.nextInt(abonents.size() * 4 / 3 +1);

            String phone;

            // если индекс превышает число абонентов "Ромашки", то сгенерим случайных номер
            // другого оператора
            if(abonentId >= abonents.size()){
                // создаем переменную для региона телефона
                StringBuilder phoneNumber = new StringBuilder("7");

                // генерируем 9 случайных цифр от 0 до 9 и добавляем их к строке
                for (int j = 1; j < 10; j++) {
                    int randomPhone = randomize.nextInt(10);
                    phoneNumber.append(randomPhone);
                }
                phone = phoneNumber.toString();
            }
            // иначе - выбираем номер существующего абонента
            else{
                phone = abonents.get(abonentId).getPhoneNumber();
            }

            // выводим сгенерированную строку на экран
            //System.out.println("Сгенерированный номер: " + phone);

            // создаем объекты класса SimpleDateFormat для парсинга даты и форматирования вывода
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat outputSdf = new SimpleDateFormat("yyyyMMddHHmmss");

            try {
                // парсим даты в формате YYYYMMDDHH24MMSS
                Date start = sdf.parse(startDate);
                Date end = sdf.parse(endDate);

                // генерируем случайное число между начальной и конечной датой в миллисекундах,
                // при это возьмем за данность, что разговор длился не больше суток
                long randomStart = ThreadLocalRandom.current().nextLong(start.getTime(), end.getTime() + 1);
                long randomEnd = ThreadLocalRandom.current().nextLong(randomStart, Long.min(randomStart+millisecondsInOneDay, end.getTime()) + 1);

                // создаем объекты Date из случайного количества миллисекунд
                Date resultStart = new Date(randomStart);
                Date resultEnd = new Date(randomEnd);

                // форматируем и выводим даты
                //System.out.println("Сгенерированная дата начала: " + outputSdf.format(resultStart));
                //System.out.println("Сгенерированная дата конца: " + outputSdf.format(resultEnd));
                formattedDateStart = outputSdf.format(resultStart);
                formattedDateEnd = outputSdf.format(resultEnd);

            } catch (Exception ex) {
                System.out.println("Ошибка: " + ex.getMessage());
            }

            cdrList.add(new CDR(phoneCall, phone.toString(), formattedDateStart, formattedDateEnd));
        }

        // производим запись в файл
        try (
                FileWriter writer = new FileWriter(pathToCdr)
        )
        {
            for (CDR cdr : cdrList) {
                writer.write(cdr.toString() + "\n");
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
