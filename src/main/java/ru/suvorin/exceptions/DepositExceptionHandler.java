package ru.suvorin.exceptions;

import picocli.CommandLine;

public class DepositExceptionHandler implements CommandLine.IExecutionExceptionHandler {
    @Override
    public int handleExecutionException(Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) {
        commandLine.getErr().println(commandLine.getColorScheme().errorText(e.getMessage()));

        return commandLine.getExitCodeExceptionMapper() != null
                ? commandLine.getExitCodeExceptionMapper().getExitCode(e)
                : commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
