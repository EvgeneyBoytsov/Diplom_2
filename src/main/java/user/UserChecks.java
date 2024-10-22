package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.Map;
import java.util.Set;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class UserChecks {

    /**
     * Проверка создания пользователя
     * @param createdResponse - тело ответа на запрос создание пользователя
     * @return - токен авторизации
     */
    @Step("Проверка создания пользователя")
    public String checkCreatedUser(ValidatableResponse createdResponse) {
        var body = createdResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа", Set.of("success", "user", "accessToken", "refreshToken"), body.keySet());
        assertEquals("Поле success false",true, body.get("success"));
        assertNotEquals("Поле user пустое",null, body.get("user"));
        assertNotEquals("Поле refreshToken пустое",null, body.get("refreshToken"));

        Map<String, String> user = (Map<String, String>) body.get("user");
        assertEquals("Неверное тело ответа",Set.of("email", "name"), user.keySet());
        assertNotEquals("Поле email пустое",null,user.get("email"));
        assertNotEquals("Поле name пустое",null,user.get("name"));

        return (String) body.get("accessToken");
    }

    /**
     * Проверка создание дублированного пользователя
     * @param createdResponses тело ответа на запрос создания дубликата пользователя
     */
    @Step("Проверка создания дубликата пользователя")
    public void checkRequestWithDuplicateUserData(ValidatableResponse createdResponses) {
        var body = createdResponses
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа",Set.of("success", "message"), body.keySet());
        assertEquals("Не верный ответ в поле message","User already exists", body.get("message"));
        assertEquals("Поле success true",false, body.get("success"));
    }

    /**
     * Проверка создания пользователя без передачи обязательных параметров
     * @param createdResponse тело ответа на создание пользователя без передачи обязательных параметров
     */
    @Step("Проверка создания пользователя без передачи обязательных параметров")
    public void checkRequestWithoutUserData(ValidatableResponse createdResponse) {
        var body = createdResponse
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа",Set.of("success", "message"), body.keySet());
        assertEquals("Поле success true",false, body.get("success"));
        assertEquals("Не верный ответ в поле message","Email, password and name are required fields", body.get("message"));
    }

    /**
     * Проверка авторизации пользователя
     * @param loginResponse тело ответа на запрос авторизации пользователя
     * @return токен авторизации
     */
    @Step("Проверка авторизации пользователя")
    public String checkLogInUser(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа",Set.of("success", "accessToken", "refreshToken", "user"), body.keySet());
        assertEquals("Поле success false",true, body.get("success"));
        assertNotEquals("Поле refreshToken пустое",null, body.get("refreshToken"));
        assertNotEquals("Поле user пустое",null, body.get("user"));

        Map<String, String> user = (Map<String, String>) body.get("user");
        assertEquals("Неверное тело ответа",Set.of("email", "name"), user.keySet());
        assertNotEquals("Поле email пустое",null,user.get("email"));
        assertNotEquals("Поле name пустое",null,user.get("name"));

        return (String) body.get("accessToken");
    }

    /**
     * Проверка авторизации пользователя с неверными данными пользователя
     * @param loginResponsesWrongData тело ответа на запрос авторизации с неверными данными
     */
    @Step("Проверка авторизации с неверными данными пользователя")
    public void checkBadRequestLoginUserData(ValidatableResponse loginResponsesWrongData) {
        var body = loginResponsesWrongData
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа",Set.of("success", "message"), body.keySet());
        assertEquals("Поле success true",false, body.get("success"));
        assertEquals("Не верный ответ в поле message","email or password are incorrect", body.get("message"));
    }

    /**
     * Проверка обновления данных пользователя
     * @param createdResponseUpdate тело ответа на запрос обновления данных пользователя
     */
    @Step("Проверка обновления данных пользователя")
    public void checkUpdateUserData(ValidatableResponse createdResponseUpdate) {
        var body = createdResponseUpdate
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа",Set.of("success", "user"), body.keySet());
        assertEquals("Поле success false",true, body.get("success"));
        assertNotEquals("Поле user пустое",null, body.get("user"));

        Map<String, String> user = (Map<String, String>) body.get("user");
        assertEquals("Неверное тело ответа",Set.of("email", "name"), user.keySet());
        assertNotEquals("Поле email пустое",null,user.get("email"));
        assertNotEquals("Поле name пустое",null,user.get("name"));
    }

    /**
     * Проверка обновления данных пользователя неавторизованного пользователя
     * @param createdResponseUpdate тело ответа на запрос обновления данных неавторизованного пользователя
     */
    @Step("Проверка обновления данных пользователя без авторизации")
    public void checkUpdateDataWithoutLogin(ValidatableResponse createdResponseUpdate) {
        var body = createdResponseUpdate
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное тело ответа", Set.of("success", "message"), body.keySet());
        assertEquals("Поле success true",false, body.get("success"));
        assertEquals("Не верный ответ в поле message","You should be authorised", body.get("message"));
    }
}