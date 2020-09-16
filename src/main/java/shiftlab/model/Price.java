package shiftlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Класс Price, описывающий характеристики цены
 */

@AllArgsConstructor
@Getter
public class Price {
    /**
     * Количество валюты (цена)
     */
    private BigDecimal value;

    /**
     * Валюта
     */
    private String currency;
}
