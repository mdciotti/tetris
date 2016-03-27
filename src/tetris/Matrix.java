package tetris;

import java.awt.Graphics;

/**
 * This is the Tetris board represented by a (HEIGHT - by - WIDTH) matrix of
 * Squares.
 * 
 * The upper left Mino is at (0,0). The lower right Mino is at (HEIGHT -1,
 * WIDTH -1).
 * 
 * Given a Mino at (x,y) the square to the left is at (x-1, y) the square
 * below is at (x, y+1)
 * 
 * Each Mino has a color. A white Mino is EMPTY; any other color means that
 * spot is occupied (i.e. a piece cannot move over/to an occupied square). A
 * matrix will also remove completely full rows.
 */
public class Matrix {

    private Mino[][] board;

    // Width and Height of Matrix in number of cells
    public static final int HEIGHT = 20;

    public static final int WIDTH = 10;

    private static final int BORDER = 10;

    public static final ColorScheme EMPTY = ColorScheme.BASE_00;

    // The left edge of where this matrix will be drawn
    public int left = 100;

    // The top edge of where this matrix will be drawn
    public int top = 50;

    /**
     * Creates the matrix.
     */
    public Matrix() {
        board = new Mino[HEIGHT][WIDTH];

        // put cells in the board
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board[row][col] = new Mino(this, row, col, EMPTY, false);

            }
        }

    }

    /**
     * Returns true if the location (row, col) on the matrix is occupied.
     * 
     * @param row the row in the matrix
     * @param col the column in the matrix
     */
    public boolean isSet(int row, int col) {
        return !board[row][col].getColor().equals(EMPTY);
    }

    /**
     * Changes the color of the Mino at the given location.
     * 
     * @param row the row of the Mino in the Matrix
     * @param col the column of the Mino in the Matrix
     * @param c the color to set the Mino
     * @throws IndexOutOfBoundsException
     *             if row < 0 || row>= WIDTH || col < 0 || col >= HEIGHT
     */
    public void set(int row, int col, ColorScheme c) {
        board[row][col].setColor(c);
    }

    /**
     * Checks for and remove all solid rows of cells.
     * 
     * If a solid row is found and removed, all rows above it are moved down and
     * the top row set to empty.
     * 
     * @param r the row to erase
     */
    private void removeRow(int r) {
        // Change color of that row to empty
        for (int col = 0; col < WIDTH; col++) {
            set(r, col, EMPTY);
        }

        // Move the remaining rows down one
        for (int row = r-1; row >= 0; row--) {
            for (int col = 0; col < WIDTH; col++) {
                if (isSet(row, col)) {
                    ColorScheme c = board[row][col].getColor();
                    board[row][col].setColor(EMPTY);
                    board[row+1][col].setColor(c);  
                }
            }
        }
    }


    /**
     * Check all rows for a completed (filled) row.
     */
    public void checkRows() {
        int col, row;

        for (row = 0; row < HEIGHT; row++) {
            for (col = 0; col < WIDTH; col++) {
                // Move to next row if this square is empty
                if (!isSet(row, col)) break;
            }

            // A row is filled when col contains WIDTH cells
            if (col == WIDTH) {
                // Remove the row
                removeRow(row);
            }
        } 
    }

    /**
     * Draws the matrix on the given Graphics context.
     * 
     * @param g the Graphics context with which to draw
     */
    public void draw(Graphics g) {

        // Draw the background (slightly larger than the matrix playing area)
        g.setColor(ColorScheme.BASE_00.color);
        g.fillRect(left - BORDER, top - BORDER,
            WIDTH * Mino.WIDTH + 2 * BORDER,
            HEIGHT * Mino.HEIGHT + 2 * BORDER);

        // Vertical stripes
        // g.setColor(ColorScheme.BASE_01.color);
        // for (int c = 1; c < WIDTH; c += 2) {
        //     g.fillRect(LEFT + c * Mino.WIDTH + 1, TOP,
        //         Mino.WIDTH - 2, HEIGHT * Mino.HEIGHT);
        // }

        // Draw all the cells in the matrix, skipping empty ones
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (!board[r][c].getColor().equals(EMPTY)) {
                    board[r][c].draw(g, false);
                }
            }
        }
    }
}
