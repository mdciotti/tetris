package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * An L-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 squares in the following configuration:
 * 
 * [L]
 * [L]
 * [L][L]
 * 
 * The game piece "floats above" the Grid. The (row, col) coordinates are the
 * location of the middle Square on the side within the Grid.
 */
public class LShape {
    // Whether this piece is frozen or not
    private boolean ableToMove;

    // The squares that make up this piece
    private Square[] squares;

    // The board this piece is on, made up of PIECE_COUNT squares
    private Grid grid;

    // Number of squares in one Tetris game piece
    private static final int PIECE_COUNT = 4;

    // The index of the rotational center of the game piece
    private static final int CENTER = 1;

    /**
     * Creates an L-Shape piece. See class description for actual location of
     * parameters r and c.
     * 
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the Grid for this game piece
     */
    public LShape(int r, int c, Grid g) {
        grid = g;
        squares = new Square[PIECE_COUNT];
        ableToMove = true;

        // Create the squares
        squares[0] = new Square(g, r - 1, c, Color.magenta, true);
        squares[1] = new Square(g, r, c, Color.magenta, true);
        squares[2] = new Square(g, r + 1, c, Color.magenta, true);
        squares[3] = new Square(g, r + 1, c + 1, Color.magenta, true);
    }

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
    public Color getColor() {
        // all squares of this piece have the same color
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

        System.out.println("Rotating piece around (" + center + ")");

        for (int i = 0; i < PIECE_COUNT; i++) {
            if (i == CENTER) continue;
            System.out.print("sq[" + i + "]: ");
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
