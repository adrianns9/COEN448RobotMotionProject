package ca.concordia.coen448;

import ca.concordia.coen448.command.Command;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class CommandInvokerTest {

    @Test
    void execute_addsToHistory_andReplayReExecutes() {
        CommandInvoker invoker = new CommandInvoker();
        AtomicInteger counter = new AtomicInteger(0);

        Command cmd = counter::incrementAndGet;

        invoker.execute(cmd);
        assertEquals(1, counter.get());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            invoker.replay();
        } finally {
            System.setOut(old);
        }

        // replay should execute again
        assertEquals(2, counter.get());

        // also covers the printing inside replay()
        assertTrue(out.toString().contains("--"), "Expected replay output header");
    }
}
