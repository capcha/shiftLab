package shift.lab.models;

import lombok.Getter;

/**
 *
 * Класс Laptop, описывающий характеристики Ноутбука
 *
 */
public class Laptop extends Product {

    /**
     * Размер ноутбука
     */
    private final @Getter Double size;

    public Laptop(String id, String serialNumber, String manufacturer, Price price, Integer amount, Double size) {
        super(id, serialNumber, manufacturer, price, amount);
        this.size = size;
    }

    public Double getSize() {
        return size;
    }

}
