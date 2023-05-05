package APITests;
import APITests.endpoints.ApiPayEndpoint;
import APITests.endpoints.ApiReportEndpoint;
import APITests.ext.ApiTestExtension;
import dto.AbonentReportResponse;
import dto.PaymentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@DisplayName("/abonent")
@ExtendWith(ApiTestExtension.class)
public class ApiAbonentTest {
    @Test
    @DisplayName("/abonent/pay: 200, пополнение счета")
    void patchPayTest() {
        new ApiPayEndpoint().paymentRequest(PaymentRequest.builder()
                .money(100.0)
                .numberPhone("74325189429")
                .build());
    }

    @Test
    @DisplayName("/abonent/report/{numberPhone}: 200, проверка истории звонков")
    void getReportTest() {
        new ApiReportEndpoint().reportRequest(AbonentReportResponse.builder()
                .build());

    }
}
