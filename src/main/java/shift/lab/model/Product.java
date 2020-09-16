package shift.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * Класс Product, описывающий общие характеристики товара в магазине компьютеров
 *
 */

@AllArgsConstructor
@Getter
public abstract class Product {
    /**
     * Уникальный идентификатор товара
     */
    private String id;
    /**
     * Уникальный серийный номер товара
     */
    private String serialNumber;

    /**
     * Производитель товара
     */
    private String manufacturer;

    /**
     * Цена товара
     */
    private Price price;

    /**
     * Количество экземпляров данного товара в наличии
     */
    private Integer amount;
}
