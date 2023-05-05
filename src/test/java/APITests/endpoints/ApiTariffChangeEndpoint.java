package APITests.endpoints;

import dto.ChangeTariffRequest;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

@Endpoint("/manager/change-tariff")
public class ApiTariffChangeEndpoint extends BaseEndpoint {
    public void tariffChangeRequest(ChangeTariffRequest changeTariffRequest) {
        given()
                .auth()
                .basic("manager0","Sbcg5UVGrrY=")
                .body(changeTariffRequest)
                .patch(endpoint)
                .then()
                .statusCode(200)
                .body("numberPhone", Matchers.equalTo("71223562226"))
                .body("tariffId", Matchers.equalTo(6));
    }
}
