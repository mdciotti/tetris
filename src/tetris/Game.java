package tetris;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Manages the game Tetris. Keeps track of the current piece and the matrix.
 * Updates the display whenever the state of the game has changed.
 */
public class Game {

    // The Matrix that makes up the Tetris board
    private Matrix matrix;

    // The visual for the Tetris game
    private Tetris display;

    // The current piece that is in play
    private Tetrimino piece;

    // The next piece that will be in play
    private Tetrimino nextPiece;

    // The preview of where the piece will fall
    private Tetrimino ghost;

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

        matrix.left = (display.getWidth() - Matrix.WIDTH * Mino.WIDTH) / 2;
        matrix.top = 10;

        matrix.draw(g);
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
     * matrix. This usually happens when a new piece is made.
     */
    private void checkEndConditions() {
        if (piece == null) return;

        // Check if game is already over
        if (isOver) return;

        // Check every part of the piece
        // NOTE: p.x = row, p.y = col
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {

            // Check for Block Out condition
            if (matrix.isSet((int) p[i].getX(), (int) p[i].getY())) {
                end();
                return;
            }

            // Check for Lock Out condition
            if (p[i].getY() < 0) {
                end();
                return;
            }
        }
    }

    private void end() {
        isOver = true;
        piece = null;
        ghost = null;
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
        matrix = new Matrix();
        isOver = false;
        piece = generatePiece(0, Matrix.WIDTH / 2 - 1);
        updateGhost();
        display.update();
    }

    /**
     * Generate a new random piece at the specified location.
     *
     * @param row the row to set the center of the piece
     * @param col the column to set the center of the piece
     */
    private Tetrimino generatePiece(int row, int col) {
        switch ((int)(NUM_PIECES * Math.random())) {
            default:
            case 0: return new ZShape(row, col, matrix);
            case 1: return new OShape(row, col, matrix);
            case 2: return new JShape(row + 1, col, matrix);
            case 3: return new TShape(row, col, matrix);
            case 4: return new SShape(row, col, matrix);
            case 5: return new IShape(row, col, matrix);
            case 6: return new LShape(row + 1, col, matrix);
        }
    }

    /**
     * Runs all testing conditions and creates a new piece if need be.
     */
    public void update() {
        if (piece == null) {
            piece = generatePiece(0, Matrix.WIDTH / 2 - 1);
            checkEndConditions();
        } else {
            updatePiece();
        }
        updateGhost();
        matrix.checkRows();
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
        // When the piece reaches 'ground', set Matrix positions corresponding to
        // frozen piece and then release the piece
        if (!piece.canMove(Direction.DOWN)) {
            checkEndConditions();
            piece.lockDown();
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
