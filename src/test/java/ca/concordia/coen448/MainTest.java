package ca.concordia.coen448;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void main_exitsOnQ_R10() {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream("Q\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
        try {
            Main.main(new String[]{});
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }

        // Should at least print the prompt once
        assertTrue(out.toString().contains("Enter command"));
    }
}
