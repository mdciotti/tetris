package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 */
public class Game {

    // The Grid that makes up the Tetris board
    private Grid grid;

    // The visual for the Tetris game
    private Tetris display;

    // The current piece that is in play
    private LShape piece;

    // Whether the game is over or not
    private boolean isOver;

    /**
     * Creates a Tetris game.
     * 
     * @param Tetris the display
     */
    public Game(Tetris display) {
        grid = new Grid();
        this.display = display;
        piece = new LShape(1, Grid.WIDTH / 2 - 1, grid);
        isOver = false;
    }

    /**
     * Draws the current state of the game.
     * 
     * @param g the Graphics context on which to draw
     */
    public void draw(Graphics g) {
        grid.draw(g);
        if (piece != null) {
            piece.draw(g);
        }
    }

    /**
     * Moves the piece in the given direction.
     * 
     * @param the direction to move
     */
    public void movePiece(Direction direction) {
        if (piece != null) {
            piece.move(direction);
        }
        updatePiece();
        grid.checkRows();
        display.update();
    }

    /**
     * Drops the piece to the bottom immediately.
     */
    public void dropPiece() {
        if (piece != null) {
            while (piece.canMove(Direction.DOWN))
                piece.move(Direction.DOWN);
        }
        updatePiece();
        grid.checkRows();
        display.update();
    }

    /**
     * Returns true if the game is over.
     */
    public boolean isGameOver() {
        // Game is over if the piece occupies the same space as some non-empty
        // part of the grid. Usually happens when a new piece is made
        if (piece == null) return false;

        // Check if game is already over
        if (isOver) return true;

        // Check every part of the piece
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {
            if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
                isOver = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the piece.
     */
    private void updatePiece() {
        if (piece == null) {
            // Create new LShape piece
            piece = new LShape(1, Grid.WIDTH / 2 - 1, grid);
        }

        // When the piece reaches 'ground', set Grid positions corresponding to
        // frozen piece and then release the piece
        else if (!piece.canMove(Direction.DOWN)) {
            Point[] p = piece.getLocations();
            Color c = piece.getColor();
            for (int i = 0; i < p.length; i++) {
                grid.set((int) p[i].getX(), (int) p[i].getY(), c);
            }
            piece = null;
        }
    }

    /**
     * Rotate the piece.
     */
    public void rotatePiece() {
        if (piece != null) {
            piece.rotate();
        }
        updatePiece();
        grid.checkRows();
        display.update();
    }
}
