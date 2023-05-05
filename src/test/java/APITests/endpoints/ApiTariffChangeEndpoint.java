package APITests.endpoints;

import dto.ChangeTariffRequest;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

@Endpoint("/manager/change-tariff")
public class ApiTariffChangeEndpoint extends BaseEndpoint {
    public void tariffChangeRequest(ChangeTariffRequest changeTariffRequest, String login, String password, Integer code) {
        given()
                .auth()
                .basic(login, password)
                .body(changeTariffRequest)
                .patch(endpoint)
                .then()
                .statusCode(code)
                .body("numberPhone", Matchers.equalTo(changeTariffRequest.getNumberPhone()))
                .body("tariffId", Matchers.equalTo(changeTariffRequest.getTariffId()));
    }
}
