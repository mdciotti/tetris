Tetris
======

Final project for COSC 1320.


Building
--------

Builds are handled with Gradle. Run the following command at a terminal:

```
gradle build
```

You can then run the program with the following command:

```
java -cp build/classes/main/ tetris.Tetris
```

Controls
--------

| Key   | Action                 |
|-------|------------------------|
| Left  | Move piece left        |
| Right | Move piece right       |
| Down  | Move piece down        |
| Up    | Rotate piece clockwise |
| Space | Drop piece             |
| Shift | Hold piece             |
| Q     | Quit game              |

Work Allocation
---------------

- Piece randomizer
- Score

Randomization should occur when creating the `nextPiece`.

### Max

- Implement rotate algorithm
- TShape
- SShape
- BarShape

### Brandon

- AbstractPiece, piece interface
- ZShape
- SquareShape
- JShape
