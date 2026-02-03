package ca.concordia.coen448.command;

import ca.concordia.coen448.Floor;

public class PrintFloorCommand implements Command {
    private final Floor floor;

    public PrintFloorCommand(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void execute() {
        floor.printFloor();
    }
}
