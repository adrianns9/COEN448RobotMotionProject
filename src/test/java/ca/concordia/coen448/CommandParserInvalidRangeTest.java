package ca.concordia.coen448;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandParserInvalidRangeTest {

    private static String runParserWithInput(String input) {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
        try {
            Robot robot = new Robot();
            Floor floor = new Floor();
            CommandInvoker invoker = new CommandInvoker();
            CommandParser parser = new CommandParser(robot, floor, invoker);

            parser.run();
            return out.toString();
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
    }

    @Test
    void parser_initNegative_shouldReject_invalidFloorSize() {
        String output = runParserWithInput("I -1\nQ\n");
        assertTrue(
                output.contains("Invalid command syntax.") || output.contains("Invalid command."),
                "Negative floor size should be rejected"
        );
    }

    @Test
    void parser_initZero_shouldReject_invalidFloorSize_specBasedQA() {
        // Defect-revealing QA test:
        // Spec says I n requires n > 0, so I 0 should be rejected.
        String output = runParserWithInput("I 0\nQ\n");
        assertTrue(
                output.contains("Invalid command syntax.") || output.contains("Invalid command."),
                "Floor size 0 should be rejected according to the specification"
        );
    }

    @Test
    void parser_moveNegative_shouldReject_invalidStepCount_specBasedQA() {
        // Defect-revealing QA test:
        // Spec says M s requires s to be non-negative, so M -1 should be rejected.
        String output = runParserWithInput("I 5\nM -1\nQ\n");
        assertTrue(
                output.contains("Invalid command syntax.") || output.contains("Invalid command."),
                "Negative move steps should be rejected according to the specification"
        );
    }
}