package tetris;

/**
 * An S-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 cells in the following configuration:
 * 
 *    [1][0]
 * [3][2]
 */
public class SShape extends Tetrimino {

    private static final ColorScheme COLOR = ColorScheme.BASE_0B;

    /**
     * Creates an S-Shape piece. See class description for actual location of
     * parameters r and c.
     * 
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the Matrix for this game piece
     */
    public SShape(int r, int c, Matrix g) {
        matrix = g;
        cells = new Mino[CELL_COUNT];
        ableToMove = true;

        // Create the cells
        cells[0] = new Mino(g, r, c + 1, COLOR, true);
        cells[1] = new Mino(g, r, c, COLOR, true);
        cells[2] = new Mino(g, r + 1, c, COLOR, true);
        cells[3] = new Mino(g, r + 1, c - 1, COLOR, true);
    }
}
