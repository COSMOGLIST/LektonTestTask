package ru.suvorin.service;

import ru.suvorin.models.DepositDto;
import ru.suvorin.models.CounterResultDto;
import ru.suvorin.models.DepositValidator;

import java.math.BigDecimal;

public class DepositCounterServiceYearlyPayImpl implements DepositCounterService {

    private final DepositValidator validator;

    public DepositCounterServiceYearlyPayImpl(DepositValidator validator) {
        this.validator = validator;
    }
    @Override
    public String getServiceDescription() {
        return "Считает для вклада с годовыми выплатами.";
    }

    @Override
    public CounterResultDto calculateDepositMultiplyTime(DepositDto depositDto) {
        validator.validate(depositDto);
        double accurateTime = Math.log(depositDto.getMultiply()) / (Math.log(1 + depositDto.getPercent() / 100));

        int totalYears = (int) Math.ceil(accurateTime);
        BigDecimal moneyAmount =
                BigDecimal.valueOf(Math.pow(1 + depositDto.getPercent() / 100, totalYears))
                        .multiply(depositDto.getMoney());

        return new CounterResultDto(totalYears, 0, accurateTime * 12, moneyAmount);
    }
}
