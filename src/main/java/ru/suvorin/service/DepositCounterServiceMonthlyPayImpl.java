package ru.suvorin.service;

import ru.suvorin.models.DepositDto;
import ru.suvorin.models.CounterResultDto;
import ru.suvorin.models.DepositValidator;

import java.math.BigDecimal;

public class DepositCounterServiceMonthlyPayImpl implements DepositCounterService {

    private final DepositValidator validator;

    public DepositCounterServiceMonthlyPayImpl(DepositValidator validator) {
        this.validator = validator;
    }

    @Override
    public String getServiceDescription() {
        return "Считает для вклада с ежемесячными выплатами.";
    }

    @Override
    public CounterResultDto calculateDepositMultiplyTime(DepositDto depositDto) {
        validator.validate(depositDto);
        double monthlyPercent = depositDto.getPercent() / 12 / 100;

        double accurateTime = Math.log(depositDto.getMultiply()) / (Math.log(1 + monthlyPercent));

        int totalMonths = (int) Math.ceil(accurateTime);
        int years = totalMonths / 12;
        int months = totalMonths % 12;
        BigDecimal moneyAmount =
                BigDecimal.valueOf(Math.pow(1 + monthlyPercent, totalMonths))
                        .multiply(depositDto.getMoney());

        return new CounterResultDto(years, months, accurateTime, moneyAmount);
    }
}