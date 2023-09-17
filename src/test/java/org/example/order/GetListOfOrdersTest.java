package org.example.order;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.courier.CourierClient;
import org.example.model.Order;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GetListOfOrdersTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private int courierId;
    CourierClient courierClient = new CourierClient();
    OrderClient orderClient = new OrderClient();
    private Order order;
    private int orderTrack;
    private int orderId;
    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;

        Order order = OrderGenerator.getOrderNoColors();

    }
    @Test
    public void checkGetListOfOrders() {
        Response response = orderClient.getOrdersList();
        assertEquals("Код не соотвествует ожидаемому 200", 200, response.statusCode());
        assertNotNull(response.body().path("orders"));
    }
}