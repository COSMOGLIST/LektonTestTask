package ru.suvorin.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CounterResultDto {
    private int years;
    private int month;
    private double accurateTimeInMonth;
    private BigDecimal moneyAmount;
}
