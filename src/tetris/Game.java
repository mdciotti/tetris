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

    // Whether the game is over or not
    private boolean isOver;

    // The number of different pieces
    private final int NUM_PIECES = 7;

    /**
     * Creates a Tetris game.
     * 
     * @param Tetris the display
     */
    public Game(Tetris display) {
        playField = new PlayField();
        this.display = display;
        generatePiece(1, PlayField.WIDTH / 2 - 1);
        isOver = false;
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
        playField.checkRows();
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
        playField.checkRows();
        display.update();
    }

    /**
     * Returns true if the game is over.
     */
    public boolean isGameOver() {
        // Game is over if the piece occupies the same space as some non-empty
        // part of the playField. Usually happens when a new piece is made
        if (piece == null) return false;

        // Check if game is already over
        if (isOver) return true;

        // Check every part of the piece
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {
            if (playField.isSet((int) p[i].getX(), (int) p[i].getY())) {
                isOver = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Generate a new random piece at the specified location.
     *
     * @param row the row to set the center of the piece
     * @param col the column to set the center of the piece
     */
    private void generatePiece(int row, int col) {
        switch ((int)(NUM_PIECES * Math.random())) {
            default:
            case 0: piece = new ZShape(row, col, playField); break;
            case 1: piece = new OShape(row, col, playField); break;
            case 2: piece = new JShape(row, col, playField); break;
            case 3: piece = new TShape(row, col, playField); break;
            case 4: piece = new SShape(row, col, playField); break;
            case 5: piece = new IShape(row, col, playField); break;
            case 6: piece = new LShape(row, col, playField); break;
        }
    }

    /**
     * Updates the piece.
     */
    private void updatePiece() {
        if (piece == null) {
            // Create new LShape piece
            generatePiece(1, PlayField.WIDTH / 2 - 1);
        }

        // When the piece reaches 'ground', set PlayField positions corresponding to
        // frozen piece and then release the piece
        else if (!piece.canMove(Direction.DOWN)) {
            Point[] p = piece.getLocations();
            ColorScheme c = piece.getColor();
            for (int i = 0; i < p.length; i++) {
                playField.set((int) p[i].getX(), (int) p[i].getY(), c);
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
        playField.checkRows();
        display.update();
    }
}
