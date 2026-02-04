# COEN448 Robot Motion Project - Task 1

This project implements a robot motion simulation on a grid-based floor for COEN448 Winter 2026. The robot can move around the floor, draw paths when the pen is down, and respond to various commands entered by the user.

## Description

The system simulates a robot that navigates an n x n grid floor. The robot starts at position (0,0), facing north, with the pen up. Users can issue commands to control the robot's movement, pen state, and display the floor status.

## Features

- Initialize an n x n floor grid
- Robot movement in four directions: North, South, East, West
- Pen control (up/down) for drawing paths
- Boundary checking to prevent moving outside the floor
- Command history replay
- Real-time status display
- Floor visualization with marked paths

## Requirements (Task 1)

**R1** — Initialize system: When user enters `I n`, system creates an n x n floor of zeros and resets robot to (0,0), pen UP, facing NORTH.

**R2** — Pen up: `U` sets pen to UP (movement does not draw).

**R3** — Pen down: `D` sets pen to DOWN (drawing enabled).

**R4** — Turn right: `R` rotates robot 90° clockwise.

**R5** — Turn left: `L` rotates robot 90° counterclockwise.

**R6** — Move forward: `M s` moves forward s spaces (step-by-step).

**R7** — Draw while moving: If pen is DOWN, each visited cell becomes 1 (stays 1 even if traced again).

**R8** — Print floor: `P` prints floor: `*` for 1, blank for 0 (and indices if implemented).

**R9** — Print status: `C` prints current position, pen state, and facing direction.

**R10** — Quit: `Q` stops the program.

**R11** — Replay history: `H` replays all commands since start.

**R12** — Boundaries: Robot must not move outside the n x n floor (ignore remaining steps once blocked).

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

### Building the Project

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd COEN448RobotMotionProject
   ```

2. Build the project using Maven:
   ```bash
   mvn clean compile
   ```

### Running the Application

Run the main class:
```bash
mvn exec:java -Dexec.mainClass="ca.concordia.coen448.Main"
```

Or compile and run manually:
```bash
mvn clean compile
java -cp target/classes ca.concordia.coen448.Main
```

## Usage

The program accepts commands via standard input. Here are the available commands:

- `I n` - Initialize floor of size n x n
- `U` - Pen up
- `D` - Pen down
- `R` - Turn right (clockwise)
- `L` - Turn left (counterclockwise)
- `M s` - Move forward s steps
- `P` - Print floor
- `C` - Print current status
- `H` - Replay command history
- `Q` - Quit

### Example Session

```
I 5
D
M 3
R
M 2
P
C
Q
```

This initializes a 5x5 floor, puts the pen down, moves 3 steps north (drawing a line), turns right (to east), moves 2 steps east, prints the floor, shows status, and quits.

## Testing

The project includes comprehensive unit tests and integration tests.

### Running Tests

```bash
mvn test
```

### Test Coverage

Test coverage reports are generated using JaCoCo:

```bash
mvn clean test jacoco:report
```

View the coverage report at `target/site/jacoco/index.html`

## Project Structure

```
src/
├── main/java/ca/concordia/coen448/
│   ├── Main.java                 # Entry point
│   ├── Robot.java                # Robot model
│   ├── Floor.java                # Floor grid model
│   ├── CommandInvoker.java       # Command execution
│   ├── CommandParser.java        # Command parsing
│   └── command/                  # Command classes
│       ├── Command.java
│       ├── InitCommand.java
│       ├── MoveCommand.java
│       ├── PenDownCommand.java
│       ├── PenUpCommand.java
│       ├── PrintFloorCommand.java
│       ├── StatusCommand.java
│       ├── TurnLeftCommand.java
│       └── TurnRightCommand.java
└── test/java/ca/concordia/coen448/
    ├── CommandIntegrationTest.java
    ├── CommandInvokerTest.java
    ├── CommandParserTest.java
    ├── FloorTest.java
    ├── MainTest.java
    └── RobotTest.java
```

## Authors

- Céline Ziadé - 40251642
- Adrian Nico Salvado - XXXXXXXX
- 

## License

This project is for educational purposes as part of COEN448 coursework.