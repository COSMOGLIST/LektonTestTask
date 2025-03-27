package ru.suvorin.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DepositDto {
    private BigDecimal money;
    private double percent;
    private double multiply;
}
