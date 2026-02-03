package ca.concordia.coen448.command;

import ca.concordia.coen448.Floor;
import ca.concordia.coen448.Robot;

public class MoveCommand implements Command {
    private final Robot robot;
    private final Floor floor;
    private final int steps;

    public MoveCommand(Robot robot, Floor floor, int steps) {
        this.robot = robot;
        this.floor = floor;
        this.steps = steps;
    }

    @Override
    public void execute() {
        // Move the robot one step at a time
        for (int i = 0; i < steps; i++) {
            // First, check if next position forward is outside of floor
            int[] position = robot.getNextForwardPosition();
            if (!floor.isWithin(position[0], position[1])) {
                break;
            }

            // Move the robot
            robot.moveForward();

            // Then, if pen is down, mark the current position on the floor
            if (robot.getPenState() == Robot.PenState.DOWN) {
                int x = robot.getX();
                int y = robot.getY();
                floor.markPosition(x, y);
            }
        }

        System.out.printf("Moved %d steps to (%d, %d)%n", steps, robot.getX(), robot.getY());
    }
}