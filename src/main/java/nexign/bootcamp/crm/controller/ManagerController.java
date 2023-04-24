package nexign.bootcamp.crm.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nexign.bootcamp.crm.dto.*;
import nexign.bootcamp.crm.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@OpenAPIDefinition(info = @Info(title = "Billing CRM API"))
@SecurityRequirement(name = "basicAuth")
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
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAbonentResponse createAbonent(@RequestBody @Validated CreateAbonentRequest createAbonentRequest){
        return managerService.createAbonent(createAbonentRequest);
    }

    @PatchMapping("/billing")
    public List<AbonentTarrificationResponse> processBilling(){
        return managerService.processBilling();
    }
}
