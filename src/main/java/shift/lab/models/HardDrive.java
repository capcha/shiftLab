package shift.lab.models;

import lombok.Getter;

/**
 *
 * Класс HardDrive, описывающий характеристики товара Жесткий Диск
 *
 */
public class HardDrive extends Product {

    /**
     * Объем жесткого диска
     */
    private final @Getter Double volume;

    public HardDrive(String id, String serialNumber, String manufacturer, Price price, Integer amount, Double volume) {
        super(id, serialNumber, manufacturer, price, amount);
        this.volume = volume;
    }

    public Double getVolume() {
        return volume;
    }

}
