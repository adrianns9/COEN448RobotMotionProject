package ca.concordia.coen448;

import ca.concordia.coen448.command.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class QAErrorTest {

    private Robot robot;
    private Floor floor;
    private CommandInvoker invoker;
    
    // Used to capture System.out and simulate System.in
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        robot = new Robot();
        floor = new Floor();
        invoker = new CommandInvoker(robot,floor);
       
        
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Defect 1 Regression Test
     * QA Test: parser_initZero_shouldReject_invalidFloorSize_specBasedQA
     */
    @Test
    public void parser_initZero_shouldReject_invalidFloorSize_specBasedQA() {
        // Arrange: Simulate user typing "I 0" then "Q" to quit
        String simulatedInput = "I 0\nQ\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        CommandParser parser = new CommandParser(robot, floor, invoker);

        // Act
        parser.run();

        // Assert: The floor size should remain 0 (uninitialized) because I 0 was rejected
        assertEquals(0, floor.getSize(), "Floor size 0 should be rejected according to the specification.");
        assertTrue(outContent.toString().contains("must be greater than zero"), 
                "Expected error message indicating size must be > 0.");
    }

    /**
     * Defect 2 Regression Test
     * QA Test: parser_moveNegative_shouldReject_invalidStepCount_specBasedQA
     */
    @Test
    public void parser_moveNegative_shouldReject_invalidStepCount_specBasedQA() {
        // Arrange: Simulate user typing "I 5", then "M -1", then "Q" to quit
        String simulatedInput = "I 5\nM -1\nQ\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        CommandParser parser = new CommandParser(robot, floor, invoker);

        // Act
        parser.run();

        // Assert: Robot's Y coordinate should still be 0 because the move was rejected
        assertEquals(0, robot.getY(), "Negative move steps should be rejected according to the specification.");
        assertTrue(outContent.toString().contains("cannot be negative"), 
                "Expected error message indicating steps cannot be negative.");
    }

    /**
     * Defect 3 Regression Test
     * QA Test: replay_shouldRestartFromInitialState_beforeReplaying_specBasedQA
     */
    @Test
    public void replay_shouldRestartFromInitialState_beforeReplaying_specBasedQA() {
        // Arrange: Initialize a 5x5 floor programmatically
    	new InitCommand(robot, floor, 5).execute();
        
        // Execute the exact sequence from the QA report: 
        // Pen down, Move 2, Turn Right, Move 1
        invoker.execute(new PenDownCommand(robot, floor));
        invoker.execute(new MoveCommand(robot, floor, 2));
        invoker.execute(new TurnRightCommand(robot));
        invoker.execute(new MoveCommand(robot, floor, 1));
        
        // Verify current state before replay
        assertEquals(1, robot.getX(), "Initial X should be 1");
        assertEquals(2, robot.getY(), "Initial Y should be 2");

        // Act: Trigger the replay
        invoker.replay();

        // Assert: The final state after replay should be exactly the same as the original execution.
        // If the code is broken (like it was for QA), X will end up being 3 instead of 1.
        assertEquals(1, robot.getX(), "Replay should reproduce the original final X => expected: <1>");
        assertEquals(2, robot.getY(), "Replay should reproduce the original final Y => expected: <2>");
        assertEquals(Robot.Direction.EAST, robot.getDirection(), "Robot should face EAST");
    }
}