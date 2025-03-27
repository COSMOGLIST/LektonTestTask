package ru.suvorin;

import picocli.CommandLine;
import ru.suvorin.console.DepositController;
import ru.suvorin.console.MainMenu;
import ru.suvorin.exceptions.DepositExceptionHandler;
import ru.suvorin.models.DepositValidator;
import ru.suvorin.service.DepositCounterService;
import ru.suvorin.service.DepositCounterServiceMonthlyPayImpl;
import ru.suvorin.service.DepositCounterServiceYearlyPayImpl;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        var services = new ArrayList<DepositCounterService>();
        DepositValidator validator = new DepositValidator();
        services.add(new DepositCounterServiceMonthlyPayImpl(validator));
        services.add(new DepositCounterServiceYearlyPayImpl(validator));
        CommandLine commandLine = new CommandLine(new DepositController(services));
        commandLine.setExecutionExceptionHandler(new DepositExceptionHandler());
        MainMenu mainMenu = new MainMenu(commandLine);
        mainMenu.run();
    }
}