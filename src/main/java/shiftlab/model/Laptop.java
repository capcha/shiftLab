package shiftlab.model;

import lombok.Getter;

/**
 *
 * Класс Laptop, описывающий характеристики Ноутбука
 *
 */

@Getter
public class Laptop extends Product {
    /**
     * Размер ноутбука
     */
    private Double size;

    public Laptop(String id, String serialNumber, String manufacturer, Price price, Integer amount, Double size) {
        super(id, serialNumber, manufacturer, price, amount);
        this.size = size;
    }
}
