package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

public class UserUpdateDataTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userAutToken;
    User defaultUser = new User("test-updatet@yandex.ru", "123456", "test");

    @Test
    @DisplayName("Обновление данных авторизованного пользователя")
    public void checkUpdateDataUserWithAuthorization() {
        User user = defaultUser;
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreateUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);

        User userUpdate = defaultUser.clone();
        userUpdate.setEmail("test-updatedata@yandex.ru");
        userUpdate.setName("123456");

        ValidatableResponse createdResponseUpdate = client.updateUserData(userAutToken,userUpdate);
        check.checkUpdateUserData(createdResponseUpdate);

        var autUpdate = UserCredentials.fromUserData(userUpdate);
        ValidatableResponse loginResponseUpdate = client.loginUser(autUpdate);
        userAutToken = check.checkLogInUser(loginResponseUpdate);
    }

    @Test
    @DisplayName("Обновление поля Email авторизованного пользователя")
    public void checkUpdateEmailUser() {
        User user = defaultUser;
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreateUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);

        User userUpdate = defaultUser.clone();
        userUpdate.setEmail("test-updatedata@yandex.ru");

        ValidatableResponse createdResponseUpdate = client.updateUserData(userAutToken,userUpdate);
        check.checkUpdateUserData(createdResponseUpdate);

        var autUpdate = UserCredentials.fromUserData(userUpdate);
        ValidatableResponse loginResponseUpdate = client.loginUser(autUpdate);
        userAutToken = check.checkLogInUser(loginResponseUpdate);
    }

    @Test
    @DisplayName("Обновление поля Name авторизованного пользователя")
    public void checkUpdateNameUser() {
        User user = defaultUser;
        ValidatableResponse createdResponse = client.createUser(user);
        check.checkCreateUser(createdResponse);

        var userCredentials = UserCredentials.fromUserData(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        userAutToken = check.checkLogInUser(loginResponse);

        User userUpdate = defaultUser.clone();
        userUpdate.setName("123456");

        ValidatableResponse createdResponseUpdate = client.updateUserData(userAutToken,userUpdate);
        check.checkUpdateUserData(createdResponseUpdate);

        var autUpdate = UserCredentials.fromUserData(userUpdate);
        ValidatableResponse loginResponseUpdate = client.loginUser(autUpdate);
        userAutToken = check.checkLogInUser(loginResponseUpdate);
    }

    @Test
    @DisplayName("Обновление данных неавторизованного пользователя")
    public void checkUpdateDataUserWithoutLogin() {
        User user = defaultUser;
        ValidatableResponse createdResponse = client.createUser(user);
        userAutToken = check.checkCreateUser(createdResponse);

        User userUpdate = defaultUser.clone();
        userUpdate.setEmail("test-updatedata@yandex.ru");

        ValidatableResponse createdResponseUpdate = client.updateUserDataWithoutLogin(userUpdate);
        check.checkUpdateDataWithoutLogin(createdResponseUpdate);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        if (userAutToken != null)
            client.deleteUser(StringUtils.substringAfter(userAutToken, " "));
    }
}