package ru.suvorin.console;

import picocli.CommandLine;
import ru.suvorin.exceptions.DepositException;
import ru.suvorin.models.CounterResultDto;
import ru.suvorin.models.DepositDto;
import ru.suvorin.service.DepositCounterService;

import java.math.BigDecimal;
import java.util.List;

@CommandLine.Command(
        name = "Deposit controller",
        description = "Собирает информацию о вашем вкладе.")
public class DepositController implements Runnable {

    private final List<DepositCounterService> depositCounterServices;

    public DepositController(List<DepositCounterService> depositCounterServices) {
        this.depositCounterServices = depositCounterServices;
    }

    @CommandLine.Command(name = "--payouts", description = "Доступные варианты выплат.")
    void getPossibleServices() {
        System.out.println("Доступные варианты получения выплат:");
        for (int i = 0; i < depositCounterServices.size(); i++) {
            System.out.println(i + ". " + depositCounterServices.get(i).getServiceDescription());
        }
    }

    @CommandLine.Command(name = "--help", description = "Помощь с пользованием.")
    void help() {
        System.out.println(
                """
                Список команд:
                --help - вывести список команд.
                --quit - завершить работу помощника.
                --payouts - список доступных вариантов выплат.
                --count - рассчитать нужный вклад, опции:
                    -p={номер доступного варианта выплат}
                    -m={Стартовый вклад, руб}
                    -perc={Процент вклада, %%}
                    -mult={кратность увеличения вклада}
                    Пример:
                    --count -p=1 -m=1000 -perc=10 -mult=2
                """);
    }

    @CommandLine.Command(name = "--count", description = "Начать расчет для вашего вклада.")
    void startDepositCounter(
            @CommandLine.Option(names = "-p", required = true, description = "Номер варианта выплат") int serviceNumber,
            @CommandLine.Option(names = "-m", required = true, description = "Стартовый вклад, руб") BigDecimal moneyAmount,
            @CommandLine.Option(names = "-mult", required = true, description = "Кратность увеличения вклада") double multiply,
            @CommandLine.Option(names = "-perc", required = true, description = "Процент вклада, %%") double percent
    ) {
        if (validate(serviceNumber)) {
            DepositDto depositDto = new DepositDto(moneyAmount, percent, multiply);
            CounterResultDto result = depositCounterServices.get(serviceNumber).calculateDepositMultiplyTime(depositDto);
            writeResult(result);
        }
    }
    @Override
    public void run() {}

    private boolean validate(int serviceNumber) throws RuntimeException {
        if (serviceNumber >= 0 && serviceNumber < depositCounterServices.size()) {
            return true;
        } else {
            throw new DepositException("Вы выбрали не существующий номер доступного варианта выплат.");
        }
    }

    private void writeResult(CounterResultDto result) {
        String message = "Ваша сумма увеличится согласно кратности за " +
                String.format("%.2f", result.getAccurateTimeInMonth()) +
                " месяцев.\n" +
                "Это произойдет при выплате, когда ваша сумма достигнет " +
                String.format("%.2f", result.getMoneyAmount()) +
                " рублей.\n" +
                "Это произойдет через " +
                "\nКоличество лет: " +
                result.getYears() +
                ".\nКоличество месяцев: " +
                result.getMonth() +
                '.';
        System.out.println(message);
    }
}
