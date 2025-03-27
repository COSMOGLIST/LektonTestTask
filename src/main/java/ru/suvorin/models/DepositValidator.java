package ru.suvorin.models;

import ru.suvorin.exceptions.DepositException;

import java.math.BigDecimal;

public class DepositValidator {
    public void validate(DepositDto depositDto) {
        StringBuilder builder = new StringBuilder();
        if (depositDto.getMoney().compareTo(BigDecimal.valueOf(0)) <= 0) {
            builder.append("Неверно указан стартовый вклад. Он должен быть положительным.\n");
        }
        if (depositDto.getPercent() <= 0) {
            builder.append("Неверно указан процент. Он должен быть положительным.\n");
        }
        if (depositDto.getMultiply() <= 1) {
            builder.append("Неверно указана кратность. Она должна быть больше единицы.\n");
        }
        if (!builder.isEmpty()) {
            throw new DepositException(builder.toString());
        }
    }
}
