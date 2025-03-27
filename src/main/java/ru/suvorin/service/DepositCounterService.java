package ru.suvorin.service;

import ru.suvorin.models.DepositDto;
import ru.suvorin.models.CounterResultDto;

public interface DepositCounterService {
    String getServiceDescription();
    CounterResultDto calculateDepositMultiplyTime(DepositDto depositDto);
}
