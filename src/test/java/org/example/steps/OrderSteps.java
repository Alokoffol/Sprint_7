package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.clients.OrderClient;
import org.example.models.Order;

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

    @Step("Отмена заказа с track ID: {trackId}")
    public ValidatableResponse cancel(int trackId) {
        return client.cancel(trackId);
    }
}