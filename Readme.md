Tetris
======

Final project for COSC 1320.

![Preview 1](https://github.com/mdciotti/tetris/blob/master/preview-01.png)
![Preview 2](https://github.com/mdciotti/tetris/blob/master/preview-02.png)
![Preview 3](https://github.com/mdciotti/tetris/blob/master/preview-03.png)

![Demonstration](https://github.com/mdciotti/tetris/blob/master/preview.gif)

Game partially conforms to the [guidelines available on the Tetris Wiki](https://tetris.wiki/Tetris_Guideline). Many other elements are inspired from [TetrisFriends](http://www.tetrisfriends.com/help/tips_appendix.php).

We have changed some nomenclature to match that of the official Tetris guidelines rather than what was provided in the skeleton code.

- `Grid` is now `Matrix`
- `BarShape` is now `IShape`
- `SquareShape` is now `OShape`
- `AbstractPiece` is now `Tetrimino`
- `Square` is now `Mino`


Definitions
-----------

- **Mino**: a single square-shaped building block of a shape called a Tetrimino. Four Minos arranged into any of their various connected patterns is known as a Tetrimino.
- **Tetrimino**: a geometric shape formed by four Minos connected along their sides. A total of 7 possible Tetriminos can be made using four Mino, each represented by a single unique color.
- **Level**: the indication of progress, determines the speed at which pieces fall. Starts at 1, increases by 1 every time the goal is reached.
- **Goal**: the number of lines left that need to be cleared to advance to the next level. Starts at 5, and counts down with line clears. Increases by 5 every time the goal is reached.
- **Matrix**: the rectangular arrangement of cells that create the active game area, usually 10 columns wide by 20 rows high. Tetriminos fall from the top-middle just above the top of the matrix (off-screen) to the bottom.
- **Hold**: this action removes the Tetrimino in play and places into the Hold Queue. If there was a Tetrimino in the Hold Queue already, it will start to fall from the top of the Matrix, beginning from its originating position and orientation. *You must Lock Down the swapped out Tetrimino before you can perform a Hold again.*
- **Hold queue**: the storage place where players can hold any falling Tetrimino for use later. When called for, the held Tetrimino swaps places with the currently falling Tetrimino, and begins falling from the top of the Matrix.
- **Next queue**: shows the player what the next Tetrimino will be. The next Tetrimino will begin to fall once the previous Tetrimino locks down.
- **Line clear**: the result of when a horizontal row is completely filled with blocks and removed from the Matrix. All pieces above the Line Clear shift down to fill the space.
- **Lock down**: the condition after landing when a Tetrimino can no longer move. (There is a brief pause period once a Tetrimino lands in the Matrix where it can still be moved.)
- **Soft drop**: an action that causes the Tetrimino in play to drop at an accelerated rate from its current location.
- **Hard drop**: an action that causes the Tetrimino in play to drop straight down instantly from its current location and Lock Down on the first surface it lands on. Once performed, the player cannot further manipulate this Tetrimino.
- **Single**: when one line is cleared at once.
- **Double**: when two lines are cleared at once.
- **Triple**: when three lines are cleared at once.
- **Tetris**: the result of using a vertical I-Tetrimino to clear four lines at the same time---the maximum possible---for a large scoring bonus.
- **Back-to-back bonus**: when one or more lines are cleared immediately after the last piece cleared one or more lines.
- **Combo**: a chain of one or more Line Clears performed consecutively. if a Tetrimino locks down without clearing a line during a Combo, the Combo chain is broken. Longer Combo chains will yield more points resulting in a higher score.
- **Block out**: the Game Over condition that occurs when part of a newly generated Tetrimino cannot fall due to an existing block in the Matrix.
- **Lock out**: the Game Over condition that occurs when a Tetrimino Locks Down completely above the top of the Matrix.


Scoring
-------

**Back-to-back** is a bonus, awarded on top of any other action award.

| Action       | Awarded points     |
|--------------|--------------------|
| Single       | 100 * level        |
| Double       | 300 * level        |
| Triple       | 500 * level        |
| Tetris       | 800 * level        |
| Back-to-back | 0.5 * action total |
| Soft drop    | n*                 |
| Hard drop    | 2 * m**            |

*n: the number of lines you allow the Tetrimino to soft drop.
**m: the total distance of the hard drop.

| Action       | Awarded line clears     |
|--------------|-------------------------|
| Single       | 1                       |
| Double       | 3                       |
| Triple       | 5                       |
| Tetris       | 8                       |
| Back to back | 0.5 * total line clears |


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

| Tetrimino | ColorScheme Value |
|---------- |-------------------|
| `ZShape`  | `BASE_08`         |
| `OShape`  | `BASE_0A`         |
| `JShape`  | `BASE_0D`         |
| `TShape`  | `BASE_0E`         |
| `SShape`  | `BASE_0B`         |
| `IShape`  | `BASE_0C`         |
| `LShape`  | `BASE_09`         |


Sounds
------

Sound effects are needed for the following events:

- Piece move (tap.wav)
- Line clear (pop.wav)
- Hold (scuff.wav)
- Can't hold (dink.wav)
- Drop (thump.wav)
- Level up (ding-ding.wav)


Work Allocation
---------------

### Max

- [x] Save scores to disk
- [x] Top score list
- [x] Last-second rotations
- [x] Pause menu
- [x] Game menu
- [x] Display hold, next piece
- [x] Hold
- [x] [GhostPiece](https://tetris.wiki/Ghost_piece)
- [x] Implement rotate algorithm
- [x] AbstractPiece
- [x] TShape
- [x] SShape
- [x] BarShape

### Brandon

- [ ] Music
- [x] Display level, goal, score, top score
- [ ] Piece randomizer (grab bag)
- [x] Score
- [x] NextPiece
- [x] ZShape
- [x] SquareShape
- [x] JShape
