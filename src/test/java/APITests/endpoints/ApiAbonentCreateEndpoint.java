package APITests.endpoints;

import dto.CreateAbonentRequest;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

@Endpoint("/manager/abonent")
public class ApiAbonentCreateEndpoint extends BaseEndpoint {
    public void abonentCreateRequest(CreateAbonentRequest createAbonentRequest) {
        given()
                .auth()
                .basic("manager0","Sbcg5UVGrrY=")
                .body(createAbonentRequest)
                .post(endpoint)
                .then()
                .statusCode(201)
                .body("numberPhone", Matchers.equalTo("71223562226"))
                .body("tariffId", Matchers.equalTo(3))
                .body("balance", Matchers.equalTo(200.0F));
    }
}
