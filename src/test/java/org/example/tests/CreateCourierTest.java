package org.example.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.models.CourierCredentials;
import io.qameta.allure.Description;
import org.example.steps.CourierSteps;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тесты на создание курьера")
public class CreateCourierTest {
    private CourierSteps steps;
    private CourierCredentials credentials;
    private int courierId = -1;
    private CourierCredentials credentialsForDeletion; // ← ДОБАВИЛИ ПЕРЕМЕННУЮ

    @Before
    public void setUp() {
        steps = new CourierSteps();
        credentials = new CourierCredentials(
                "courier_" + System.currentTimeMillis(),
                "password123",
                "Test Courier"
        );
    }

    // ... другие тесты ...

    @Test
    @DisplayName("Создание курьера без имени")
    public void createWithoutFirstNameTest() {
        String uniqueLogin = "no_first_name_" + System.currentTimeMillis();

        CourierCredentials noFirstName = new CourierCredentials(
                uniqueLogin,
                "password123",
                null
        );

        steps.create(noFirstName)
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));

        // ТОЛЬКО ЗАПОМИНАЕМ ДАННЫЕ, удаление будет в @After
        credentialsForDeletion = noFirstName;
    }

    @After
    public void tearDown() {
        try {
            if (courierId != -1) {
                steps.delete(courierId);
            }

            // ↓↓↓ ВЫНЕСЛИ УДАЛЕНИЕ В @After ↓↓↓
            if (credentialsForDeletion != null) {
                try {
                    ValidatableResponse loginResponse = steps.login(
                            CourierCredentials.fromLogin(credentialsForDeletion.getLogin(),
                                    credentialsForDeletion.getPassword()));

                    if (loginResponse.extract().statusCode() == 200) {
                        int id = loginResponse.extract().path("id");
                        steps.delete(id);
                    }
                } catch (Exception e) {
                    System.out.println("Курьер без имени не может быть удален: " + e.getMessage());
                }
            }
            // ↑↑↑ ВЫНЕСЛИ УДАЛЕНИЕ В @After ↑↑↑

            if (credentials != null && credentials.getLogin() != null) {
                try {
                    ValidatableResponse loginResponse = steps.login(
                            CourierCredentials.fromLogin(credentials.getLogin(), credentials.getPassword()));

                    if (loginResponse.extract().statusCode() == 200) {
                        int mainCourierId = loginResponse.extract().path("id");
                        steps.delete(mainCourierId);
                    }
                } catch (Exception e) {
                    System.out.println("Курьер не был создан или уже удален: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении курьера: " + e.getMessage());
        }
    }
}