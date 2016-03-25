package tetris;

import java.awt.Graphics;
import java.awt.Point;

/**
 * A generic piece in the Tetris Game.
 * 
 * The game piece "floats above" the Grid. The (row, col) coordinates are the
 * location of the middle Square on the side within the Grid.
 */
abstract public class AbstractPiece {
    // Whether this piece is frozen or not
    protected boolean ableToMove;

    // The squares that make up this piece
    protected Square[] squares;

    // The board this piece is on, made up of PIECE_COUNT squares
    protected Grid grid;

    // Number of squares in one Tetris game piece
    protected static final int PIECE_COUNT = 4;

    // The index of the rotational center of the game piece
    protected static final int CENTER = 1;

    // The color of this piece
    private static final ColorScheme COLOR = ColorScheme.BASE_03;

    /**
     * Draws the piece on the given Graphics context.
     * 
     * @param g the Graphics context on which to draw
     */
    public void draw(Graphics g) {
        for (int i = 0; i < PIECE_COUNT; i++) {
            squares[i].draw(g);
        }
    }

    /**
     * Moves the piece if possible, otherwise freeze the piece if it cannot move
     * down anymore.
     * 
     * @param direction the Direction to move
     */
    public void move(Direction direction) {
        if (canMove(direction)) {
            for (int i = 0; i < PIECE_COUNT; i++)
                squares[i].move(direction);
        }
        // If we couldn't move, see if because we're at the bottom
        else if (direction == Direction.DOWN) {
            ableToMove = false;
        }
    }

    /**
     * Returns the (row, col) grid coordinates occupied by this Piece.
     * 
     * @return an Array of (row, col) Points
     */
    public Point[] getLocations() {
        Point[] points = new Point[PIECE_COUNT];
        for (int i = 0; i < PIECE_COUNT; i++) {
            points[i] = new Point(squares[i].getRow(), squares[i].getCol());
        }
        return points;
    }

    /**
     * Return the color of this piece.
     */
    public ColorScheme getColor() {
        // All squares of this piece have the same color
        return squares[0].getColor();
    }

    /**
     * Returns if this piece can move in the given direction.
     * 
     * @param direction the Direction to check for movement capability
     */
    public boolean canMove(Direction direction) {
        if (!ableToMove)
            return false;

        // Each square must be able to move in that direction
        boolean answer = true;
        for (int i = 0; i < PIECE_COUNT; i++) {
            answer = answer && squares[i].canMove(direction);
        }

        return answer;
    }

    /**
     * Returns if this piece can rotate.
     */
    public boolean canRotate() {
        if (!ableToMove)
            return false;

        // Each square must be able to move in that direction
        Point center = new Point(squares[CENTER].getCol(), squares[CENTER].getRow());
        boolean answer = true;

        // System.out.println("Rotating piece around (" + center + ")");

        for (int i = 0; i < PIECE_COUNT; i++) {
            if (i == CENTER) continue;
            // System.out.print("sq[" + i + "]: ");
            answer = answer && squares[i].canRotateAbout(center);
        }

        return answer;
    }

    /**
    * Rotate the Piece in the direction specified.
    */
    public void rotate() {
        if (canRotate()) {
            Point center = new Point(squares[CENTER].getCol(), squares[CENTER].getRow());
            for (int i = 0; i < PIECE_COUNT; i++) {
                if (i == CENTER) continue;
                squares[i].rotateAbout(center);
            }
        }
    }
}
