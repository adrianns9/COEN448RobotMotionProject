package ca.concordia.coen448;

import ca.concordia.coen448.command.MoveCommand;
import ca.concordia.coen448.command.PenDownCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveCommandWhiteBoxTest {

    @Test
    void move_zeroSteps_shouldNotEnterLoop() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new MoveCommand(robot, floor, 0).execute();

        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertEquals(0, floor.getCell(0, 0)); // pen is UP by default
    }

    @Test
    void move_withPenUp_shouldMoveWithoutMarkingPath() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new MoveCommand(robot, floor, 2).execute();

        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());

        // No cells should be marked because pen is UP
        for (int x = 0; x < floor.getSize(); x++) {
            for (int y = 0; y < floor.getSize(); y++) {
                assertEquals(0, floor.getCell(x, y));
            }
        }
    }

    @Test
    void move_withPenDown_shouldMarkEveryVisitedCell() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new PenDownCommand(robot, floor).execute(); // marks (0,0)
        new MoveCommand(robot, floor, 3).execute(); // move north to (0,3)

        assertEquals(0, robot.getX());
        assertEquals(3, robot.getY());

        // path from (0,0) to (0,3) should be marked
        for (int y = 0; y <= 3; y++) {
            assertEquals(1, floor.getCell(0, y));
        }
    }

    @Test
    void move_shouldStopAtBoundary_whenNextPositionIsOutsideFloor() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(3);

        new MoveCommand(robot, floor, 10).execute();

        // Start at (0,0), face NORTH, max reachable inside 3x3 is (0,2)
        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());
    }

    @Test
    void move_withPenDown_andBoundary_shouldOnlyMarkReachableCells() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(3);

        new PenDownCommand(robot, floor).execute(); // mark start
        new MoveCommand(robot, floor, 10).execute(); // should stop at boundary

        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());

        // only reachable cells inside the boundary should be marked
        assertEquals(1, floor.getCell(0, 0));
        assertEquals(1, floor.getCell(0, 1));
        assertEquals(1, floor.getCell(0, 2));

        // verify another cell was not accidentally marked
        assertEquals(0, floor.getCell(1, 1));
    }

    @Test
    void move_afterTurnRight_shouldUpdateXInsteadOfY() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        robot.turnRight(); // now facing EAST
        new MoveCommand(robot, floor, 2).execute();

        assertEquals(2, robot.getX());
        assertEquals(0, robot.getY());
    }
}