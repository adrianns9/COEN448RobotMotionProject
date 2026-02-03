package ca.concordia.coen448.command;

import ca.concordia.coen448.Robot;

public class StatusCommand implements Command {
    private final Robot robot;
    public StatusCommand(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void execute() {
        System.out.println(robot.toString());
    }
}
