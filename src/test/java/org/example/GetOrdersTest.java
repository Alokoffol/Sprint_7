package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

@DisplayName("Тесты: Получение списка заказов")
public class GetOrdersTest {
    private final OrderSteps steps = new OrderSteps();

    @Test
    @DisplayName("Успешный запрос — возвращается список заказов")
    public void getOrdersSuccess() {
        steps.getOrders()
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", instanceOf(java.util.List.class));
    }
}