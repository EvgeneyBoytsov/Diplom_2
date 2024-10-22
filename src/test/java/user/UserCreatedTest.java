package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

public class UserCreatedTest {

    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userAutToken;
    User defaultUser = User.randomCreatedUser();

    @Test
    @DisplayName("Создание пользователя")
    public void userCreated() {
        ValidatableResponse createdResponse = client.createUser(defaultUser);
        userAutToken = check.checkCreatedUser(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем Email")
    public void createdUserWithoutEmail() {
        var user = defaultUser.clone();
        user.setEmail("");

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя без поля Email")
    public void createdUserNullEmail() {
        var user = defaultUser.clone();
        user.setEmail(null);

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем Password")
    public void createdUserWithoutPassword() {
        var user = defaultUser.clone();
        user.setPassword("");

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя без поля Password")
    public void createdUserNullPassword() {
        var user = defaultUser.clone();
        user.setPassword(null);

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем Name")
    public void createdUserWithoutName() {
        var user = defaultUser.clone();
        user.setName("");

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя без поля Name")
    public void createdUserNullName() {
        var user = defaultUser.clone();
        user.setName(null);

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание дубликата пользователя")
    public void createdUserDuplicate() {
        var user = User.randomCreatedUser();
        client.createUser(user);

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithDuplicateUserData(createdResponse);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        if (userAutToken != null)
            client.deleteUser(StringUtils.substringAfter(userAutToken, " "));
    }
}