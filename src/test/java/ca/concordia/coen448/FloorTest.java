package ca.concordia.coen448;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void getCell_outOfBounds_shouldThrow_R12() {
        Floor floor = new Floor();
        floor.initialize(3);

        assertThrows(IllegalArgumentException.class, () -> floor.getCell(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.getCell(0, -1));
        assertThrows(IllegalArgumentException.class, () -> floor.getCell(3, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.getCell(0, 3));
    }

    @Test
    void markPosition_outOfBounds_shouldNotChangeGrid_R12() {
        Floor floor = new Floor();
        floor.initialize(3);

        // mark inside once
        floor.markPosition(1, 1);
        assertEquals(1, floor.getCell(1, 1));

        // try marking outside (should be ignored)
        floor.markPosition(-1, 0);
        floor.markPosition(3, 0);
        floor.markPosition(0, -1);
        floor.markPosition(0, 3);

        // still only the original cell should be marked
        assertEquals(1, floor.getCell(1, 1));
    }

}
