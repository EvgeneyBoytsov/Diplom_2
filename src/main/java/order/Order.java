package order;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private List<String> ingredients;
    private String _id;
    private String status;
    private String number;
    private String createdAt;
    private String updatedAt;

    /**
     * создание списка ингредиентов
     */
    public Order() {
        ingredients = new ArrayList<>();
    }
}