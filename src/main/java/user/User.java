package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@AllArgsConstructor
@Data
public class User implements Cloneable {
    private  String email;
    private  String password;
    private  String name;

    /**
     * Создание пользователя с рандомными данными
     * @return нового пользователя
     */
    public static User randomCreateUser() {
        String email = RandomStringUtils.randomAlphanumeric(5,10) + "-data@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(5,10);
        String name = RandomStringUtils.randomAlphanumeric(5,10);

        return new User(email,password,name);
    }

    /**
     * Создание клона пользователя
     * @return нового клона пользователя
     */
    public User clone() {
        User clone = null;
        try {
            clone = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.email = email;
        clone.password = password;
        clone.name = name;
        return clone;
    }
}