package tetris;

/**
 * A Square-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 squares in the following configuration:
 * 
 * [0][1]
 * [2][3]
 */
public class SquareShape extends AbstractPiece {

    private static final ColorScheme COLOR = ColorScheme.BASE_0F;

    /**
     * Creates a Square-Shape piece. See class description for actual location of
     * parameters r and c.
     *
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the Grid for this game piece
     */
    public SquareShape(int r, int c, Grid g) {
        grid = g;
        squares = new Square[PIECE_COUNT];
        ableToMove = true;

        // Create the squares
        squares[0] = new Square(g, r, c - 1, COLOR, true);
        squares[1] = new Square(g, r, c, COLOR, true);
        squares[2] = new Square(g, r + 1, c - 1, COLOR, true);
        squares[3] = new Square(g, r + 1, c, COLOR, true);
    }
}
