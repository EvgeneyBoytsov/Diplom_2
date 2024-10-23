package order;

import io.restassured.response.ValidatableResponse;
import java.util.List;

public class Ingredients {
    private final OrderClient orderClient = new OrderClient();

    /**
     * Добавление ингредиентов в список
     * @param order - список ингредиентов
     */
    public void initIngredients(Order order) {
        ValidatableResponse ingredientsList = orderClient.getAllIngredients();
        List<String> list = ingredientsList.extract().path("data._id");

        List<String> ingredients = order.getIngredients();
        ingredients.add(list.get(1));
        ingredients.add(list.get(7));
    }

    /**
     * Добавление ингредиентов в список с неверным хэшом
     * @param order - список ингредиентов
     */
    public void changeIngredients(Order order) {
        ValidatableResponse response = orderClient.getAllIngredients();
        List<String> list = response.extract().path("data._id");

        List<String> ingredients = order.getIngredients();
        ingredients.add(list.get(1).replaceAll("a", "l"));
        ingredients.add(list.get(4));
    }
}