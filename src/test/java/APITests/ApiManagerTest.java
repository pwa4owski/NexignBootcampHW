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
    @Test
    @DisplayName("/manager/abonent: 200, создание абонента")
    void postAbonent() {
        new ApiAbonentCreateEndpoint().abonentCreateRequest(CreateAbonentRequest.builder()
                .numberPhone("71223562226")
                .tariffId(3)
                .balance(200.0)
                .build());
    }

    @Test
    @DisplayName("/manager/change-tariff: 200, смена тарифа")
    void patchTariff() {
        new ApiTariffChangeEndpoint().tariffChangeRequest(ChangeTariffRequest.builder()
                .numberPhone("71223562226")
                .tariffId(6)
                .build());

    }

    @Test
    @DisplayName("/manager/billing: 200, тарификация")
    void patchBilling() {
        new ApiBillingEndpoint().billingRequest();

    }
}
