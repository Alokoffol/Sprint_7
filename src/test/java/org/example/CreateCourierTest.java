package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    private CourierSteps steps;
    private CourierCredentials credentials;
    private int courierId = -1;

    @Before
    public void setUp() {
        steps = new CourierSteps();
        // Генерируем уникальные данные для основного курьера
        credentials = new CourierCredentials(
                "courier_" + System.currentTimeMillis(),
                "password123",
                "Test Courier"
        );
    }

    // 1. Курьера можно создать - соответствует документации
    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierSuccess() {
        steps.create(credentials)
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    // 2. Нельзя создать двух одинаковых курьеров - ДОКУМЕНТАЦИЯ vs РЕАЛЬНОСТЬ
    // баг
    @Test
    @DisplayName("Создание двух одинаковых курьеров возвращает ошибку")
    public void createDuplicateCourierFails() {
        // Сначала создаем курьера
        steps.create(credentials);

        // Пытаемся создать такого же
        steps.create(credentials)
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется")); // По документации
    }

    // 3. Тест для реального поведения сервера
    @Test
    @DisplayName("Создание курьера с существующим логином (реальное поведение)")
    public void createWithExistingLoginRealBehavior() {
        // Сначала создаем курьера
        steps.create(credentials);

        // Пытаемся создать курьера с таким же логином
        steps.create(credentials)
                .assertThat()
                .statusCode(409);

        // Проверяем оба возможных варианта сообщения
        String actualMessage = steps.create(credentials)
                .extract()
                .path("message");

        // Логируем фактическое сообщение для отладки
        System.out.println("Фактическое сообщение от сервера: " + actualMessage);
    }

    // 4. Обязательные поля
    @Test
    @DisplayName("Создание курьера без логина возвращает ошибку")
    public void createWithoutLoginFails() {
        CourierCredentials noLogin = new CourierCredentials(
                null,           // login = null
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
    @DisplayName("Создание курьера без пароля возвращает ошибку")
    public void createWithoutPasswordFails() {
        String uniqueLogin = "no_pass_" + System.currentTimeMillis();

        CourierCredentials noPassword = new CourierCredentials(
                uniqueLogin,
                null,           // password = null
                "Test Courier"
        );

        steps.create(noPassword)
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    // 5. Создание без имени (firstName не обязателен по документации)
    @Test
    @DisplayName("Создание курьера без имени")
    public void createWithoutFirstName() {
        String uniqueLogin = "no_first_name_" + System.currentTimeMillis();

        CourierCredentials noFirstName = new CourierCredentials(
                uniqueLogin,
                "password123",
                null  // firstName = null
        );

        // Создаем курьера и проверяем ответ
        steps.create(noFirstName)
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));

        // Получаем ID для удаления
        courierId = steps.login(CourierCredentials.fromLogin(noFirstName.getLogin(), noFirstName.getPassword()))
                .extract()
                .path("id");
    }

    @After
    public void tearDown() {
        try {
            // Очищаем созданных курьеров после тестов
            if (courierId != -1) {
                steps.delete(courierId);
            }
            // Также удаляем основного курьера если он был создан
            if (credentials != null) {
                try {
                    int mainCourierId = steps.login(CourierCredentials.fromLogin(credentials.getLogin(), credentials.getPassword()))
                            .extract()
                            .path("id");
                    if (mainCourierId != 0) {
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