package APITests.endpoints;

import dto.PaymentRequest;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/pay")
public class ApiPayEndpoint extends BaseEndpoint {


    public void paymentRequest(PaymentRequest paymentRequest) {
                given()
                .auth()
                .basic("74325189429","B+VobxzQEV8=")
                .body(paymentRequest)
                .patch(endpoint)
                .then()
                .statusCode(200);
    }

}