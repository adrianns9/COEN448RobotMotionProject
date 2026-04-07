package ca.concordia.coen448;

import ca.concordia.coen448.command.Command;

import java.util.*;

public class CommandInvoker {
    private final List<Command> history = new ArrayList<>();
    private final Robot robot;
    private final Floor floor;

    // Modified Constructor to accept Robot and Floor
    public CommandInvoker(Robot robot, Floor floor) {
        this.robot = robot;
        this.floor = floor;
    }
    public void execute(Command command) {
        command.execute();
        history.add(command);
    }

    
    public void replay() {
        // Defect 3 Fix: Reset the environment to its initial state before replaying
        robot.reset();
        if (floor.getSize() > 0) {
            // Calling initialize overrides the grid array with zeros, clearing the floor
            floor.initialize(floor.getSize()); 
        }

        for (Command command : history) {
            System.out.printf("-- %s --\n", command.getClass().getSimpleName());
            command.execute();
        }
    }
    
}
