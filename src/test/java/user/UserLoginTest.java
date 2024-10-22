package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

public class UserLoginTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userAutToken;
    User defaultUser = new User("logintest@yandex.ru", "123456", "test");
    UserCredentials defaultCredentials = new UserCredentials("login-test@yandex.ru", "123456");

    @Test
    @DisplayName("Авторизация пользователя")
    public void userLogin() {
        var user = User.randomCreatedUser();
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreatedUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным полем Email")
    public void userLoginWrongEmail() {
        User user = defaultUser;
        ValidatableResponse createResponse = client.createUser(user);
        userAutToken = check.checkCreatedUser(createResponse);

        UserCredentials credentials = defaultCredentials.clone();
        credentials.setEmail("Ma");
        credentials.setPassword("123456");

        ValidatableResponse loginResponsesWrongData = client.loginUser(credentials);
        check.checkBadRequestLoginUserData(loginResponsesWrongData);
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным полем Password")
    public void userLoginWrongPassword() {
        User user = defaultUser;
        ValidatableResponse createResponse = client.createUser(user);
        userAutToken = check.checkCreatedUser(createResponse);

        UserCredentials credentials = defaultCredentials.clone();
        credentials.setEmail("logintest@yandex.ru");
        credentials.setPassword("123465");

        ValidatableResponse loginResponsesWrongData = client.loginUser(credentials);
        check.checkBadRequestLoginUserData(loginResponsesWrongData);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        if (userAutToken != null)
            client.deleteUser(StringUtils.substringAfter(userAutToken, " "));
    }
}