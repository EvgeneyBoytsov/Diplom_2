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
    User defaultUser = User.randomCreateUser();

    @Test
    @DisplayName("Создание пользователя")
    public void checkCreateUser() {
        ValidatableResponse createdResponse = client.createUser(defaultUser);
        userAutToken = check.checkCreateUser(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем Email")
    public void checkCreateUserWithoutEmail() {
        var user = defaultUser.clone();
        user.setEmail("");

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя без поля Email")
    public void checkCreateUserNullEmail() {
        var user = defaultUser.clone();
        user.setEmail(null);

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем Password")
    public void checkCreateUserWithoutPassword() {
        var user = defaultUser.clone();
        user.setPassword("");

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя без поля Password")
    public void checkCreateUserNullPassword() {
        var user = defaultUser.clone();
        user.setPassword(null);

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем Name")
    public void checkCreateUserWithoutName() {
        var user = defaultUser.clone();
        user.setName("");

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание пользователя без поля Name")
    public void checkCreateUserNullName() {
        var user = defaultUser.clone();
        user.setName(null);

        ValidatableResponse createdResponse = client.createUser(user);
        check.checkRequestWithoutUserData(createdResponse);
    }

    @Test
    @DisplayName("Создание дубликата пользователя")
    public void checkCreateUserDuplicate() {
        ValidatableResponse createdResponse = client.createUser(defaultUser);
        userAutToken = check.checkCreateUser(createdResponse);

        ValidatableResponse createdResponseDuplicate = client.createUser(defaultUser);
        check.checkRequestWithDuplicateUserData(createdResponseDuplicate);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        if (userAutToken != null)
            client.deleteUser(StringUtils.substringAfter(userAutToken, " "));
    }
}