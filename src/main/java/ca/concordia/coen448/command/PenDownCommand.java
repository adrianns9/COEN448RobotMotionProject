package ca.concordia.coen448.command;

import ca.concordia.coen448.Floor;
import ca.concordia.coen448.Robot;

public class PenDownCommand implements Command {
    private final Robot robot;
    private final Floor floor;

    public PenDownCommand(Robot robot, Floor floor) {
        this.robot = robot;
        this.floor = floor;
    }

    @Override
    public void execute() {
        robot.penDown();
        floor.markPosition(robot.getX(), robot.getY());
    }
}
