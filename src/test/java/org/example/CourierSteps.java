package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierSteps {
    private final CourierClient client = new CourierClient();

    @Step("Создание курьера")
    public ValidatableResponse create(CourierCredentials credentials) {
        return client.create(credentials);
    }

    @Step("Логин курьера")
    public ValidatableResponse login(CourierCredentials credentials) {
        return client.login(credentials);
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return client.delete(id);
    }
}