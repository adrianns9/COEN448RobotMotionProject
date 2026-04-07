package ca.concordia.coen448;

import ca.concordia.coen448.command.*;

import java.util.Scanner;

public class CommandParser {
    private final Scanner scanner = new Scanner(System.in);
    private final Robot robot;
    private final CommandInvoker invoker;
    private final Floor floor;
    private boolean running = true;

    public CommandParser(Robot controller, Floor floor, CommandInvoker invoker) {
        this.robot = controller;
        this.floor = floor;
        this.invoker = invoker;
    }

    public void run() {
        while (running) {
            System.out.print("> Enter command: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            parse(input);
        }
    }

    private void parse(String input) {
        String[] tokens = input.split("\\s+");
        String cmd = tokens[0].toUpperCase();

        try {
            switch (cmd) {
                case "I" ->  {
                    int size = Integer.parseInt(tokens[1]);
                   
                    if (size > 0) {
                        invoker.execute(new InitCommand(robot, floor, size));
                    } else {
                        System.out.println("Invalid command: Floor size must be greater than zero.");
                    }
                }
                case "M" -> {
                    int steps = Integer.parseInt(tokens[1]);
                   
                    if (steps >= 0) {
                        invoker.execute(new MoveCommand(robot, floor, steps));
                    } else {
                        System.out.println("Invalid command: Move steps cannot be negative.");
                    }
                }

                case "D" -> invoker.execute(
                        new PenDownCommand(robot, floor)
                );

                case "U" -> invoker.execute(
                        new PenUpCommand(robot)
                );

                case "R" -> invoker.execute(
                        new TurnRightCommand(robot)
                );

                case "L" -> invoker.execute(
                        new TurnLeftCommand(robot)
                );

                case "C" -> invoker.execute(
                        new StatusCommand(robot)
                );

                case "P" -> invoker.execute(
                        new PrintFloorCommand(floor)
                );

                case "H" -> invoker.replay();

                case "Q" -> running = false;

                default -> System.out.println("Invalid command.");
            }
        } catch (Exception e) {
            System.out.println("Invalid command syntax.");
        }
    }
}