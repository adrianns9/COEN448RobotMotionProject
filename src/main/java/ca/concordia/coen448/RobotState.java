package ca.concordia.coen448;


public class RobotState {

    //direction change
	public enum Direction {
        NORTH, EAST, SOUTH, WEST;
		//anti-clockwise turn
        public Direction turnLeft() {
            switch (this) {
                case NORTH: return WEST;
                case WEST:  return SOUTH;
                case SOUTH: return EAST;
                case EAST:  return NORTH;
                default:    return null; 
            }
        }
//clockwise turn 
        public Direction turnRight() {
            switch (this) {
                case NORTH: return EAST;
                case EAST:  return SOUTH;
                case SOUTH: return WEST;
                case WEST:  return NORTH;
                default:    return null; 
            }
        }
    }
	
    public enum PenState {
        UP, DOWN
    }

    
    private int x;
    private int y;
    private Direction direction;
    private PenState penState;

   //constructor
    public RobotState() {
        this(0, 0, Direction.NORTH, PenState.UP);
    }

    public RobotState(int x, int y, Direction direction, PenState penState) {
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


    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPenState(PenState penState) {
        this.penState = penState;
    }

    //Command to use
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
    //increment value
    public void moveForward(int steps) {
        if (direction == Direction.NORTH) {
            y += steps;
        } else if (direction == Direction.SOUTH) {
            y -= steps;
        } else if (direction == Direction.EAST) {
            x += steps;
        } else if (direction == Direction.WEST) {
            x -= steps;
        }
    }
    @Override
    public String toString() {
        return "Position:" +
                 x +
                "," + y +
                " - Pen=" + penState +
                " - Facing=" + direction +
                '}';
    }
    public static void main(String[] args)
    {
    	
    }
}




