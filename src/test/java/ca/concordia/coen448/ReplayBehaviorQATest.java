package ca.concordia.coen448;

import ca.concordia.coen448.command.MoveCommand;
import ca.concordia.coen448.command.PenDownCommand;
import ca.concordia.coen448.command.TurnRightCommand;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReplayBehaviorQATest {

    @Test
    void replay_shouldPrintStoredCommandNames() {
        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(6);
        CommandInvoker invoker = new CommandInvoker();

        invoker.execute(new PenDownCommand(robot, floor));
        invoker.execute(new MoveCommand(robot, floor, 1));
        invoker.execute(new TurnRightCommand(robot));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            invoker.replay();
        } finally {
            System.setOut(old);
        }

        String printed = out.toString();
        assertTrue(printed.contains("PenDownCommand"));
        assertTrue(printed.contains("MoveCommand"));
        assertTrue(printed.contains("TurnRightCommand"));
    }

    @Test
    void replay_shouldRestartFromInitialState_beforeReplaying_specBasedQA() {
        // Defect-revealing QA test:
        // If replay truly replays "since start", one reasonable expectation is that it
        // reproduces the same final state as the original execution, rather than continuing
        // from the current state.

        Robot robot = new Robot();
        Floor floor = new Floor();
        floor.initialize(6);
        CommandInvoker invoker = new CommandInvoker();

        invoker.execute(new PenDownCommand(robot, floor));
        invoker.execute(new MoveCommand(robot, floor, 2));   // (0,2), facing NORTH
        invoker.execute(new TurnRightCommand(robot));        // EAST
        invoker.execute(new MoveCommand(robot, floor, 1));   // (1,2), facing EAST

        // Original final state
        assertEquals(1, robot.getX());
        assertEquals(2, robot.getY());
        assertEquals(Robot.Direction.EAST, robot.getDirection());

        invoker.replay();

        // Spec-oriented expectation:
        // replay should end in the same state as the original trace from the program start.
        assertEquals(1, robot.getX(), "Replay should reproduce the original final X");
        assertEquals(2, robot.getY(), "Replay should reproduce the original final Y");
        assertEquals(Robot.Direction.EAST, robot.getDirection(), "Replay should reproduce the original final direction");
    }
}