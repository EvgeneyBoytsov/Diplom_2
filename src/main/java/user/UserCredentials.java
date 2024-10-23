package user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserCredentials implements Cloneable {
    private String email;
    private String password;

    /**
     * Получение данных пользователя для авторизации
     * @param user данные пользователя
     * @return данные пользователя для авторизации
     */
    public static UserCredentials fromUserData(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

    /**
     * Клонирование пользователя
     * @return клон пользователя
     */
    public UserCredentials clone() {
        UserCredentials cloneLogin = null;
        try {
            cloneLogin = (UserCredentials) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        cloneLogin.email = email;
        cloneLogin.password = password;
        return cloneLogin;
    }
}