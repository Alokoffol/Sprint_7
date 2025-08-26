package org.example.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.models.Order;
import io.qameta.allure.Description;
import org.example.steps.OrderSteps;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
@DisplayName("Тесты: Создание заказа с разными цветами")
public class CreateOrderTest {
    private final String[] color;
    private final String testName;
    private final OrderSteps steps = new OrderSteps();
    private int trackId = -1;

    public CreateOrderTest(String[] color, String testName) {
        this.color = color;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {1}")
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"BLACK"}, "Черный"},
                {new String[]{"GREY"}, "Серый"},
                {new String[]{"BLACK", "GREY"}, "Оба цвета"},
                {new String[]{}, "Без цвета"},
                {null, "Null цвет"}
        };
    }

    @Test
    @DisplayName("Можно создать заказ с указанным цветом")
    public void createOrderWithColorTest() {
        Order order = Order.withColor(color);
        trackId = steps.create(order)
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue())
                .extract()
                .path("track");
    }

    @After
    public void tearDown() {
        if (trackId != -1) {
            steps.cancel(trackId);
        }
    }
}