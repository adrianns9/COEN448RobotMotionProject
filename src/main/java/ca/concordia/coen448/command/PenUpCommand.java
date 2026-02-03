package ca.concordia.coen448.command;

import ca.concordia.coen448.Robot;

public class PenUpCommand implements Command {
    private final Robot robot;

    public PenUpCommand(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void execute() {
        robot.penUp();
    }
}
