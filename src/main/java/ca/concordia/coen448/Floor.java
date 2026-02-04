package ca.concordia.coen448;

public class Floor {
    private int[][] grid;
    private int size;

    public Floor() {
    }

    public boolean isWithin(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public void initialize(int size) {
        this.size = size;
        grid = new int[size][size];
    }

    public void markPosition(int x, int y) {
        if (isWithin(x, y)) {
            grid[x][y] = 1;
        }
    }

    public void printFloor() {
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                boolean isMarked = grid[j][i] == 1;
                System.out.print(isMarked ? "* " : "  ");
            }
            System.out.println();
        }
    }

    public int getCell(int x, int y) {
        if (!isWithin(x, y)) throw new IllegalArgumentException("Out of bounds");
        return grid[x][y];
    }

    public int getSize() {
        return size;
    }

}