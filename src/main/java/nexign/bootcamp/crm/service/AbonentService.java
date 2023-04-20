package nexign.bootcamp.crm.service;

import nexign.bootcamp.crm.dto.PaymentRequest;
import nexign.bootcamp.crm.dto.PaymentResponse;
import nexign.bootcamp.crm.repository.AbonentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AbonentService {
    private final AbonentRepo abonentRepo;

    public AbonentService(AbonentRepo abonentRepo) {
        this.abonentRepo = abonentRepo;
    }

    @Transactional
    public PaymentResponse pay(PaymentRequest payment) {
        var abonent = abonentRepo.findByPhoneNumber(payment.getNumberPhone())
                .orElseThrow(() -> new IllegalArgumentException("пользователь с таким номером телефона не найден"));
        abonent.setBalance(abonent.getBalance() + payment.getMoney());
        abonent = abonentRepo.save(abonent);
        return PaymentResponse.builder()
                .id(abonent.getId())
                .numberPhone(abonent.getPhoneNumber())
                .money(abonent.getBalance())
                .build();
    }
}
