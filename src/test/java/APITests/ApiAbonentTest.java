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
    public String abonentPhone = "75418522523";
    public String abonentPassword= "ln7UN2fgDOc=";
    @Test
    @DisplayName("/abonent/pay: 200, пополнение счета")
    void patchPayTest() {
        new ApiPayEndpoint().paymentRequest(PaymentRequest.builder()
                .money(100.0)
                .numberPhone(abonentPhone)
                .build(),
                abonentPhone,
                abonentPassword,
                200);
    }
    @Test
    @DisplayName("/abonent/report/{numberPhone}: 200, проверка истории звонков")
    void getReportTest() {
        new ApiReportEndpoint().reportRequest(AbonentReportResponse.builder()
                .numberPhone(abonentPhone)
                .build(),
                abonentPassword,
                200);

    }
}
