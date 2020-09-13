package shift.lab.models;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Класс Price, описывающий характеристики цены
 */

public class Price {

    /**
     * Количество валюты (цена)
     */
    private final @Getter BigDecimal value;

    /**
     * Валюта
     */
    private final @Getter String currency;

    public Price(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

}
