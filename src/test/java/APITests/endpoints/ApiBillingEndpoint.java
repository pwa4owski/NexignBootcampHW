package APITests.endpoints;
import static io.restassured.RestAssured.given;

@Endpoint("/manager/billing")
public class ApiBillingEndpoint extends BaseEndpoint {
    public void billingRequest(String login, String password,Integer code) {
        given()
                .auth()
                .basic(login,password)
                .patch(endpoint)
                .then()
                .statusCode(code);
    }
}
