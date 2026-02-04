package ca.concordia.coen448;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {

    @Test
    void initialize_shouldCreateGridFilledWithZeros_R1() {
        Floor floor = new Floor();
        floor.initialize(5);

        // If you have something like getCell:
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertEquals(0, floor.getCell(x, y));
            }
        }
    }

    @Test
    void markPosition_shouldSetCellToOne_R7() {
        Floor floor = new Floor();
        floor.initialize(3);

        floor.markPosition(1, 2);
        assertEquals(1, floor.getCell(1, 2));

        // marking again stays 1
        floor.markPosition(1, 2);
        assertEquals(1, floor.getCell(1, 2));
    }
}
