package user;

import base.Base;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Param;
import static io.qameta.allure.model.Parameter.Mode.HIDDEN;

public class UserClient extends Base {
    public static final String API_AUTH = "/login";
    public static final String API_REG = "/register";
    public static final String API_USER = "/user";

    /**
     * Запрос на создание пользователя
     * @param user данные пользователя
     * @return запрос на создание пользователя
     */
    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return
                spec()
                .body(user)
                .when()
                .post(API_REG)
                .then().log().all();
    }

    /**
     * Запрос на обновление данных авторизованного пользователя
     * @param user данные пользователя
     * @param autToken токен авторизации
     * @return запрос на обновление данных
     */
    @Step("Обновление данных авторизованного пользователя")
    public ValidatableResponse updateUserData(@Param(mode=HIDDEN)String autToken, User user) {
        return
                spec()
                .header("Authorization",autToken)
                .body(user)
                .when()
                .patch(API_USER)
                .then().log().all();
    }

    /**
     * Запрос на обновление данных неавторизованного пользователя
     * @param user данные пользователя
     * @return запрос на обновление данных
     */
    @Step("Обновление данных неавторизованного пользователя")
    public ValidatableResponse updateUserDataWithoutLogin(User user) {
        return
                spec()
                .body(user)
                .when()
                .patch(API_USER)
                .then().log().all();
    }

    /**
     * Запрос на авторизацию пользователя
     * @param userCredentials данные пользователя
     * @return запрос на авторизацию пользователя
     */
    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(UserCredentials userCredentials) {
        return
                spec()
                .body(userCredentials)
                .when()
                .post(API_AUTH )
                .then().log().all();
    }

    /**
     * Удаление пользователя
     * @param userAutToken токен авторизации
     */
    @Step("Удаление пользователя")
    public void deleteUser(@Param(mode=HIDDEN)String userAutToken) {
                spec()
                .auth().oauth2(userAutToken)
                .when()
                .delete(API_USER)
                .then().log().all();
    }
}