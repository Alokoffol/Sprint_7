package org.example.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.steps.OrderSteps;
import io.qameta.allure.Description;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

@DisplayName("Тесты: Получение списка заказов")
public class GetOrdersTest {
    private final OrderSteps steps = new OrderSteps();

    @Test
    @DisplayName("Успешный запрос — возвращается список заказов")
    public void getOrdersSuccessTest() {
        steps.getOrders()
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", instanceOf(java.util.List.class));
    }
}