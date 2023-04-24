package APITests;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("/abonent")
public class ApiAbonentTest {
    @Test
    @DisplayName("/abonent/pay: 200, пополнение счета")
    void patchPayTest() {
        given()
                .auth()
                .basic("71653459394","nTIrhi3A5ec=")
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body("{\n" +
                        "  \"numberPhone\": \"71653459394\",\n" +
                        "  \"money\": 100\n" +
                        "}")
                .log()
                .all()
                .patch("http://localhost:8090/abonent/pay")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("numberPhone", Matchers.equalTo("71653459394"))
                .body("money", Matchers.equalTo(12825.31F));
    }

    @Test
    @DisplayName("/abonent/report/{numberPhone}: 200, проверка истории звонков")
    void getReportTest() {
        given()
                .auth()
                .basic("71653459394","nTIrhi3A5ec=")
                .log()
                .all()
                .get("http://localhost:8090/abonent/report/"+"71653459394")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("numberPhone", Matchers.equalTo("71653459394"));

    }
}
