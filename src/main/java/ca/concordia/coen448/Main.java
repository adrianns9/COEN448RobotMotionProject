package ca.concordia.coen448;

public class Main {
    public static void main(String[] args) {
        // Testing Robot State Class
        Robot robot = new Robot();
		CommandInvoker invoker = new CommandInvoker();
        Floor floor = new Floor();
		CommandParser parser = new CommandParser(robot, floor, invoker);

		parser.run();
    }
}
