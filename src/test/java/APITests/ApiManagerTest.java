package APITests;
import APITests.ext.ApiTestExtension;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;

@DisplayName("/manager")
@ExtendWith(ApiTestExtension.class)
public class ApiManagerTest {
    @Test
    @DisplayName("/manager/abonent: 200, создание абонента")
    void postAbonent() {
        given()
                .auth()
                .basic("manager0","Qq4UAr6Ij84=")
                .body("{\n" +
                        "  \"numberPhone\": \"79133562233\",\n" +
                        "  \"tariffId\": 3,\n" +
                        "  \"balance\": 200\n" +
                        "}")
                .post("/manager/abonent")
                .then()
                .statusCode(200)
                .body("numberPhone", Matchers.equalTo("79133562233"))
                .body("tariffId", Matchers.equalTo(3))
                .body("balance", Matchers.equalTo(200));
    }

    @Test
    @DisplayName("/manager/change-tariff: 200, смена тарифа")
    void patchTariff() {
        given()
                .auth()
                .basic("manager0","Qq4UAr6Ij84=")
                .body("{\n" +
                        "  \"numberPhone\": \"71653459394\",\n" +
                        "  \"tariffId\": 11\n" +
                        "}")
                .patch("/manager/change-tariff")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("numberPhone", Matchers.equalTo("71653459394"))
                .body("tariffId", Matchers.equalTo(11));

    }

    @Test
    @DisplayName("/manager/billing: 200, тарификация")
    void patchBilling() {
        given()
                .auth()
                .basic("manager0","Qq4UAr6Ij84=")
                .patch("/manager/billing")
                .then()
                .statusCode(200);

    }
}
