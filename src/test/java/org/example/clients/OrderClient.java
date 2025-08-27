package org.example.clients;

import io.restassured.response.ValidatableResponse;
import org.example.Endpoints;
import org.example.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    public ValidatableResponse create(Order order) {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 30000)
                                .setParam("http.socket.timeout", 30000)))
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(Endpoints.BASE_URL + Endpoints.ORDER_CREATE)
                .then();
    }

    public ValidatableResponse getOrders() {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000)))
                .get(Endpoints.BASE_URL + Endpoints.ORDER_GET_LIST)
                .then();
    }

    public ValidatableResponse cancel(int trackId) {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000)))
                .header("Content-type", "application/json")
                .when()
                .put(Endpoints.BASE_URL + Endpoints.ORDER_CANCEL + "?track=" + trackId)
                .then();
    }
}