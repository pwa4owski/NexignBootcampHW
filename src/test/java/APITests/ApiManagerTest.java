package APITests;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("/manager")
public class ApiManagerTest {
    @Test
    @DisplayName("/manager/abonent: 200, создание абонента")
    void postAbonent() {
        given()
                .auth()
                .basic("manager0","Qq4UAr6Ij84=")
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body("{\n" +
                        "  \"numberPhone\": \"79133562233\",\n" +
                        "  \"tariffId\": 3,\n" +
                        "  \"balance\": 200\n" +
                        "}")
                .log()
                .all()
                .post("http://localhost:8090/manager/abonent")
                .prettyPeek()
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
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body("{\n" +
                        "  \"numberPhone\": \"71653459394\",\n" +
                        "  \"tariffId\": 3\n" +
                        "}")
                .log()
                .all()
                .patch("http://localhost:8090/manager/change-tariff")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("numberPhone", Matchers.equalTo("71653459394"))
                .body("tariffId", Matchers.equalTo(3));

    }

    @Test
    @DisplayName("/manager/billing: 200, просмотр баланса")
    void patchBilling() {
        given()
                .auth()
                .basic("manager0","Qq4UAr6Ij84=")
                .contentType(ContentType.JSON)
                .log()
                .all()
                .patch("http://localhost:8090/manager/billing")
                .prettyPeek()
                .then()
                .statusCode(200);
                //.body("numberPhone", Matchers.equalTo("71653459394"))
                //.body("numberPhone", Matchers.hasItem("76056246620"));
                //.body("balance", Matchers.equalTo(12712.81F));

    }
}
