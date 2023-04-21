package nexign.bootcamp.cdr.controller;

import nexign.bootcamp.cdr.dto.CDR;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CDRController {
    public void CDRCreator() {
        Random randomize = new Random();
        String formattedDateStart = null, formattedDateEnd=null, phoneCall;
        ArrayList<CDR> cdrList = new ArrayList<>();
        // задаем количество записей
        int amount=50;
        // задаем диапазон начальной и конечной даты в пределах которых будет генерироваться начало и конец звонка
        // в формате YYYYMMDDHH24MMSS
        String startDate = "20220101000000";
        String endDate = "20221231235959";

        for(int i=0; i<=amount; i++) {

            // генерируем случайное число от 0 до 1
            int randomNumber = randomize.nextInt(2);

            // если случайное число равно 0, то выбираем значение "01", иначе выбираем "02"
            if (randomNumber == 0) {
                phoneCall = "01";
            } else {
                phoneCall = "02";
            }

            // выводим выбранное значение на экран
            //System.out.println("Сгенерированный тип вызова: " + phoneCall);

            // создаем переменную для региона телефона
            StringBuilder phone = new StringBuilder("7");

            // генерируем 10 случайных цифр от 0 до 9 и добавляем их к строке
            for (int j = 0; j < 10; j++) {
                int randomPhone = randomize.nextInt(10);
                phone.append(randomPhone);
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

                // генерируем случайное число между начальной и конечной датой в миллисекундах
                long randomStart = ThreadLocalRandom.current().nextLong(start.getTime(), end.getTime() + 1);
                long randomEnd = ThreadLocalRandom.current().nextLong(randomStart, end.getTime() + 1);

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
                FileWriter writer = new FileWriter("src/main/resources/CDRRandom.txt")) {
            for (CDR cdr : cdrList) {
                writer.write(cdr.toString() + "\n");
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

}
