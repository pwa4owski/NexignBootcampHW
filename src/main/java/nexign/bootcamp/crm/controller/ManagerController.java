package nexign.bootcamp.crm.controller;

import nexign.bootcamp.crm.dto.*;
import nexign.bootcamp.crm.service.ManagerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PatchMapping("/change-tariff")
    public ChangeTariffResponse changeTariff(@RequestBody @Validated ChangeTariffRequest changeTariffRequest){
        return managerService.changeTariff(changeTariffRequest);
    }

    @PostMapping("/abonent")
    public CreateAbonentResponse createAbonent(@RequestBody @Validated CreateAbonentRequest createAbonentRequest){
        return managerService.createAbonent(createAbonentRequest);
    }

    @PatchMapping("/billing")
    public List<AbonentTarrificationResponse> processBilling(){
        return managerService.processBilling();
    }
}
