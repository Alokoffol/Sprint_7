package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public ValidatableResponse create(CourierCredentials credentials) {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 30000)
                                .setParam("http.socket.timeout", 30000)))
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(BASE_URL + "/api/v1/courier")
                .then();
    }

    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000)))
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(BASE_URL + "/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse delete(int id) {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000)))
                .header("Content-type", "application/json")
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + id)
                .then();
    }
}