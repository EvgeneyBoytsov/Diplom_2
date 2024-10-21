package order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Класс для десерилизации ответа получения списков заказов
 */
@Data
@AllArgsConstructor
public class OrderResponse {

    private boolean success;
    private String total;
    private String totalToday;
    private List<Order> orders;

}