package tetris;

import java.awt.Color;
import java.awt.Graphics;

/**
 * One Square on our Tetris Grid or one square in our Tetris game piece.
 */
public class Square {

    // The environment where this Square is situated
    private Grid grid;

    // The grid location of this Square
    private int row, col;

    // Whether this piece is frozen or not
    private boolean ableToMove;

    // The color of this Square
    private Color color;

    // The possible move directions are defined by the Game class

    // Dimensions of a Square in pixels
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    /**
     * Creates a square.
     * 
     * @param g the Grid for this Square
     * @param row the row of this Square in the Grid
     * @param col the column of this Square in the Grid
     * @param c the Color of this Square
     * @param mobile true if this Square can move
     * 
     * @throws IllegalArgumentException if row and col not within the Grid
     */
    public Square(Grid g, int row, int col, Color c, boolean mobile) {
        if (row < 0 || row > Grid.HEIGHT - 1)
            throw new IllegalArgumentException("Invalid row = " + row);
        if (col < 0 || col > Grid.WIDTH - 1)
            throw new IllegalArgumentException("Invalid column  = " + col);

        // initialize instance variables
        grid = g;
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

        boolean move = true;
        // If the given direction is blocked, the square cannot move
        // Remember to check the edges of the grid
        switch (direction) {
        case DOWN:
            if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
                move = false;
            break;
        case LEFT:
            if (col == 0 || grid.isSet(row, col-1))
                move = false;
            break;
        case RIGHT:
            if (col == grid.WIDTH - 1 || grid.isSet(row, col+1))
                move = false;
            break;
        }
        return move;
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
     * Changes the color of this square.
     * 
     * @param c the new Color
     */
    public void setColor(Color c) {
        color = c;
    }

    /**
     * Gets the color of this square.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Draws this square on the given graphics context.
     * 
     * @param g the AWT Graphics context with which to draw
     */
    public void draw(Graphics g) {

        // Calculate the upper left (x,y) coordinate of this square
        int actualX = Grid.LEFT + col * WIDTH;
        int actualY = Grid.TOP + row * HEIGHT;
        g.setColor(color);
        g.fillRect(actualX, actualY, WIDTH, HEIGHT);

        // Draw a white border (if not empty)
        if (!color.equals(Grid.EMPTY)) {
            g.setColor(Color.WHITE);
            g.drawRect(actualX, actualY, WIDTH - 1, HEIGHT - 1);
        }
    }
}
