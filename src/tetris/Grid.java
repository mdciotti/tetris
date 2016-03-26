package tetris;

import java.awt.Graphics;

/**
 * This is the Tetris board represented by a (HEIGHT - by - WIDTH) matrix of
 * Squares.
 * 
 * The upper left Square is at (0,0). The lower right Square is at (HEIGHT -1,
 * WIDTH -1).
 * 
 * Given a Square at (x,y) the square to the left is at (x-1, y) the square
 * below is at (x, y+1)
 * 
 * Each Square has a color. A white Square is EMPTY; any other color means that
 * spot is occupied (i.e. a piece cannot move over/to an occupied square). A
 * grid will also remove completely full rows.
 */
public class Grid {

    private Square[][] board;

    // Width and Height of Grid in number of squares
    public static final int HEIGHT = 20;

    public static final int WIDTH = 10;

    private static final int BORDER = 10;

    public static final ColorScheme EMPTY = ColorScheme.BASE_00;

    // The left edge of where this grid will be drawn
    public int left = 100;

    // The top edge of where this grid will be drawn
    public int top = 50;

    /**
     * Creates the grid.
     */
    public Grid() {
        board = new Square[HEIGHT][WIDTH];

        // put squares in the board
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board[row][col] = new Square(this, row, col, EMPTY, false);

            }
        }

    }

    /**
     * Returns true if the location (row, col) on the grid is occupied.
     * 
     * @param row the row in the grid
     * @param col the column in the grid
     */
    public boolean isSet(int row, int col) {
        return !board[row][col].getColor().equals(EMPTY);
    }

    /**
     * Changes the color of the Square at the given location.
     * 
     * @param row the row of the Square in the Grid
     * @param col the column of the Square in the Grid
     * @param c the color to set the Square
     * @throws IndexOutOfBoundsException
     *             if row < 0 || row>= WIDTH || col < 0 || col >= HEIGHT
     */
    public void set(int row, int col, ColorScheme c) {
        board[row][col].setColor(c);
    }

    /**
     * Checks for and remove all solid rows of squares.
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

            // A row is filled when col contains WIDTH squares
            if (col == WIDTH) {
                // Remove the row
                removeRow(row);
            }
        } 
    }

    /**
     * Draws the grid on the given Graphics context.
     * 
     * @param g the Graphics context with which to draw
     */
    public void draw(Graphics g) {

        // Draw the background (slightly larger than the grid playing area)
        g.setColor(ColorScheme.BASE_00.color);
        g.fillRect(left - BORDER, top - BORDER,
            WIDTH * Square.WIDTH + 2 * BORDER,
            HEIGHT * Square.HEIGHT + 2 * BORDER);

        // Vertical stripes
        // g.setColor(ColorScheme.BASE_01.color);
        // for (int c = 1; c < WIDTH; c += 2) {
        //     g.fillRect(LEFT + c * Square.WIDTH + 1, TOP,
        //         Square.WIDTH - 2, HEIGHT * Square.HEIGHT);
        // }

        // Draw all the squares in the grid, skipping empty ones
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (!board[r][c].getColor().equals(EMPTY)) {
                    board[r][c].draw(g);
                }
            }
        }
    }
}
