package tetris;

import java.awt.Graphics;
import java.awt.Point;

/**
 * One Mino on our Tetris Matrix or one square in our Tetris game piece.
 */
public class Mino {

    // The environment where this Mino is situated
    private Matrix matrix;

    // The matrix location of this Mino
    private int row, col;

    // Whether this piece is frozen or not
    private boolean ableToMove;

    // The color of this Mino
    private ColorScheme color;

    // The possible move directions are defined by the Game class

    // Dimensions of a Mino in pixels
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    /**
     * Creates a square.
     * 
     * @param m the Matrix for this Mino
     * @param row the row of this Mino in the Matrix
     * @param col the column of this Mino in the Matrix
     * @param c the Color of this Mino
     * @param mobile true if this Mino can move
     */
    public Mino(Matrix m, int row, int col, ColorScheme c, boolean mobile) {
        // initialize instance variables
        matrix = m;
        this.row = row;
        this.col = col;
        color = c;
        ableToMove = mobile;
    }

    /**
     * Gets the Row for this Mino.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column for this Mino.
     *
     * @return the column
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the position of this Mino.
     *
     * @param row the new row
     * @param col the new column
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the Matrix that this Mino resides in.
     *
     * @return the matrix
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * Sets the Matrix that this Mino resides in.
     *
     * @param m the new matrix
     */
    public void setMatrix(Matrix m) {
        matrix = m;
    }

    /**
     * Check whether this Mino can move one spot in a particular direction.
     * 
     * @param direction the Direction to test for possible move
     * @return true if the Mino can move
     */
    public boolean canMove(Direction direction) {
        if (!ableToMove)
            return false;

        int r = row;
        int c = col;

        // If the given direction is blocked, the square cannot move
        // Remember to check the edges of the matrix
        switch (direction) {
            case DOWN: r = row + 1; break;
            case LEFT: c = col - 1; break;
            case RIGHT: c = col + 1; break;
        }

        return canMoveTo(r, c);
    }

    /**
     * Moves this square in the given direction if possible.
     * 
     * The square will not move if the direction is blocked, or if the square is
     * unable to move.
     * 
     * If it attempts to move DOWN and it can't, the square is frozen and cannot
     * move anymore.
     * 
     * @param direction the Direction to move
     */
    public void move(Direction direction) {
        if (canMove(direction)) {
            switch (direction) {
                case DOWN: row++; break;
                case LEFT: col--; break;
                case RIGHT: col++; break;
            }
        }
    }

    /**
     * Checks if this Mino can be moved to an arbitrary location on its matrix.
     *
     * @param r the row in the matrix to check
     * @param c the column in the matrix to check
     * @return true if it can be moved to that location
     */
    private boolean canMoveTo(int r, int c) {
        if ((0 <= r && r < matrix.getRows()) && (0 <= c && c < matrix.getCols())) {
            return !matrix.isSet(r, c);
        } else {
            return false;
        }
    }

    /**
     * Gives the direction to move next when in the process of rotating a piece.
     * The center here is defined as the central cell in a Tetrimino, and
     * therefore all cells rotate about that central cell as if it were the
     * center of the diagram below. Works in a clockwise fashion.
     *
     * > > > > > > V 
     * ^ > > > > V V 
     * ^ ^ > > V V V 
     * ^ ^ ^   V V V 
     * ^ ^ ^ < < V V 
     * ^ ^ < < < < V 
     * ^ < < < < < < 
     * 
     * @param  rx the x radius of the current location
     * @param  ry the y radius of the current location
     * @return    the direction the square should move
     */
    private Direction getDir(int rx, int ry) {
        Direction dir = Direction.NONE;
        if (rx == 0 && ry == 0) dir = Direction.NONE;
        else if (rx >= ry && ry < -rx) dir = Direction.RIGHT;
        else if (ry > -rx && rx <= ry) dir = Direction.LEFT;
        else if (ry > rx && rx <= -ry) dir = Direction.UP;
        else if (rx >= -ry && ry < rx) dir = Direction.DOWN;
        return dir;
    }

    /**
     * Check whether this Mino can rotate about the given center. This algorithm
     * assumes that each Mino in a Tetrimino must move in a rectilinear manner
     * and pass through all intermediate cells to the destination. As long as
     * nothing obstructs the Mino's path, the rotation is valid.
     * 
     * @param center the square about which to test for possible rotation
     * @return true if the rotation is possible
     */
    public boolean canRotateAbout(Point center) {
        if (!ableToMove)
            return false;

        boolean move = true;
        
        // This square's coordinates relative to the center of rotation
        int rx = col - center.x;
        int ry = row - center.y;

        // Calculate which "ring" of rotation this square is in
        int ring = Math.max(Math.abs(rx), Math.abs(ry));

        // Calculate the number of steps for a quarter rotation
        int quarterRotationSteps = 2 * ring;

        // System.out.print("ring " + ring + ", " + quarterRotationSteps + " steps: ");

        // Continue moving the square in the rotation direction unless a
        // collision is detected or the destination is reached
        for (int i = 0; i < quarterRotationSteps; i++) {

            // System.out.print("(" + rx + ", " + ry + ") -> ");

            // Step in direction
            switch (getDir(rx, ry)) {
                case NONE: break;
                case LEFT: rx--; break;
                case RIGHT: rx++; break;
                case DOWN: ry++; break;
                case UP: ry--; break;
            }

            // Check for 'collision'
            if (!canMoveTo(center.y + ry, center.x + rx)) {
                move = false;
                break;
            }
        }

        // System.out.println("(" + rx + ", " + ry + ") " + move);

        return move;
    }

    /**
     * Rotates this square, whether or not it is possible.
     * 
     * @param center the location to rotate around
     */
    public void rotateAbout(Point center) {
        int rx = col - center.x;
        int ry = row - center.y;
        col = center.x - ry;
        row = center.y + rx;
    }

    /**
     * Locks down this Mino at its current location in the Matrix.
     */
    public void lockDown() {
        matrix.set(getRow(), getCol(), color);
    }

    /**
     * Changes the color of this Mino.
     * 
     * @param c the new Color
     */
    public void setColor(ColorScheme c) {
        color = c;
    }

    /**
     * Gets the ColorScheme value of this Mino.
     *
     * @return the color
     */
    public ColorScheme getColor() {
        return color;
    }

    /**
     * Draws this square on the given graphics context.
     * 
     * @param g the AWT Graphics context with which to draw
     */
    public void draw(Graphics g, boolean isGhost) {

        // Calculate the upper left (x,y) coordinate of this square
        Point topleft = matrix.getPosition();
        int actualX = topleft.x + col * WIDTH;
        int actualY = topleft.y + row * HEIGHT;
        if (isGhost) {
            g.setColor(ColorScheme.BASE_02.color);
            g.fillRect(actualX + 1, actualY + 1, WIDTH - 2, HEIGHT - 2);
            g.setColor(ColorScheme.BASE_00.color);
            g.fillRect(actualX + 3, actualY + 3, WIDTH - 6, HEIGHT - 6);
        } else {
            g.setColor(color.color);
            g.fillRect(actualX + 1, actualY + 1, WIDTH - 2, HEIGHT - 2);
        }
    }
}
