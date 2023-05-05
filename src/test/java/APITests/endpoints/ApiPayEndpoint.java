package APITests.endpoints;

import dto.PaymentRequest;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/pay")
public class ApiPayEndpoint extends BaseEndpoint {

    public void paymentRequest(PaymentRequest paymentRequest, String login, String password, Integer code) {
                given()
                .auth()
                .basic(login,password)
                .body(paymentRequest)
                .patch(endpoint)
                .then()
                .statusCode(code);
    }

}