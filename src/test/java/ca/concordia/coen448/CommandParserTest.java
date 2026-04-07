package ca.concordia.coen448;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandParserTest {

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
            CommandInvoker invoker = new CommandInvoker(robot, floor);
            CommandParser parser = new CommandParser(robot, floor, invoker);

            parser.run();
            return out.toString();
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
    }

    @Test
    void parser_acceptsValidCommands_andStopsOnQ_R1_R3_R6_R10() {
        String output = runParserWithInput("I 5\n" + "D\n" + "M 2\n" + "Q\n");

        // not strict matching, just ensure it ran without "Invalid" errors
        assertFalse(output.contains("Invalid command."), "Should not report invalid command for valid inputs");
        assertFalse(output.contains("Invalid command syntax."), "Should not report invalid syntax for valid inputs");
    }

    @Test
    void parser_invalidCommand_printsInvalidCommandBranch() {
        String output = runParserWithInput("X\n" + "Q\n");
        assertTrue(output.contains("Invalid command."), "Expected 'Invalid command.' for unknown command");
    }

    @Test
    void parser_invalidSyntax_printsInvalidSyntaxBranch() {
        // 'I' without number triggers exception -> "Invalid command syntax."
        String output = runParserWithInput("I\n" + "Q\n");
        assertTrue(output.contains("Invalid command syntax."), "Expected 'Invalid command syntax.' for bad input");
    }

    @Test
    void parser_replayCommand_runsReplayBranch_H() {
        String output = runParserWithInput("I 3\n" + "D\n" + "M 1\n" + "H\n" + "Q\n");
        // replay prints headers like "-- InitCommand --"
        assertTrue(output.contains("InitCommand") || output.contains("--"), "Expected replay output to include command name/header");
    }

    @Test
    void parser_ignoresEmptyLines() {
        String output = runParserWithInput("\n" + "\n" + "Q\n");
        // just ensure it terminates and doesn't print invalid errors for blanks
        assertFalse(output.contains("Invalid command."), "Empty lines should be ignored");
        assertFalse(output.contains("Invalid command syntax."), "Empty lines should be ignored");
    }

    @Test
    void parser_covers_U_R_L_C_P_commands() {
        String output = runParserWithInput("I 5\n" + "U\n" + // PenUpCommand
                "R\n" + // TurnRightCommand
                "L\n" + // TurnLeftCommand
                "C\n" + // StatusCommand
                "P\n" + // PrintFloorCommand
                "Q\n");

        // only check it didn't go into error branches
        assertFalse(output.contains("Invalid command syntax."));
    }

    @Test
    void parser_covers_invalidSyntax_forMove_missingSteps() {
        // "M" without number triggers catch -> Invalid command syntax.
        String output = runParserWithInput("M\n" + "Q\n");
        assertTrue(output.contains("Invalid command syntax."));
    }

}
