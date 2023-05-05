package APITests;
import APITests.endpoints.ApiAbonentCreateEndpoint;
import APITests.endpoints.ApiBillingEndpoint;
import APITests.endpoints.ApiTariffChangeEndpoint;
import APITests.ext.ApiTestExtension;
import dto.ChangeTariffRequest;
import dto.CreateAbonentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("/manager")
@ExtendWith(ApiTestExtension.class)
public class ApiManagerTest {
    public String managerName = "manager0";
    public String managerPassword= "gb4HK/2D3YE=";
    @Test
    @DisplayName("/manager/abonent: 200, создание абонента")
    void postAbonent() {
        new ApiAbonentCreateEndpoint().abonentCreateRequest(CreateAbonentRequest.builder()
                .numberPhone("71283162526")
                .tariffId(3)
                .balance(200.0F)
                .build(),
                managerName,
                managerPassword,
                201);
    }
    @Test
    @DisplayName("/manager/change-tariff: 200, смена тарифа")
    void patchTariff() {
        new ApiTariffChangeEndpoint().tariffChangeRequest(ChangeTariffRequest.builder()
                .numberPhone("71223562226")
                .tariffId(6)
                .build(),
                managerName,
                managerPassword,
                200);

    }
    @Test
    @DisplayName("/manager/billing: 200, тарификация")
    void patchBilling() {
        new ApiBillingEndpoint().billingRequest(
                managerName,
                managerPassword,
                200);
    }
}
