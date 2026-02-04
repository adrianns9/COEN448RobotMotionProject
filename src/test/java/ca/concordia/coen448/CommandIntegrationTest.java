package ca.concordia.coen448;

import ca.concordia.coen448.command.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CommandIntegrationTest {

    @Test
    void penDown_shouldMarkStartingCell_R3_R7() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(10);

        new PenDownCommand(robot, floor).execute();

        assertEquals(Robot.PenState.DOWN, robot.getPenState());
        assertEquals(1, floor.getCell(robot.getX(), robot.getY()));
    }

    @Test
    void moveWithPenDown_shouldMarkFullPath_R6_R7() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(10);

        new PenDownCommand(robot, floor).execute(); // marks start
        new MoveCommand(robot, floor, 4).execute(); // move north 4 (based on your demo)

        // path should be marked from (0,0) to (0,4)
        for (int y = 0; y <= 4; y++) {
            assertEquals(1, floor.getCell(0, y));
        }
    }

    @Test
    void moveWithPenUp_shouldNotMarkAnyCell_R2_R6() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(10);

        new MoveCommand(robot, floor, 3).execute();

        // since pen UP by default, even the destination should still be 0
        assertEquals(0, floor.getCell(robot.getX(), robot.getY()));
    }

    @Test
    void turnRight_thenMove_shouldChangeX_R4_R6() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(10);

        new TurnRightCommand(robot).execute(); // now facing east
        new MoveCommand(robot, floor, 3).execute();

        // In your demo: after turning right and moving 3, you ended at (3,4) from (0,4)
        // So "east" increases X.
        assertEquals(3, robot.getX());
    }

    @Test
    void move_shouldStopAtBoundary_R12() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(3);

        new MoveCommand(robot, floor, 999).execute();

        assertTrue(robot.getX() >= 0 && robot.getX() < 3);
        assertTrue(robot.getY() >= 0 && robot.getY() < 3);
    }

    @Test
    void initCommand_shouldResetRobot_andClearFloor_R1() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(5);

        new PenDownCommand(robot, floor).execute();
        new MoveCommand(robot, floor, 2).execute();
        assertEquals(1, floor.getCell(0, 0)); // sanity

        new InitCommand(robot, floor, 5).execute();

        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertEquals(Robot.PenState.UP, robot.getPenState());
        assertTrue(robot.toString().toUpperCase().contains("NORTH"));

        // floor cleared
        for (int x = 0; x < floor.getSize(); x++) {
            for (int y = 0; y < floor.getSize(); y++) {
                assertEquals(0, floor.getCell(x, y));
            }
        }
    }

    @Test
    void penUpCommand_shouldSetPenToUp() {
        Robot robot = new Robot();
        robot.penDown();
        assertEquals(Robot.PenState.DOWN, robot.getPenState());

        new PenUpCommand(robot).execute();

        assertEquals(Robot.PenState.UP, robot.getPenState());
    }

    @Test
    void turnLeftCommand_shouldChangeDirection() {
        Robot robot = new Robot();
        Robot.Direction before = robot.getDirection();

        new TurnLeftCommand(robot).execute();

        Robot.Direction after = robot.getDirection();
        assertNotEquals(before, after);
    }

    @Test
    void statusCommand_shouldPrintRobotState() {
        Robot robot = new Robot();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            new StatusCommand(robot).execute();
        } finally {
            System.setOut(old);
        }

        String printed = out.toString().trim();
        assertFalse(printed.isEmpty(), "StatusCommand should print something");
        // Optional: check it contains key words depending on your toString()
        assertTrue(printed.toUpperCase().contains("POSITION") || printed.contains("0,0"));
    }

    @Test
    void printFloorCommand_shouldPrintFloorOutput() {
        Floor floor = new Floor();
        floor.initialize(3);
        floor.markPosition(0, 0);
        floor.markPosition(2, 2);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            new PrintFloorCommand(floor).execute();
        } finally {
            System.setOut(old);
        }

        String printed = out.toString();
        assertTrue(printed.contains("*"), "PrintFloorCommand should print at least one '*'");
    }
}
