package tetris;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Manages the game Tetris. Keeps track of the current piece and the playField.
 * Updates the display whenever the state of the game has changed.
 */
public class Game {

    // The PlayField that makes up the Tetris board
    private PlayField playField;

    // The visual for the Tetris game
    private Tetris display;

    // The current piece that is in play
    private Tetromino piece;

    // The next piece that will be in play
    private Tetromino nextPiece;

    // The preview of where the piece will fall
    private Tetromino ghost;

    // Whether the game is over or not
    private boolean isOver;

    // The number of different pieces
    private final int NUM_PIECES = 7;

    /**
     * Creates a Tetris game.
     * 
     * @param display the Tetris container for this game
     */
    public Game(Tetris display) {
        this.display = display;
        this.display.update();
        restart();
    }

    /**
     * Draws the current state of the game.
     * 
     * @param g the Graphics context on which to draw
     */
    public void draw(Graphics g) {

        playField.left = (display.getWidth() - PlayField.WIDTH * Square.WIDTH) / 2;
        playField.top = 10;

        playField.draw(g);
        if (ghost != null) ghost.draw(g);
        if (piece != null) piece.draw(g);
    }

    /**
     * Moves the piece in the given direction.
     * 
     * @param direction the direction to move
     */
    public void movePiece(Direction direction) {
        if (piece != null) {
            piece.move(direction);
        }
        update();
    }

    /**
     * Drops the piece to the bottom immediately.
     */
    public void dropPiece() {
        if (piece != null) {
            while (piece.canMove(Direction.DOWN))
                piece.move(Direction.DOWN);
            update();
        }
    }

    /**
     * Checks whether the game end condition has been met. The game is over
     * if the piece occupies the same space as some non-empty part of the
     * playField. This usually happens when a new piece is made.
     */
    private void checkEndCondition() {
        if (piece == null) return;

        // Check if game is already over
        if (isOver) return;

        // Check every part of the piece
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {
            if (playField.isSet((int) p[i].getX(), (int) p[i].getY())) {
                isOver = true;
                piece = null;
                ghost = null;
            }
        }
    }

    /**
     * Whether this game is over or not.
     * @return the game over state
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * Restarts the game from scratch.
     */
    public void restart() {
        playField = new PlayField();
        isOver = false;
        piece = generatePiece(0, PlayField.WIDTH / 2 - 1);
        updateGhost();
        display.update();
    }

    /**
     * Generate a new random piece at the specified location.
     *
     * @param row the row to set the center of the piece
     * @param col the column to set the center of the piece
     */
    private Tetromino generatePiece(int row, int col) {
        switch ((int)(NUM_PIECES * Math.random())) {
            default:
            case 0: return new ZShape(row, col, playField);
            case 1: return new OShape(row, col, playField);
            case 2: return new JShape(row + 1, col, playField);
            case 3: return new TShape(row, col, playField);
            case 4: return new SShape(row, col, playField);
            case 5: return new IShape(row, col, playField);
            case 6: return new LShape(row + 1, col, playField);
        }
    }

    /**
     * Runs all testing conditions and creates a new piece if need be.
     */
    public void update() {
        if (piece == null) {
            piece = generatePiece(0, PlayField.WIDTH / 2 - 1);
            checkEndCondition();
        } else {
            updatePiece();
        }
        updateGhost();
        playField.checkRows();
        display.update();
    }

    /**
     * Creates a new ghost piece and places it at the proper location.
     */
    private void updateGhost() {
        if (piece != null) {
            // Attempt to recreate the ghost
            ghost = piece.makeGhost();

            // If successfully created the ghost:
            if (ghost != null) {
                // Drop ghost to bottom
                while (ghost.canMove(Direction.DOWN))
                    ghost.move(Direction.DOWN);
            }
        }
    }

    /**
     * Updates the piece.
     */
    private void updatePiece() {
        // When the piece reaches 'ground', set PlayField positions corresponding to
        // frozen piece and then release the piece
        if (!piece.canMove(Direction.DOWN)) {
            Point[] p = piece.getLocations();
            ColorScheme c = piece.getColor();
            for (int i = 0; i < p.length; i++) {
                playField.set((int) p[i].getX(), (int) p[i].getY(), c);
            }
            piece = null;
            ghost = null;
        }
    }

    /**
     * Rotate the piece.
     */
    public void rotatePiece() {
        if (piece != null) piece.rotate();
        update();
    }
}
