Tetris
======

Final project for COSC 1320.

![Preview 1](https://github.com/mdciotti/tetris/blob/master/preview-01.png)
![Preview 2](https://github.com/mdciotti/tetris/blob/master/preview-02.png)
![Preview 3](https://github.com/mdciotti/tetris/blob/master/preview-03.png)


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


Colors
------

Colors are defined in the ColorScheme enum, which contains color values according to the Base16 color scheme. Additional colors may be found at https://chriskempson.github.io/base16/

Pieces are assigned colors as follows:

| Piece       | ColorScheme Value |
|-------------|-------------------|
| ZShape      | BASE_08           |
| SquareShape | BASE_0F           |
| JShape      | BASE_0D           |
| TShape      | BASE_0A           |
| SShape      | BASE_0B           |
| BarShape    | BASE_0C           |
| LShape      | BASE_0E           |


Work Allocation
---------------

Randomization should occur when creating the `nextPiece`.

### Max

- Implement rotate algorithm
- Piece randomizer
- AbstractPiece
- TShape
- SShape
- BarShape

### Brandon

- NextPiece
- Score
- ZShape
- SquareShape
- JShape
