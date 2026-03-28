package ca.concordia.coen448;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorConditionCoverageTest {

    private Floor floor;

    @BeforeEach
    void setUp() {
        floor = new Floor();
        floor.initialize(3);
    }

    @Test
    void isWithin_allConditionsTrue_shouldReturnTrue() {
        assertTrue(floor.isWithin(0, 0));
        assertTrue(floor.isWithin(2, 2));
        assertTrue(floor.isWithin(1, 1));
    }

    @Test
    void isWithin_xLessThanZero_shouldReturnFalse() {
        assertFalse(floor.isWithin(-1, 0));
    }

    @Test
    void isWithin_xEqualToSize_shouldReturnFalse() {
        assertFalse(floor.isWithin(3, 0));
    }

    @Test
    void isWithin_yLessThanZero_shouldReturnFalse() {
        assertFalse(floor.isWithin(0, -1));
    }

    @Test
    void isWithin_yEqualToSize_shouldReturnFalse() {
        assertFalse(floor.isWithin(0, 3));
    }

    @Test
    void markPosition_shouldOnlyMarkWhenInsideBounds() {
        floor.markPosition(1, 1);
        floor.markPosition(-1, 1);
        floor.markPosition(1, -1);
        floor.markPosition(3, 1);
        floor.markPosition(1, 3);

        assertEquals(1, floor.getCell(1, 1));
        assertEquals(0, floor.getCell(0, 0));
        assertEquals(0, floor.getCell(2, 2));
    }

    @Test
    void getCell_outOfBounds_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> floor.getCell(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.getCell(0, -1));
        assertThrows(IllegalArgumentException.class, () -> floor.getCell(3, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.getCell(0, 3));
    }
}