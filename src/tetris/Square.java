package tetris;

import java.awt.Graphics;
import java.awt.Point;

/**
 * One Square on our Tetris PlayField or one square in our Tetris game piece.
 */
public class Square {

    // The environment where this Square is situated
    private PlayField playField;

    // The playField location of this Square
    private int row, col;

    // Whether this piece is frozen or not
    private boolean ableToMove;

    // The color of this Square
    private ColorScheme color;

    // The possible move directions are defined by the Game class

    // Dimensions of a Square in pixels
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    /**
     * Creates a square.
     * 
     * @param g the PlayField for this Square
     * @param row the row of this Square in the PlayField
     * @param col the column of this Square in the PlayField
     * @param c the Color of this Square
     * @param mobile true if this Square can move
     * 
     * @throws IllegalArgumentException if row and col not within the PlayField
     */
    public Square(PlayField g, int row, int col, ColorScheme c, boolean mobile) {
        if (row < 0 || row > PlayField.HEIGHT - 1)
            throw new IllegalArgumentException("Invalid row = " + row);
        if (col < 0 || col > PlayField.WIDTH - 1)
            throw new IllegalArgumentException("Invalid column  = " + col);

        // initialize instance variables
        playField = g;
        this.row = row;
        this.col = col;
        color = c;
        ableToMove = mobile;
    }

    /**
     * Returns the row for this Square.
     */
    public int getRow() {
        return row;
    }
    /**
     * Returns the column for this Square.
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns true if this Square can move one spot in direction d.
     * 
     * @param direction the Direction to test for possible move
     */
    public boolean canMove(Direction direction) {
        if (!ableToMove)
            return false;

        int r = row;
        int c = col;

        // If the given direction is blocked, the square cannot move
        // Remember to check the edges of the playField
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

    private boolean canMoveTo(int r, int c) {
        if ((0 <= r && r < playField.HEIGHT) && (0 <= c && c < playField.WIDTH)) {
            return !playField.isSet(r, c);
        } else {
            return false;
        }
    }

    /**
     * Gives the direction to move next when in the process of rotating a piece.
     *
     * Works in a clockwise fashion.
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
     * Returns true if this Square can rotate about the given center.
     * 
     * @param center the square about which to test for possible rotation
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
     * Rotates this square if possible.
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
     * Changes the color of this square.
     * 
     * @param c the new Color
     */
    public void setColor(ColorScheme c) {
        color = c;
    }

    /**
     * Gets the color of this square.
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
        int actualX = playField.left + col * WIDTH;
        int actualY = playField.top + row * HEIGHT;
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
