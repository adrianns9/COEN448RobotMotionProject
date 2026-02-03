package ca.concordia.coen448;

public class Main {
    public static void main(String[] args) {
        // Testing Robot State Class
        Robot robot = new Robot();

    	robot.penDown();
    	robot.moveForward(5);
    	robot.turnRight();
    	robot.moveForward(3);

    	System.out.println(robot);
    }
}
