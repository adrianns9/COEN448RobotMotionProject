package ca.concordia.coen448.command;

import ca.concordia.coen448.Floor;
import ca.concordia.coen448.Robot;

public class InitCommand implements Command {
    private final Robot robot;
    private final Floor floor;
    private final int size;

    public InitCommand(Robot robot, Floor floor, int size) {
        this.robot = robot;
        this.floor = floor;
        this.size = size;
    }

    @Override
    public void execute() {
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid command: Floor size must be greater than zero.");
        }

        floor.initialize(size);
        robot.reset();
    }
}
