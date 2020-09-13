package shift.lab.models;


/**
 *
 * Класс DesktopComputer, описывающий характеристики товара Настольный Компьютер
 *
 */

public class DesktopComputer extends Product {

    /**
     * Форм фактор настольного компьютера (стоит ли использовать enum?)
     */
    private final FormFactor formFactor;

    public DesktopComputer(String id, String serialNumber, String manufacturer, Price price, Integer amount, FormFactor formFactor) {
        super(id, serialNumber, manufacturer, price, amount);
        this.formFactor = formFactor;
    }

    public FormFactor getFormFactor() {
        return formFactor;
    }

}
