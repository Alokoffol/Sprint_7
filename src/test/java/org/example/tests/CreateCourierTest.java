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
    private CourierCredentials credentialsForDeletion;

    @Before
    public void setUp() {
        steps = new CourierSteps();
        credentials = new CourierCredentials(
                "courier_" + System.currentTimeMillis(),
                "password123",
                "Test Courier"
        );
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierSuccessTest() {
        steps.create(credentials)
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));

        // Сохраняем для удаления в @After
        credentialsForDeletion = credentials;
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void createDuplicateCourierFailsTest() {
        // Сначала создаем курьера
        steps.create(credentials)
                .assertThat()
                .statusCode(201);

        // Пытаемся создать такого же курьера
        steps.create(credentials)
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        credentialsForDeletion = credentials;
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void createWithoutLoginTest() {
        CourierCredentials noLogin = new CourierCredentials(
                null,
                "password123",
                "Test Courier"
        );

        steps.create(noLogin)
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void createWithoutPasswordTest() {
        CourierCredentials noPassword = new CourierCredentials(
                "courier_no_pass_" + System.currentTimeMillis(),
                null,
                "Test Courier"
        );

        steps.create(noPassword)
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

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

        credentialsForDeletion = noFirstName;
    }

    @After
    public void tearDown() {
        try {
            if (courierId != -1) {
                steps.delete(courierId);
            }

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
                    System.out.println("Курьер не может быть удален: " + e.getMessage());
                }
            }

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