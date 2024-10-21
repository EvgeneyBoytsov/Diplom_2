package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class OrderChecks {

    /**
     * Метод для проверки ответа на запрос создания заказа
     * @param orderResponse - тело ответа на создание заказа авторизованного пользователя
     */
    @Step("Проверка создания заказа")
    public void checkCreateOrder(ValidatableResponse orderResponse) {
        var body = orderResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа", Set.of("name", "order", "success"), body.keySet());
        assertNotEquals("Поле name пустое",null, body.get("name"));
        assertNotEquals("Поле order пустое",null, body.get("order"));
        assertEquals("Поле success false",true, body.get("success"));

        Map<String, String> order = (Map<String, String>) body.get("order");
        assertEquals("Неверное тело ответа", Set.of("number"), order.keySet());
        assertNotEquals("Поле number пустое",null,order.get("number"));
    }

    /**
     * Метод для проверки ответа на запрос создания заказа без добавления ингредиентов
     * @param orderResponse - тело ответа на создание заказа авторизованного пользователя без ингредиентов
     */
    @Step("Проверка создания заказа без ингредиентов")
    public void checkCreateOrderWithoutIngredients(ValidatableResponse orderResponse) {
        var body = orderResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа", Set.of("success", "message"), body.keySet());
        assertEquals("Поле success true",false, body.get("success"));
        assertEquals("Неверное тело ответа","Ingredient ids must be provided", body.get("message"));
    }

    /**
     * Метод для проверки ответа на запрос создания заказа с несуществующим ингредиентом
     * @param orderResponse - тело ответа на создание заказа авторизованного пользователя
     *                      с несуществующим ингредиентом
     */
    @Step("Проверка создания заказа с невалидным хэшом ингредиента")
    public void checkCreateInvalidedIngredients(ValidatableResponse orderResponse) {
             orderResponse
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Проверка создания заказа без авторизации пользователя")
    public void checkCreateOrderWithoutAuthorization(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HTTP_MOVED_TEMP);
    }

    /**
     * Метод для проверки ответа на запрос получения списка заказов авторизованного пользователя
     * @param getOrdersResponse - тело ответа на получение списка заказов авторизованного пользователя
     */
    @Step("Проверка получения списка заказов авторизованного пользователя")
    public void checkGetListUserOrders(ValidatableResponse getOrdersResponse) {
        var orderResponse = getOrdersResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(OrderResponse.class);
        assertTrue("Поле success false", orderResponse.isSuccess());
        assertNotEquals("Поле orders пустое",null, orderResponse.getOrders());
        assertNotEquals("Поле total пустое",null, orderResponse.getTotal());
        assertNotEquals("Поле totalToday пустое",null, orderResponse.getTotalToday());

        var orders = (List<Order>) orderResponse.getOrders();
        for (Order order : orders) {
            assertNotEquals("Поле ingredients пустое",null, order.getIngredients());
            assertNotEquals("Поле _id пустое",null, order.get_id());
            assertNotEquals("Поле status пустое",null, order.getStatus());
            assertNotEquals("Поле number пустое",null, order.getNumber());
            assertNotEquals("Поле createdAt пустое",null, order.getCreatedAt());
            assertNotEquals("Поле updateAt пустое",null, order.getUpdatedAt());
        }
    }

    /**
     * Метод для проверки ответа на запрос получения списка заказов неавторизованного пользователя
     * @param getOrdersResponse - тело ответа на получение списка заказов неавторизованного пользователя
     */
    @Step("Проверка получения списка заказов неавторизованного пользователя")
    public void checkGetListUserOrdersWithoutAuthorization(ValidatableResponse getOrdersResponse) {
        var body = getOrdersResponse
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа", Set.of("success", "message"), body.keySet());
        assertEquals("Поле success true",false, body.get("success"));
        assertEquals("Неверное тело ответа","You should be authorised", body.get("message"));

    }
}