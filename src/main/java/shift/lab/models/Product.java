package shift.lab.models;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * Класс Product, описывающий общие характеристики товара в магазине компьютеров
 *
 */

public abstract class Product {

    /**
     * Уникальный идентификатор товара
     */
    private final @Getter @Setter String id;
    /**
     * Уникальный серийный номер товара
     */
    private final @Getter @Setter String serialNumber;

    /**
     * Производитель товара
     */
    private final @Getter @Setter String manufacturer;

    /**
     * Цена товара
     */
    private final @Getter @Setter Price price;

    /**
     * Количество экземпляров данного товара в наличии
     */
    private final @Getter @Setter Integer amount;

    public Product(String id, String serialNumber, String manufacturer, Price price, Integer amount) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.price = price;
        this.amount = amount;
    }



}
