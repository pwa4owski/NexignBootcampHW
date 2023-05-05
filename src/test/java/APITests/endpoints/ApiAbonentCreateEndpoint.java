package APITests.endpoints;

import dto.CreateAbonentRequest;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

@Endpoint("/manager/abonent")
public class ApiAbonentCreateEndpoint extends BaseEndpoint {
    public void abonentCreateRequest(CreateAbonentRequest createAbonentRequest, String login, String password,Integer code) {
        given()
                .auth()
                .basic(login,password)
                .body(createAbonentRequest)
                .post(endpoint)
                .then()
                .statusCode(code)
                .body("numberPhone", Matchers.equalTo(createAbonentRequest.getNumberPhone()))
                .body("tariffId", Matchers.equalTo(createAbonentRequest.getTariffId()))
                .body("balance", Matchers.equalTo((createAbonentRequest.getBalance())));
    }
}
