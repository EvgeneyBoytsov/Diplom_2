package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;
import user.User;
import user.UserChecks;
import user.UserClient;
import user.UserCredentials;

public class OrderGetTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private final OrderClient orderClient = new OrderClient();
    private final Ingredients ingredients = new Ingredients();
    private final OrderChecks orderChecks = new OrderChecks();
    private final Order order = new Order();
    String userAutToken;

    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void checkGetUserOrders() {
        var user = User.randomCreateUser();
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreateUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);

        ingredients.initIngredients(order);

        orderClient.createOrderUserByAuthorization(userAutToken, order);

        ValidatableResponse getOrdersResponse = orderClient.getAllUserOrdersWithAuthorization(userAutToken);
        orderChecks.checkGetListUserOrders(getOrdersResponse);
    }

    @Test
    @DisplayName("Получение списка заказов неавторизованного пользователя")
    public void checkGetUserOrdersWithoutAuthorization() {
        var user = User.randomCreateUser();
        ValidatableResponse createdResponse = client.createUser(user);
        userAutToken = check.checkCreateUser(createdResponse);

        ingredients.initIngredients(order);

        ValidatableResponse orderResponse = orderClient.createOrderUserWithoutAuthorization(order);
        orderChecks.checkCreateOrderWithoutAuthorization(orderResponse);

        ValidatableResponse getOrdersResponse = orderClient.getAllUserOrdersWithoutAuthorization();
        orderChecks.checkGetListUserOrdersWithoutAuthorization(getOrdersResponse);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        if (userAutToken != null)
            client.deleteUser(StringUtils.substringAfter(userAutToken, " "));
    }
}