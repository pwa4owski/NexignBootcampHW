package nexign.bootcamp.util;

import nexign.bootcamp.crm.entity.Abonent;
import nexign.bootcamp.crm.entity.Manager;
import nexign.bootcamp.crm.entity.Tariff;
import nexign.bootcamp.crm.entity.TariffTimeDetails;
import nexign.bootcamp.crm.repository.AbonentRepo;
import nexign.bootcamp.crm.repository.ManagerRepo;
import nexign.bootcamp.crm.repository.TariffRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class DataGenerationService {
    private final AbonentRepo abonentRepo;

    private final TariffRepo tariffRepo;

    private final ManagerRepo managerRepo;

    @Value("${abonents.amount}")
    private Integer abonentsAmount;

    @Value("${managers.amount}")
    private Integer managersAmount;

    public DataGenerationService(AbonentRepo abonentRepo, TariffRepo tariffRepo, ManagerRepo managerRepo) {
        this.abonentRepo = abonentRepo;
        this.tariffRepo = tariffRepo;
        this.managerRepo = managerRepo;
    }

    private void genarateTariffs(){
        var tariff3 = Tariff.builder()
                .id(3)
                .tariffName("Поминутный")
                .incomingMinuteCost(1.5)
                .outcomingMinuteCost(1.5)
                .build();
        tariffRepo.save(tariff3);

        var tariff11TimeDetails = TariffTimeDetails.builder()
                .abonentFee(0)
                .commonMinutesLimit(false)
                .incomingMinutesLimit(0)
                .outcomingMinutesLimit(100)
                .incomingMinuteCost(0.0)
                .outcomingMinuteCost(0.5)
                .build();

        var tariff11 = Tariff.builder()
                .id(11)
                .tariffName("Обычный")
                .incomingMinuteCost(0.0)
                .outcomingMinuteCost(1.5)
                .timeDetails(tariff11TimeDetails)
                .build();

        tariffRepo.save(tariff11);

        var tariff6TimeDetails = TariffTimeDetails.builder()
                .abonentFee(100)
                .incomingMinutesLimit(300)
                .outcomingMinutesLimit(300)
                .commonMinutesLimit(true)
                .incomingMinuteCost(0.0)
                .outcomingMinuteCost(0.0)
                .build();

        var tariff6 = Tariff.builder()
                .id(6)
                .tariffName("Безлимит")
                .incomingMinuteCost(1.0)
                .outcomingMinuteCost(1.0)
                .timeDetails(tariff6TimeDetails)
                .build();

        tariffRepo.save(tariff6);
    }

    private void generateAbonents(int amount){
        Random random = new Random();
        List<Tariff> tariffs = tariffRepo.findAll();
        for(int i = 0; i < amount; i++){
            // создаем переменную для региона телефона
            StringBuilder phoneNumber = new StringBuilder("7");

            // генерируем 10 случайных цифр от 0 до 9 и добавляем их к строке
            for (int j = 0; j < 10; j++) {
                int randomPhone = random.nextInt(10);
                phoneNumber.append(randomPhone);
            }

            int id = random.nextInt(tariffs.size());
            var tariff = tariffs.get(id);

            //случайное дробное от -3000 до 7000 с точностью до сотых
            Double balance = Math.ceil((random.nextDouble(10000) - 3000) * 100) / 100;

            //генерируем случайный восьми символьный пароль
            var bytes = new byte[8];
            random.nextBytes(bytes);
            String password = Base64.getEncoder().encodeToString(bytes);

            var abonent = Abonent.builder()
                    .phoneNumber(phoneNumber.toString())
                    .tariff(tariff)
                    .balance(balance)
                    .password(password)
                    .build();

            abonentRepo.save(abonent);
        }
    }

    private void generateManagers(int amount){
        Random random = new Random();
        for(int i = 0; i < amount; i++){
            String login = "manager"+i;
            //генерируем случайный восьми символьный пароль
            var bytes = new byte[8];
            random.nextBytes(bytes);
            String password = Base64.getEncoder().encodeToString(bytes);

            var manager = Manager.builder()
                    .login(login)
                    .password(password)
                    .build();

            managerRepo.save(manager);
        }
    }

    public void generateData(){
        genarateTariffs();
        generateAbonents(abonentsAmount);
        generateManagers(managersAmount);
    }
}
