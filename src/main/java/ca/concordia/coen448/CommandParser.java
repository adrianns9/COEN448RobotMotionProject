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
            String input = scanner.nextLine()
                .trim();

            if (input.isEmpty()) continue;

            parse(input);
        }
    }

    private void parse(String input) {
        String[] tokens = input.split("\\s+");
        String cmd = tokens[0].toUpperCase();

        try {
            switch (cmd) {
                case "I" -> {
                    int size = Integer.parseInt(tokens[1]);
                    invoker.execute(new InitCommand(robot, floor, size));
                }
                case "M" -> {
                    int steps = Integer.parseInt(tokens[1]);
                    invoker.execute(new MoveCommand(robot, floor, steps));
                }
                case "D" -> invoker.execute(new PenDownCommand(robot, floor));

                case "U" -> invoker.execute(new PenUpCommand(robot));

                case "R" -> invoker.execute(new TurnRightCommand(robot));

                case "L" -> invoker.execute(new TurnLeftCommand(robot));

                case "C" -> invoker.execute(new StatusCommand(robot));

                case "P" -> invoker.execute(new PrintFloorCommand(floor));

                case "H" -> invoker.replay();

                case "Q" -> running = false;

                default -> System.out.println("Invalid command.");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Covers invalid integers AND missing arguments
            System.out.println("Invalid command syntax.");
        } catch (IllegalArgumentException e) {
            // From MoveCommand / InitCommand validation
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid command syntax.");
        }
    }
}