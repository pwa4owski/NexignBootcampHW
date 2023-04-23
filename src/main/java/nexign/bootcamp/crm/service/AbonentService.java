package nexign.bootcamp.crm.service;

import nexign.bootcamp.crm.dto.AbonentReportResponse;
import nexign.bootcamp.crm.dto.CallReportDTO;
import nexign.bootcamp.crm.dto.PaymentRequest;
import nexign.bootcamp.crm.dto.PaymentResponse;
import nexign.bootcamp.crm.repository.AbonentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public AbonentReportResponse getReport(String numberPhone, String actualAbonentNumber) throws IllegalAccessException {
        if(!numberPhone.equals(actualAbonentNumber)){
            throw new IllegalAccessException("нельзя запрашивать детализацию звонков не на свой номер телефона!");
        }
        var abonent = abonentRepo.findByPhoneNumber(numberPhone)
                .orElseThrow(() -> new IllegalArgumentException("пользователь с таким номером телефона не найден"));
        List<CallReportDTO> callReports = abonent.getCalls().stream()
                .map(callDetails -> CallReportDTO.builder()
                        .callType(callDetails.getCallType())
                        .startTime(callDetails.getStartTime())
                        .endTime(callDetails.getEndTime())
                        .duration(callDetails.getDuration())
                        .cost(callDetails.getCost())
                        .build())
                .toList();
        // если есть абон.плата, то включаем ее в отчет
        // к сожалению, тут сидит баг - ведь если мы поменяли тариф, то информацию будет неактуальна
        double totalCost = (abonent.getTariff().getTimeDetails() == null ?
                                0 :
                                abonent.getTariff().getTimeDetails().getAbonentFee());
        totalCost += callReports.stream()
                .mapToDouble(CallReportDTO::getCost).sum();

        return AbonentReportResponse.builder()
                .id(abonent.getId())
                .numberPhone(abonent.getPhoneNumber())
                .tariffIndex(abonent.getTariff().getId())
                .payload(callReports)
                .totalCost(totalCost)
                .build();
    }
}
