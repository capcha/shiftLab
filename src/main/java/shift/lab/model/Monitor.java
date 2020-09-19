package shift.lab.model;

import lombok.Getter;

/**
 *
 * Класс Monitor, описывающий характеристики Монитора
 *
 */

@Getter
public class Monitor extends Product {
    /**
     * Диагональ монитора
     */
    private Double diagonal;

    public Monitor(String id, String serialNumber, String manufacturer, Price price, Integer amount, Double diagonal) {
        super(id, serialNumber, manufacturer, price, amount);
        this.diagonal = diagonal;
    }
}
