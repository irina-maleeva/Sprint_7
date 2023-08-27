package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.Order;
import org.example.utils.BaseClient;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDER_URL = "api/v1/orders";

    @Step("Создание заказа")
    public Response create(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post(ORDER_URL);
    }

    @Step("Получение списка заказов курьера")
    public Response getOrdersList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_URL);
    }

    @Step("Получение деталей заказа по номеру отслеживания")
    public Response getOrderDetails(int track) {
        return given()
                .spec(getBaseSpec())
                .and()
                .queryParam("t", track)
                .when()
                .get(ORDER_URL+"/track");
    }

    @Step("Принять заказ курьером")
    public Response acceptOrder(int orderId, int courierId) {
        return given()
                .spec(getBaseSpec())
                .and()
                .queryParam("courierId", courierId)
                .when()
                .get(ORDER_URL + "accept/" + orderId);
    }

    @Step("Отмена заказа")
    public Response cancelOrder(int trackId){
        return given()
                .spec(getBaseSpec())
                .and()
                .queryParam("track", trackId)
                .when()
                .put(ORDER_URL + "/cancel");
    }
}
