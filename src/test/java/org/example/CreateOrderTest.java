package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
@DisplayName("Тесты: Создание заказа с разными цветами")
public class CreateOrderTest {
    private final String[] color;
    private final OrderSteps steps = new OrderSteps();

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
                {null}
        };
    }

    @Test
    @DisplayName("Можно создать заказ с указанным цветом")
    public void createOrderWithColor() {
        Order order = Order.withColor(color);
        steps.create(order)
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }
}