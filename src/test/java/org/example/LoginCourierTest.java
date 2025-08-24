package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

@DisplayName("Тесты на логин курьера")
public class LoginCourierTest {

    private final CourierSteps steps = new CourierSteps();
    private CourierCredentials credentials;
    private int courierId = -1;

    @Before
    public void setUp() {
        credentials = new CourierCredentials(
                "login_user_" + System.currentTimeMillis(),
                "login_pass",
                "Alice"
        );
        // Создаем курьера перед тестами логина
        steps.create(credentials);
    }

    @After
    public void tearDown() {
        if (courierId != -1) {
            steps.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void loginCourierSuccess() {
        courierId = steps.login(CourierCredentials.fromLogin(credentials.getLogin(), credentials.getPassword()))
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");
    }
// тут баг
    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля")
    public void loginWithoutPasswordFails() {
        CourierCredentials noPass = new CourierCredentials(credentials.getLogin(), null, null);
        steps.login(noPass)
                .assertThat()
                .statusCode(400) // Ожидаем корректный статус по спецификации
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при неправильном логине")
    public void loginWithWrongLoginFails() {
        CourierCredentials wrong = new CourierCredentials("wrong_login_123", credentials.getPassword(), null);
        steps.login(wrong)
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка при неправильном пароле")
    public void loginWithWrongPasswordFails() {
        CourierCredentials wrong = new CourierCredentials(credentials.getLogin(), "wrong_password", null);
        steps.login(wrong)
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка при отсутствии логина")
    public void loginWithoutLoginFails() {
        CourierCredentials noLogin = new CourierCredentials(null, credentials.getPassword(), null);
        steps.login(noLogin)
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}