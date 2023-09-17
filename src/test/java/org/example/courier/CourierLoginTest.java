package org.example.courier;

import io.restassured.response.Response;
import org.example.model.Courier;
import org.example.model.CourierCreds;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.courier.CourierClient;
import org.junit.After;

import static io.restassured.RestAssured.given;
import static org.example.utils.Utils.randomString;
import static org.junit.Assert.assertEquals;

import static org.example.courier.CourierGenerator.randomCourier;
import static org.junit.Assert.*;

public class CourierLoginTest {
    CourierClient courierClient = new CourierClient();
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private String id;
    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Курьер может авторизоваться, успешный запрос возвращает 'id'")
    public void loginWithCorrectData() {
        Courier courier = randomCourier();
        Response createResponse = courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.credsFrom(courier));
        id = loginResponse.body().path("id").toString();
        assertEquals("Курьер не залогинен", 200, loginResponse.statusCode());
        assertNotNull(loginResponse.body().path("id"));
    }

    @Test
    @DisplayName("Если нет поля 'login' запрос возвращает ошибку ")
    public void loginWithoutLoginData() {
        Courier courier = randomCourier();
        Response createResponse = courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.specificLogin(courier, null));
        assertEquals("Неправильный код ответа", 400, loginResponse.statusCode());
        assertEquals("Недостаточно данных для входа", loginResponse.body().path("message"));
    }
    @Test
    @DisplayName("Если нет поля 'password' запрос возвращает ошибку ")
    public void loginWithoutPasswordData() {
        Courier courier = randomCourier();
        Response createResponse = courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.specificPassword(courier, ""));
        assertEquals("Неправильный код ответа", 400, loginResponse.statusCode());
        assertEquals("Недостаточно данных для входа", loginResponse.body().path("message"));
    }

    @Test
    @DisplayName("Запрос с неправильным логином выдает ошибку")
    public void loginIncorrectLoginData(){
        Courier courier = randomCourier();
        Response createResponse = courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.specificLogin(courier, randomString(10)));
        assertEquals("Неправильный код ответа", 404, loginResponse.statusCode());
        assertEquals("Учетная запись не найдена", loginResponse.body().path("message"));
    }

    @Test
    @DisplayName("Запрос с неправильным паролем выдает ошибку")
    public void loginIncorrectPassword(){
        Courier courier = randomCourier();
        Response createResponse = courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.specificPassword(courier, randomString(10)));
        assertEquals("Неправильный код ответа", 404, loginResponse.statusCode());
        assertEquals("Учетная запись не найдена", loginResponse.body().path("message"));
    }

    @After
    public void tearDown(){
        courierClient.delete(id);
    }
}