package org.example.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private Order order;
    int orderTrack;
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public CreateOrderTest(Order order) {
        this.order = order;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {OrderGenerator.getOrderBlackColor()},
                {OrderGenerator.getOrderGreyColor()},
                {OrderGenerator.getOrderTwoColors()},
                {OrderGenerator.getOrderNoColors()}
        };
    }
    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }
    OrderClient orderClient = new OrderClient();
    @Test
    @DisplayName("Тест на создание заказа, код ответа 201, в ответе есть track number")
    public void checkCreateOrder() {

        Response response = orderClient.create(order);
        orderTrack = response.body().path("track");
        assertEquals("Код не соотвествует ожидаемому 201", 201, response.statusCode());
        assertNotNull(response.body().path("track"));
    }
    @After
    public void tearDown(){
        orderClient.cancelOrder(orderTrack);
    }

}