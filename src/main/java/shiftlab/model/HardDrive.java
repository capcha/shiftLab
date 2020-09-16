package shiftlab.model;

import lombok.Getter;

/**
 *
 * Класс HardDrive, описывающий характеристики товара Жесткий Диск
 *
 */

@Getter
public class HardDrive extends Product {
    /**
     * Объем жесткого диска
     */
    private Double volume;

    public HardDrive(String id, String serialNumber, String manufacturer, Price price, Integer amount, Double volume) {
        super(id, serialNumber, manufacturer, price, amount);
        this.volume = volume;
    }
}
