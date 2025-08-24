package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderSteps {
    private final OrderClient client = new OrderClient();

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return client.create(order);
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrders() {
        return client.getOrders();
    }
}