package org.example.clients;

import io.restassured.response.ValidatableResponse;
import org.example.Endpoints;
import org.example.models.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public ValidatableResponse create(CourierCredentials credentials) {
        return given()
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 30000)
                                .setParam("http.socket.timeout", 30000)))
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(Endpoints.BASE_URL + Endpoints.COURIER_CREATE)
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
                .post(Endpoints.BASE_URL + Endpoints.COURIER_LOGIN)
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
                .delete(Endpoints.BASE_URL + Endpoints.COURIER_DELETE + id)
                .then();
    }
}