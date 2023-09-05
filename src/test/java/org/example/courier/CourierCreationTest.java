package org.example.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.model.Courier;
import org.example.model.CourierCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.example.courier.CourierGenerator.*;
import static org.example.utils.Utils.randomString;
import static org.junit.Assert.assertEquals;

public class CourierCreationTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    CourierClient courierClient = new CourierClient();

    private String id;


    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }
    @Test
    @DisplayName("Курьера можно создать, код ответа 200, запрос возвращает 'ok: true'")
    public void checkCreateCourier(){

        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.credsFrom(courier));
        assertEquals("Неверный статус код", 201, response.statusCode());
        assertEquals(true, response.body().path("ok"));
        id = loginResponse.path("id").toString();
        assertEquals("Курьер не залогинен", 200, loginResponse.statusCode());

    }
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров, код ответа 409, 'message': 'Этот логин уже используется. Попробуйте другой.'")
    public void checkCannotCreateCourierWithSameLogin(){
        Courier courier = randomCourier();

        Response response = courierClient.create(courier);
        Courier sameLoginCourier =  new Courier()
                .withLogin(courier.getLogin())
                .withPassword(randomString(8))
                .withFirstName(randomString(8));
        Response finalResponse = courierClient.create(sameLoginCourier);
        assertEquals("Код не соотвествует ожидаемому 409", 409, finalResponse.statusCode());
        assertEquals("Этот логин уже используется. Попробуйте другой.", finalResponse.body().path("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutLogin(){
        Courier courier = courierWithoutLogin();
        Response response = courierClient.create(courier);
        assertEquals("Код не соотвествует ожидаемому 400", 400, response.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.body().path("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutPassword(){
        Courier courier = courierWithoutPassword();
        Response response = courierClient.create(courier);
        assertEquals("Код не соотвествует ожидаемому 400", 400, response.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.body().path("message"));
    }

    @After
    public void tearDown(){
        courierClient.delete(id);
        }
    }
