package tetris;

import java.awt.Graphics;
import java.awt.Point;

/**
 * A generic piece (4-polyomino) in the Tetris Game.
 * 
 * The game piece "floats above" the Matrix. The (row, col) coordinates are the
 * location of the middle Mino on the side within the Matrix.
 */
abstract public class Tetrimino implements Cloneable {
    // Whether this piece is frozen or not
    protected boolean ableToMove;

    // The cells that make up this piece
    protected Mino[] cells;

    // The board this piece is on, made up of CELL_COUNT cells
    protected Matrix matrix;

    // Number of cells in one Tetris game piece
    protected static final int CELL_COUNT = 4;

    // The index of the rotational center of the game piece
    protected static final int CENTER = 1;

    // The color of this piece
    private static final ColorScheme COLOR = ColorScheme.BASE_03;

    // Whether this Tetrimino is a ghost piece
    private boolean isGhost = false;

    /**
     * Gets the Matrix that this Tetrimino belongs to.
     *
     * @return a reference to the Matrix
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * Sets the Matrix that this Tetrimino belongs to.
     *
     * @param m the new Matrix
     */
    public void setMatrix(Matrix m) {
        matrix = m;
        for (int i = 0; i < CELL_COUNT; i++) {
            cells[i].setMatrix(m);
        }
    }

    /**
     * Sets the position of this Tetrimino by moving all its cells from their
     * current location to the desired location.
     *
     * This follows the convention that the (row, col) pair specifies the
     * CENTER cell.
     *
     * @param row the new row for the CENTER cell
     * @param col the new column for the CENTER cell
     */
    public void setPosition(int row, int col) {
        int centerRow = cells[CENTER].getRow();
        int centerCol = cells[CENTER].getCol();

        for (int i = 0; i < CELL_COUNT; i++) {
            int dRow = cells[i].getRow() - centerRow;
            int dCol = cells[i].getCol() - centerCol;
            cells[i].setPosition(row + dRow, col + dCol);
        }
    }

    /**
     * Draws the Tetrimino on the given Graphics context.
     * 
     * @param g the Graphics context on which to draw
     */
    public void draw(Graphics g) {
        for (int i = 0; i < CELL_COUNT; i++) {
            cells[i].draw(g, isGhost);
        }
    }

    /**
     * Moves the Tetrimino if possible, otherwise freeze the piece if it cannot
     * move down anymore.
     * 
     * @param direction the Direction to move
     */
    public void move(Direction direction) {
        if (canMove(direction)) {
            for (int i = 0; i < CELL_COUNT; i++)
                cells[i].move(direction);
        }
        // If we couldn't move, see if because we're at the bottom
        else if (direction == Direction.DOWN) {
            ableToMove = false;
        }
    }

    /**
     * Gets all the (row, col) Matrix coordinates occupied by this
     * Tetrimino's cells.
     * 
     * @return an Array of (row, col) Points
     */
    public Point[] getLocations() {
        Point[] points = new Point[CELL_COUNT];
        for (int i = 0; i < CELL_COUNT; i++) {
            points[i] = new Point(cells[i].getRow(), cells[i].getCol());
        }
        return points;
    }

    /**
     * Gets the color of this Tetrimino.
     * @return the color of this Tetrimino
     */
    public ColorScheme getColor() {
        // All cells of this piece have the same color
        return cells[0].getColor();
    }

    /**
     * Checks if this Tetrimino can move one block in the specified direction
     * by querying all of its constituent cells.
     * 
     * @param direction the Direction to check for movement capability
     * @return true if this Tetrimino can move in the given direction
     */
    public boolean canMove(Direction direction) {
        if (!ableToMove) return false;

        // Each square must be able to move in that direction
        boolean answer = true;
        for (int i = 0; i < CELL_COUNT; i++) {
            answer = answer && cells[i].canMove(direction);
        }

        return answer;
    }

    /**
     * Checks whether this Tetrimino can rotate by querying all of its
     * constituent cells.
     *
     * @return true if this Tetrimino can rotate
     */
    public boolean canRotate() {
        if (!ableToMove) return false;

        // Each square must be able to move in that direction
        Point center = new Point(
                cells[CENTER].getCol(),
                cells[CENTER].getRow());

        boolean answer = true;

        // System.out.println("Rotating piece around (" + center + ")");

        for (int i = 0; i < CELL_COUNT; i++) {
            if (i == CENTER) continue;
            // System.out.print("sq[" + i + "]: ");
            answer = answer && cells[i].canRotateAbout(center);
        }

        return answer;
    }

    /**
     * Rotate this Tetrimino in the direction specified by telling all of its
     * constituent cells to rotate about the central cell.
     */
    public void rotate() {
        if (canRotate()) {
            Point center = new Point(
                    cells[CENTER].getCol(),
                    cells[CENTER].getRow());

            for (int i = 0; i < CELL_COUNT; i++) {
                if (i == CENTER) continue;
                cells[i].rotateAbout(center);
            }
        }
    }

    /**
     * Creates a ghost version of this Tetrimino in the same position and
     * orientation as this Tetrimino.
     *
     * @return the ghost Tetrimino
     */
    public Tetrimino makeGhost() {
        try {
            // Clone the current object to create the ghost
            Tetrimino ghost = (Tetrimino) super.clone();
            ghost.isGhost = true;

            // Make new cells at location of current piece's cells
            ghost.cells = new Mino[4];
            for (int i = 0; i < CELL_COUNT; i++) {
                int r = cells[i].getRow();
                int c = cells[i].getCol();
                ghost.cells[i] = new Mino(matrix, r, c, Matrix.EMPTY, true);
            }
            return ghost;
        } catch (Exception e) {
            System.err.println("Failed to create ghost. " + e);
            return null;
        }
    }

    /**
     * Locks down this Tetrimino's cells at their current location in
     * the Matrix.
     */
    public void lockDown() {
        // Tell all Minos to lock down
        for (int i = 0; i < CELL_COUNT; i++) {
            cells[i].lockDown();
        }
    }
}
