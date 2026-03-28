package ca.concordia.coen448;

import ca.concordia.coen448.command.MoveCommand;
import ca.concordia.coen448.command.PenDownCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveCommandDataFlowTest {

    @Test
    void du_positionDefinition_reachesBoundaryUse_whenBlockedImmediately() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(1); // only cell (0,0) exists

        new MoveCommand(robot, floor, 1).execute();

        // position is defined as next forward position (0,1)
        // and used in isWithin(...), causing the break path
        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
    }

    @Test
    void du_positionDefinition_reachesBoundaryUse_whenMoveIsAllowed() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new MoveCommand(robot, floor, 1).execute();

        // position def reaches isWithin(...) and allows move
        assertEquals(0, robot.getX());
        assertEquals(1, robot.getY());
    }

    @Test
    void du_xAndYDefinitions_reachMarkUse_whenPenIsDown() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new PenDownCommand(robot, floor).execute(); // marks (0,0)
        new MoveCommand(robot, floor, 1).execute(); // moves to (0,1)

        // x and y are defined after move and used in markPosition(x, y)
        assertEquals(1, floor.getCell(0, 0));
        assertEquals(1, floor.getCell(0, 1));
    }

    @Test
    void du_xAndYDefinitions_reachMarkUse_acrossMultipleIterations() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new PenDownCommand(robot, floor).execute();
        new MoveCommand(robot, floor, 3).execute();

        // x/y are redefined and used on each iteration
        assertEquals(1, floor.getCell(0, 0));
        assertEquals(1, floor.getCell(0, 1));
        assertEquals(1, floor.getCell(0, 2));
        assertEquals(1, floor.getCell(0, 3));
    }

    @Test
    void du_markUseIsNotReached_whenPenIsUp() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new MoveCommand(robot, floor, 2).execute();

        // move occurs, but x/y markPosition use is skipped because pen is UP
        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());

        for (int x = 0; x < floor.getSize(); x++) {
            for (int y = 0; y < floor.getSize(); y++) {
                assertEquals(0, floor.getCell(x, y));
            }
        }
    }

    @Test
    void du_positionUse_followedByBoundaryStop_afterSomeIterations() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(3);

        new PenDownCommand(robot, floor).execute();
        new MoveCommand(robot, floor, 10).execute();

        // position is redefined each loop iteration;
        // on the final attempted step it reaches isWithin(...) and breaks
        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());

        assertEquals(1, floor.getCell(0, 0));
        assertEquals(1, floor.getCell(0, 1));
        assertEquals(1, floor.getCell(0, 2));
    }
}