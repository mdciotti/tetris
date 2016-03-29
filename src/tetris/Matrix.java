package tetris;

import java.awt.*;
import java.io.InputStream;

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

    // The padding around the matrix
    private static final int BORDER = 10;

    // The background color of the matrix
    public static final ColorScheme EMPTY = ColorScheme.BASE_00;

    // The number of rows and columns of Matrix in number of cells
    private int rows, cols;

    // The left edge of where this matrix will be drawn
    private int left = 100;

    // The top edge of where this matrix will be drawn
    private int top = 50;


    /**
     * Creates the matrix.
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new Mino[rows][cols];

        // put cells in the board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = new Mino(this, r, c, EMPTY, false);
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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Point getPosition() {
        return new Point(left, top);
    }

    public void setPosition(int left, int top) {
        this.left = left;
        this.top = top;
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
        for (int col = 0; col < cols; col++) {
            set(r, col, EMPTY);
        }

        // Move the remaining rows down one
        for (int row = r-1; row >= 0; row--) {
            for (int col = 0; col < cols; col++) {
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
     *
     * @return the number of full rows that were removed
     */
    public int checkRows() {
        int col, row;
        int count = 0;

        for (row = 0; row < rows; row++) {
            for (col = 0; col < cols; col++) {
                // Move to next row if this square is empty
                if (!isSet(row, col)) break;
            }

            // A row is filled when col contains WIDTH cells
            if (col == cols) {
                // Remove the row
                removeRow(row);
                count++;
            }
        }
        return count;
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
            cols * Mino.WIDTH + 2 * BORDER,
            rows * Mino.HEIGHT + 2 * BORDER);

        // Vertical stripes
        // g.setColor(ColorScheme.BASE_01.color);
        // for (int c = 1; c < WIDTH; c += 2) {
        //     g.fillRect(LEFT + c * Mino.WIDTH + 1, TOP,
        //         Mino.WIDTH - 2, HEIGHT * Mino.HEIGHT);
        // }

        // Draw all the cells in the matrix, skipping empty ones
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!board[r][c].getColor().equals(EMPTY)) {
                    board[r][c].draw(g, false);
                }
            }
        }
    }
}
