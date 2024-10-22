package order;

import base.Base;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.qameta.allure.model.Parameter.Mode.HIDDEN;

public class OrderClient extends Base {
    public static final String API_INGREDIENTS = "/ingredients";
    public static final String API_ORDERS = "/orders";

    /**
     * Метод с запросом получения списка ингредиентов
     * @return - тело запроса на получение списка ингредиентов
     */
    @Step("Получения списка всех ингредиентов")
    public ValidatableResponse getAllIngredients() {
        return
                specGet()
                .when()
                .get(API_INGREDIENTS)
                .then().log().all();
    }

    /**
     * Метод с запросом создания заказа авторизованного пользователя
     * @param ingredients - список ингредиентов
     * @param autToken - токен авторизации
     * @return - тело запроса на создание заказа авторизованным пользователем
     */
    @Step("Создания заказа авторизованным пользователя")
    public ValidatableResponse createOrderUserByAuthorization(@Param(mode=HIDDEN)String autToken, Order ingredients) {
        return
                specOrder()
                .header("Authorization",autToken)
                .body(ingredients)
                .when()
                .post(API_ORDERS)
                .then().log().all();
    }

    /**
     * Метод с запросом создания заказа авторизованного пользователя без ингредиентов
     * @param autToken - токен авторизации
     * @return - тело запроса на создание заказа авторизованным пользователем без ингредиентов
     */
    @Step("Создания заказа без ингредиентов авторизованным пользователем")
    public ValidatableResponse createOrderWithoutIngredients(@Param(mode=HIDDEN)String autToken) {
        return
                specOrder()
                .header("Authorization",autToken)
                .when()
                .post(API_ORDERS)
                .then().log().all();
    }

    /**
     * Метод с запросом на создание заказа неавторизованного пользователя
     * @param ingredients - список ингредиентов
     * @return - тело запроса на создание заказа неавторизованным пользователем
     */
    @Step("Создания заказа неавторизованным пользователя")
    public ValidatableResponse createOrderUserWithoutAuthorization(Order ingredients) {
        return
                specOrder()
                .body(ingredients)
                .when()
                .post(API_ORDERS)
                .then().log().all();
    }

    /**
     * Метод с запросом получения заказов авторизованного пользователя
     * @param autToken - токен авторизации
     * @return - тело запроса на получение заказов авторизованного пользователя
     */
    @Step("Получения списка всех заказов авторизованного пользователя")
    public ValidatableResponse getAllUserOrdersWithAuthorization(@Param(mode=HIDDEN)String autToken) {
        return
                specGet()
                .header("Authorization",autToken)
                .when()
                .get(API_ORDERS)
                .then().log().all();
    }

    /**
     * Метод с запросом получения заказов неавторизованного пользователя
     * @return - тело запроса на получение заказов неавторизованного пользователя
     */
    @Step("Получения списка всех заказов неавторизованного пользователя")
    public ValidatableResponse getAllUserOrdersWithoutAuthorization() {
        return
                specGet()
                .when()
                .get(API_ORDERS)
                .then().log().all();
    }
}