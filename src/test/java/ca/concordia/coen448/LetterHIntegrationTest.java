package ca.concordia.coen448;

import ca.concordia.coen448.command.MoveCommand;
import ca.concordia.coen448.command.PenDownCommand;
import ca.concordia.coen448.command.PenUpCommand;
import ca.concordia.coen448.command.PrintFloorCommand;
import ca.concordia.coen448.command.TurnLeftCommand;
import ca.concordia.coen448.command.TurnRightCommand;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LetterHIntegrationTest {

    private static String capturePrintedFloor(Floor floor) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            new PrintFloorCommand(floor).execute();
        } finally {
            System.setOut(old);
        }
        return out.toString();
    }

    @Test
    void drawH_andPrintFloor_blackBoxIntegration() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(10);

        // Left vertical bar: x=0, y=0..5
        new PenDownCommand(robot, floor).execute();
        new MoveCommand(robot, floor, 5).execute();

        // Move to top of right bar without drawing: to (4,5)
        new PenUpCommand(robot).execute();
        new TurnRightCommand(robot).execute();   // EAST
        new MoveCommand(robot, floor, 4).execute();

        // Right vertical bar downward: x=4, y=5..0
        new PenDownCommand(robot, floor).execute();
        new TurnRightCommand(robot).execute();   // SOUTH
        new MoveCommand(robot, floor, 5).execute();

        // Move to middle row without drawing: to (4,3)
        new PenUpCommand(robot).execute();
        new TurnLeftCommand(robot).execute();    // SOUTH -> EAST
        new TurnLeftCommand(robot).execute();    // EAST  -> NORTH
        new MoveCommand(robot, floor, 3).execute();

        // Middle horizontal bar leftward: y=3, x=4..0
        new PenDownCommand(robot, floor).execute();
        new TurnLeftCommand(robot).execute();    // NORTH -> WEST
        new MoveCommand(robot, floor, 4).execute();

        String printed = capturePrintedFloor(floor);

        // Optional: uncomment for demo / screenshot runs
        // System.out.println("\n=== PRINTING H ===");
        // System.out.println(printed);

        // Final robot state
        assertEquals(0, robot.getX());
        assertEquals(3, robot.getY());
        assertEquals(Robot.Direction.WEST, robot.getDirection());

        // Left vertical bar
        for (int y = 0; y <= 5; y++) {
            assertEquals(1, floor.getCell(0, y), "Left bar missing at (0," + y + ")");
        }

        // Right vertical bar
        for (int y = 0; y <= 5; y++) {
            assertEquals(1, floor.getCell(4, y), "Right bar missing at (4," + y + ")");
        }

        // Middle horizontal bar
        for (int x = 0; x <= 4; x++) {
            assertEquals(1, floor.getCell(x, 3), "Middle bar missing at (" + x + ",3)");
        }

        // A few interior cells that should remain blank
        assertEquals(0, floor.getCell(2, 1));
        assertEquals(0, floor.getCell(2, 2));
        assertEquals(0, floor.getCell(2, 4));
        assertEquals(0, floor.getCell(2, 5));

        // Printed output should at least contain visible drawing
        assertTrue(printed.contains("*"), "Printed floor should contain '*' characters");
    }
}