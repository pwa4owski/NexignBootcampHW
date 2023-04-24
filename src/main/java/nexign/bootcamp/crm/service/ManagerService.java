package nexign.bootcamp.crm.service;

import nexign.bootcamp.cdr.service.CdrService;
import nexign.bootcamp.crm.dto.*;
import nexign.bootcamp.crm.entity.Abonent;
import nexign.bootcamp.crm.repository.AbonentRepo;
import nexign.bootcamp.crm.repository.TariffRepo;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class ManagerService {
    private final AbonentRepo abonentRepo;
    private final TariffRepo tariffRepo;

    private final CdrService cdrService;

    public ManagerService(AbonentRepo abonentRepo, TariffRepo tariffRepo, CdrService cdrService) {
        this.abonentRepo = abonentRepo;
        this.tariffRepo = tariffRepo;
        this.cdrService = cdrService;
    }

    public ChangeTariffResponse changeTariff(ChangeTariffRequest changeTariffRequest) {
        var abonent = abonentRepo.findByPhoneNumber(changeTariffRequest.getNumberPhone())
                .orElseThrow(() -> new IllegalArgumentException("пользователь с таким номером телефона не найден"));
        var tariff = tariffRepo.findById(changeTariffRequest.getTariffId())
                .orElseThrow(() -> new IllegalArgumentException("тариф с таким идентификатором отсутствует"));
        abonent.setTariff(tariff);
        abonent = abonentRepo.save(abonent);
        return ChangeTariffResponse.builder()
                .id(abonent.getId())
                .numberPhone(abonent.getPhoneNumber())
                .tariffId(abonent.getTariff().getId())
                .build();
    }

    public CreateAbonentResponse createAbonent(CreateAbonentRequest createAbonentRequest) {
        var tariff = tariffRepo.findById(createAbonentRequest.getTariffId())
                .orElseThrow(() -> new IllegalArgumentException("тариф с таким идентификатором отсутствует"));

        if(abonentRepo.findByPhoneNumber(createAbonentRequest.getNumberPhone()).isPresent()){
            throw new IllegalArgumentException("этот номер телефона уже используется другим абонентом");
        }

        //генерируем случайный восьми символьный пароль
        var bytes = new byte[8];
        new Random().nextBytes(bytes);
        String password = Base64.getEncoder().encodeToString(bytes);

        var abonent = Abonent.builder()
                .balance(createAbonentRequest.getBalance())
                .phoneNumber(createAbonentRequest.getNumberPhone())
                .password(password)
                .tariff(tariff)
                .build();

        abonent = abonentRepo.save(abonent);
        return CreateAbonentResponse.builder()
                .balance(abonent.getBalance())
                .numberPhone(abonent.getPhoneNumber())
                .tariffId(abonent.getTariff().getId())
                .build();
    }

    public List<AbonentTarrificationResponse> processBilling() {
        return cdrService.processTariffication();
    }
}
