package ca.concordia.coen448;

import ca.concordia.coen448.command.Command;

import java.util.*;

public class CommandInvoker {
    private final List<Command> history = new ArrayList<>();

    public void execute(Command command) {
        command.execute();
        history.add(command);
    }

    public void replay() {
        for (Command command : history) {
            System.out.printf("-- %s --", command.getClass().getSimpleName());
            command.execute();
        }
    }
}
