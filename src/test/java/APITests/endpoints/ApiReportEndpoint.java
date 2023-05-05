package APITests.endpoints;

import dto.AbonentReportResponse;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/report/")
public class ApiReportEndpoint extends BaseEndpoint {
    public void reportRequest(AbonentReportResponse abonentReportResponse) {
        given()
                .auth()
                .basic("74325189429","B+VobxzQEV8=")
                .get(endpoint +"74325189429")
                .then()
                .statusCode(200)
                .body("numberPhone", Matchers.equalTo("74325189429"))
                .extract()
                .as(AbonentReportResponse.class);
    }

}
