package ru.suvorin.console;

import picocli.CommandLine;

import java.util.Objects;
import java.util.Scanner;

@CommandLine.Command(
        name = "Main menu",
        description = "Помощник в расчете для вашего вклада")
public class MainMenu implements Runnable {

    private final CommandLine commandLine;

    public MainMenu(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public void run() {
        System.out.println(
                """
                Добро пожаловать в помощника по расчету вашего вклада!
                """);
        commandLine.execute("--help");
        while (true) {
            String line = (new Scanner(System.in)).nextLine();
            if (Objects.equals(line, "--quit")) {
                break;
            } else {
                commandLine.execute(line.split(" "));
            }
        }
    }
}
