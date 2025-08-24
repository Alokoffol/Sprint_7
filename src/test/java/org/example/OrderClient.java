package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public ValidatableResponse create(Order order) {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 30000)
                                .setParam("http.socket.timeout", 30000)))
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(BASE_URL + "/api/v1/orders")
                .then();
    }

    public ValidatableResponse getOrders() {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000)))
                .get(BASE_URL + "/api/v1/orders")
                .then();
    }
}