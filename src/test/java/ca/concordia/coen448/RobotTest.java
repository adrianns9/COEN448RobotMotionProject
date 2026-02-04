package ca.concordia.coen448;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    @Test
    void reset_shouldSetDefaultState_R1_R9() {
        Robot robot = new Robot();
        robot.penDown();
        robot.turnRight();
        robot.moveForward();

        robot.reset();

        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertEquals(Robot.PenState.UP, robot.getPenState());
        assertTrue(robot.toString().toUpperCase().contains("NORTH")); // quick check
    }

    @Test
    void turningRightFourTimes_shouldReturnToNorth_R4() {
        Robot robot = new Robot();

        robot.turnRight();
        robot.turnRight();
        robot.turnRight();
        robot.turnRight();

        assertTrue(robot.toString().toUpperCase().contains("NORTH"));
    }

    @Test
    void turningLeftFromNorth_shouldFaceWest_R5() {
        Robot robot = new Robot();

        robot.turnLeft();

        assertTrue(robot.toString().toUpperCase().contains("WEST"));
    }

    @Test
    void turningLeft_coversAllDirections_R5() {
        Robot r1 = new Robot(0, 0, Robot.Direction.NORTH, Robot.PenState.UP);
        r1.turnLeft();
        assertEquals(Robot.Direction.WEST, r1.getDirection());

        Robot r2 = new Robot(0, 0, Robot.Direction.WEST, Robot.PenState.UP);
        r2.turnLeft();
        assertEquals(Robot.Direction.SOUTH, r2.getDirection());

        Robot r3 = new Robot(0, 0, Robot.Direction.SOUTH, Robot.PenState.UP);
        r3.turnLeft();
        assertEquals(Robot.Direction.EAST, r3.getDirection());

        Robot r4 = new Robot(0, 0, Robot.Direction.EAST, Robot.PenState.UP);
        r4.turnLeft();
        assertEquals(Robot.Direction.NORTH, r4.getDirection());
    }

    @Test
    void getNextForwardPosition_coversAllDirections_R6() {
        Robot north = new Robot(2, 2, Robot.Direction.NORTH, Robot.PenState.UP);
        assertArrayEquals(new int[] { 2, 3 }, north.getNextForwardPosition());

        Robot south = new Robot(2, 2, Robot.Direction.SOUTH, Robot.PenState.UP);
        assertArrayEquals(new int[] { 2, 1 }, south.getNextForwardPosition());

        Robot east = new Robot(2, 2, Robot.Direction.EAST, Robot.PenState.UP);
        assertArrayEquals(new int[] { 3, 2 }, east.getNextForwardPosition());

        Robot west = new Robot(2, 2, Robot.Direction.WEST, Robot.PenState.UP);
        assertArrayEquals(new int[] { 1, 2 }, west.getNextForwardPosition());
    }

}
