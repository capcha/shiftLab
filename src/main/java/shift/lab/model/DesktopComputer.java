package shift.lab.model;

import lombok.Getter;

/**
 *
 * Класс DesktopComputer, описывающий характеристики товара Настольный Компьютер
 *
 */

@Getter
public class DesktopComputer extends Product {
    /**
     * Форм фактор настольного компьютера
     */
    private FormFactor formFactor;

    public DesktopComputer(String id, String serialNumber, String manufacturer, Price price, Integer amount, FormFactor formFactor) {
        super(id, serialNumber, manufacturer, price, amount);
        this.formFactor = formFactor;
    }
}
