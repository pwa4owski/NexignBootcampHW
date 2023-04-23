package nexign.bootcamp.crm.controller;

import nexign.bootcamp.crm.dto.AbonentReportResponse;
import nexign.bootcamp.crm.dto.PaymentRequest;
import nexign.bootcamp.crm.dto.PaymentResponse;
import nexign.bootcamp.crm.service.AbonentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abonent")
public class AbonentController {
    private final AbonentService abonentService;

    public AbonentController(AbonentService abonentService) {
        this.abonentService = abonentService;
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong!";
    }

    @PatchMapping("/pay")
    public PaymentResponse pay(@RequestBody @Validated PaymentRequest payment){
        return abonentService.pay(payment);
    }

    @GetMapping("/report/{numberPhone}")
    public AbonentReportResponse getReport(@PathVariable String numberPhone){
        return abonentService.getReport(numberPhone);
    }
}
