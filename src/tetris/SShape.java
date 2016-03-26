package tetris;

/**
 * An S-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 cells in the following configuration:
 * 
 *    [1][0]
 * [3][2]
 */
public class SShape extends Tetromino {

    private static final ColorScheme COLOR = ColorScheme.BASE_0B;

    /**
     * Creates an S-Shape piece. See class description for actual location of
     * parameters r and c.
     * 
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the PlayField for this game piece
     */
    public SShape(int r, int c, PlayField g) {
        playField = g;
        cells = new Square[CELL_COUNT];
        ableToMove = true;

        // Create the cells
        cells[0] = new Square(g, r, c + 1, COLOR, true);
        cells[1] = new Square(g, r, c, COLOR, true);
        cells[2] = new Square(g, r + 1, c, COLOR, true);
        cells[3] = new Square(g, r + 1, c - 1, COLOR, true);
    }
}
