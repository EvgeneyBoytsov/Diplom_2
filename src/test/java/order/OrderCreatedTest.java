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

public class OrderCreatedTest {

    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private final OrderClient orderClient = new OrderClient();
    private final Ingredients ingredients = new Ingredients();
    private final OrderChecks orderChecks = new OrderChecks();
    private final Order order = new Order();
    String userAutToken;

    @Test
    @DisplayName("Создание заказа авторизированным пользователем")
    public void checkCreateOrderWithAuthorization() {
        var user = User.randomCreateUser();
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreateUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);

        ingredients.initIngredients(order);

        ValidatableResponse orderResponse = orderClient.createOrderUserByAuthorization(userAutToken, order);
        orderChecks.checkCreateOrderWithAuthorization(orderResponse);
    }

    @Test
    @DisplayName("Создание заказа с невалидным ингредиентом")
    public void checkCreateOrderWithInvalidedIngredients() {
        var user = User.randomCreateUser();
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreateUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);

        ingredients.changeIngredients(order);

        ValidatableResponse orderResponse = orderClient.createOrderUserByAuthorization(userAutToken, order);
        orderChecks.checkCreateInvalidedIngredients(orderResponse);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void checkCreateOrderWithoutIngredients() {
        var user = User.randomCreateUser();
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreateUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);

        ValidatableResponse orderResponse = orderClient.createOrderWithoutIngredients(userAutToken);
        orderChecks.checkCreateOrderWithoutIngredients(orderResponse);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void checkCreateOrderWithoutAuthorization() {
        var user = User.randomCreateUser();
        ValidatableResponse createdResponse = client.createUser(user);
        userAutToken = check.checkCreateUser(createdResponse);

        ingredients.initIngredients(order);

        ValidatableResponse orderResponse = orderClient.createOrderUserWithoutAuthorization(order);
        orderChecks.checkCreateOrderWithoutAuthorization(orderResponse);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        if (userAutToken != null)
            client.deleteUser(StringUtils.substringAfter(userAutToken, " "));
    }
}