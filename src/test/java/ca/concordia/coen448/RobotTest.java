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
}
