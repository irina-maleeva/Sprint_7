package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.Courier;
import org.example.model.CourierCreds;
import org.example.utils.BaseClient;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {
    private static final String COURIER_URL = "api/v1/courier";
    private static final String LOGIN_URL = "api/v1/courier/login";
    @Step("Создаем курьера")
    public Response create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post(COURIER_URL);
    }

    @Step("Log-in курьера")
    public  Response login(CourierCreds creds) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(creds)
                .when()
                .post(LOGIN_URL);
    }

    @Step("Удаляем курьера")
    public Response delete(String id){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_URL + "/" + id);
    }
}
