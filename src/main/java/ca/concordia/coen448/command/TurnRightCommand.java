package ca.concordia.coen448.command;

import ca.concordia.coen448.Robot;

public class TurnRightCommand implements Command {
    private final Robot robot;

    public TurnRightCommand(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void execute() {
        robot.turnRight();
    }
}
