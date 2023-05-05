package APITests.endpoints;
import static io.restassured.RestAssured.given;

@Endpoint("/manager/billing")
public class ApiBillingEndpoint extends BaseEndpoint {
    public void billingRequest() {
        given()
                .auth()
                .basic("manager0","Sbcg5UVGrrY=")
                .patch(endpoint)
                .then()
                .statusCode(200);
    }
}
