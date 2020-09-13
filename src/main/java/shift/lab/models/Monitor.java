package shift.lab.models;

import lombok.Getter;

/**
 *
 * Класс Monitor, описывающий характеристики Монитора
 *
 */

public class Monitor extends Product{

    /**
     * Диагональ монитора
     */
    private final @Getter Double diagonal;

    public Monitor(String id, String serialNumber, String manufacturer, Price price, Integer amount, Double diagonal) {
        super(id, serialNumber, manufacturer, price, amount);
        this.diagonal = diagonal;
    }

    public Double getDiagonal() {
        return diagonal;
    }
}
