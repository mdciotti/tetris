Tetris
======

Final project for COSC 1320.

![Preview 1](https://github.com/mdciotti/tetris/blob/master/preview-01.png)
![Preview 2](https://github.com/mdciotti/tetris/blob/master/preview-02.png)
![Preview 3](https://github.com/mdciotti/tetris/blob/master/preview-03.png)

Game partially conforms to the [guidelines available on the Tetris Wiki](https://tetris.wiki/Tetris_Guideline).

We have changed some nomenclature to match that of the official Tetris guidelines rather than what was provided in the skeleton code.

- `Grid` is now `Playfield`
- `BarShape` is now `IShape`
- `SquareShape` is now `OShape`
- `AbstractPiece` is now `Tetromino`


Building
--------

Builds are handled with [Gradle](http://gradle.org). Run the following command at a terminal:

```
gradle build
```

You can then run the program with the following command:

```
java -cp build/classes/main/ tetris.Tetris
```


Controls
--------

| Key      | Action       |
|----------|--------------|
| Left     | Move left    |
| Right    | Move right   |
| Down     | Soft drop    |
| Up, X    | Rotate right |
| Z        | Rotate left  |
| Space    | Hard drop    |
| Shift, C | Hold         |
| Escape   | Pause game   |
| Q        | Quit game    |


Colors
------

Colors are defined in the ColorScheme enum, which contains color values according to the Base16 color scheme. Additional colors may be found at https://chriskempson.github.io/base16/

Pieces are assigned colors as follows:

| Piece    | ColorScheme Value |
|----------|-------------------|
| `ZShape` | `BASE_08`         |
| `OShape` | `BASE_0A`         |
| `JShape` | `BASE_0D`         |
| `TShape` | `BASE_0E`         |
| `SShape` | `BASE_0B`         |
| `IShape` | `BASE_0C`         |
| `LShape` | `BASE_09`         |


Work Allocation
---------------

Randomization should occur when creating the `nextPiece`.

### Max

- [ ] [GhostPiece](https://tetris.wiki/Ghost_piece)
- [x] Implement rotate algorithm
- [x] AbstractPiece
- [x] TShape
- [x] SShape
- [x] BarShape

### Brandon

- [ ] Piece randomizer
- [ ] NextPiece
- [ ] Score
- [x] ZShape
- [x] SquareShape
- [x] JShape
