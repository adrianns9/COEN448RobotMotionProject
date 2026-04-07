package ca.concordia.coen448;

public class Main {
    public static void main(String[] args) {
        // Testing Robot State Class
        Robot robot = new Robot();
        Floor floor = new Floor();
        CommandInvoker invoker = new CommandInvoker(robot, floor);
       
		CommandParser parser = new CommandParser(robot, floor, invoker);

		parser.run();
    }
}
