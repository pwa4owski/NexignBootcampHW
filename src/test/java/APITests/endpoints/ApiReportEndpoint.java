package APITests.endpoints;

import dto.AbonentReportResponse;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/report/")
public class ApiReportEndpoint extends BaseEndpoint {
    public void reportRequest(AbonentReportResponse abonentReportResponse, String password, Integer code) {
        given()
                .auth()
                .basic(abonentReportResponse.getNumberPhone(), password)
                .get(endpoint + abonentReportResponse.getNumberPhone())
                .then()
                .statusCode(code)
                .body("numberPhone", Matchers.equalTo(abonentReportResponse.getNumberPhone()))
                .extract()
                .as(AbonentReportResponse.class);
    }

}
