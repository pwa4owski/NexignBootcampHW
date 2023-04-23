package nexign.bootcamp.crm.service;

import nexign.bootcamp.cdr.service.CdrService;
import nexign.bootcamp.crm.dto.*;
import nexign.bootcamp.crm.entity.Abonent;
import nexign.bootcamp.crm.repository.AbonentRepo;
import nexign.bootcamp.crm.repository.TariffRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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

        var abonent = Abonent.builder()
                .balance(createAbonentRequest.getBalance())
                .phoneNumber(createAbonentRequest.getNumberPhone())
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
