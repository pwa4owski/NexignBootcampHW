package nexign.bootcamp.crm.controller;

import nexign.bootcamp.crm.dto.ChangeTariffRequest;
import nexign.bootcamp.crm.dto.ChangeTariffResponse;
import nexign.bootcamp.crm.service.ManagerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
