package nexign.bootcamp.crm.service;

import nexign.bootcamp.crm.dto.ChangeTariffRequest;
import nexign.bootcamp.crm.dto.ChangeTariffResponse;
import nexign.bootcamp.crm.repository.AbonentRepo;
import nexign.bootcamp.crm.repository.TariffRepo;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private final AbonentRepo abonentRepo;
    private final TariffRepo tariffRepo;

    public ManagerService(AbonentRepo abonentRepo, TariffRepo tariffRepo) {
        this.abonentRepo = abonentRepo;
        this.tariffRepo = tariffRepo;
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
}
