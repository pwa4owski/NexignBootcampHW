package APITests;
import APITests.ext.ApiTestExtension;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;

@DisplayName("/abonent")
@ExtendWith(ApiTestExtension.class)
public class ApiAbonentTest {
    @Test
    @DisplayName("/abonent/pay: 200, пополнение счета")
    void patchPayTest() {
        given()
                .auth()
                .basic("71653459394","nTIrhi3A5ec=")
                .body("{\n" +
                        "  \"numberPhone\": \"71653459394\",\n" +
                        "  \"money\": 100\n" +
                        "}")
                .patch("/abonent/pay")
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
                .get("/abonent/report/"+"71653459394")
                .then()
                .statusCode(200)
                .body("numberPhone", Matchers.equalTo("71653459394"));

    }
}
