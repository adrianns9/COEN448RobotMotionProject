package ca.concordia.coen448;


public class Robot {
    // direction change
    public enum Direction {
        NORTH, EAST, SOUTH, WEST;

        // anti-clockwise turn
        public Direction turnLeft() {
            return switch (this) {
                case NORTH -> WEST;
                case WEST -> SOUTH;
                case SOUTH -> EAST;
                case EAST -> NORTH;
            };
        }

        // clockwise turn
        public Direction turnRight() {
            return switch (this) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
            };
        }
    }

    public enum PenState {
        UP, DOWN
    }

    private int x;
    private int y;
    private Direction direction;
    private PenState penState;
    private int[][] floor;

    // constructor
    public Robot() {
        this(0, 0, Direction.NORTH, PenState.UP);
    }

    public Robot(int x, int y, Direction direction, PenState penState) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.penState = penState;
    }

    //  getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public PenState getPenState() {
        return penState;
    }

    // Command to use
    public void reset() {
        x = 0;
        y = 0;
        direction = Direction.NORTH;
        penState = PenState.UP;
    }

    public void penUp() {
        this.penState = PenState.UP;
    }

    public void penDown() {
        this.penState = PenState.DOWN;
    }

    public void turnLeft() {
        this.direction = direction.turnLeft();
    }

    public void turnRight() {
        this.direction = direction.turnRight();
    }

    public int[] getNextForwardPosition() {
        int nextX = x;
        int nextY = y;

        switch (direction) {
            case NORTH -> nextY += 1;
            case SOUTH -> nextY -= 1;
            case EAST -> nextX += 1;
            case WEST -> nextX -= 1;
        }

        return new int[]{nextX, nextY};
    }


    //increment value
    public void moveForward() {
        switch (direction) {
            case NORTH -> y += 1;
            case SOUTH -> y -= 1;
            case EAST -> x += 1;
            case WEST -> x -= 1;
        }
    }

    @Override
    public String toString() {
        return "Position: " + x + "," + y + " - Pen=" + penState + " - Facing=" + direction;
    }
}




