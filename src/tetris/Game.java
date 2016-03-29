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

    // The piece that the player is holding off to the side
    private Tetrimino heldPiece;

    // Whether the player held the last piece or not
    private boolean lastPieceHeld = false;

    // The preview of where the piece will fall
    private Tetrimino ghost;

    // Whether the game is over or not
    private boolean isOver;

    // The current score
    private int score;

    // The high score of the current playing session
    private int highScore = 0;

    // The current level (difficulty)
    private int level;

    // The number of line clears left to the next level
    private int goal;

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

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getLevel() {
        return level;
    }

    public int getGoal() {
        return goal;
    }

    /**
     * Gets the next Tetrimino in the queue.
     *
     * @return the next Tetrimino
     */
    public Tetrimino getNextPiece() {
        return nextPiece;
    }

    /**
     * Gets the Tetrimino that was previously held off to the side by the
     * player, or null if no Tetrimino has been held yet.
     *
     * @return the held Tetrimino (or null if none is held)
     */
    public Tetrimino getHeldPiece() {
        return heldPiece;
    }

    /**
     * Holds the currently falling piece off to the side so that the player can
     * use it later when it is more convenient.
     */
    public void holdPiece() {
        // Don't do anything if the last piece was held
        if (lastPieceHeld) {
            AudioManager.NO_HOLD.play();
            return;
        }

        // Don't do anything if piece is null
        if (piece == null) {
            AudioManager.NO_HOLD.play();
            return;
        }

        AudioManager.HOLD.play();

        if (heldPiece == null) {
            // No piece in hold yet
            heldPiece = piece;
            piece = null;
            update();
        } else {
            // A piece is already in hold, swap
            Tetrimino temp = piece;
            piece = heldPiece;
            piece.setMatrix(matrix);
            piece.setPosition(1, 4);
            heldPiece = temp;
            update();
        }

        heldPiece.setPosition(1, 2);
        lastPieceHeld = true;
    }

    /**
     * Draws the current state of the game.
     * 
     * @param g the Graphics context on which to draw
     */
    public void draw(Graphics g) {

        int w = matrix.getCols() * Mino.WIDTH;
        matrix.setPosition((display.getWidth() - w) / 2, 10);

        matrix.draw(g);
        if (ghost != null) ghost.draw(g);
        if (piece != null) piece.draw(g);
    }

    /**
     * On every tick the game piece falls down one block. Over time, the amount
     * of time between ticks decreases to make the game progressively harder.
     */
    public void tick() {
        if (piece != null)
            piece.move(Direction.DOWN);
        update();
    }

    /**
     * Moves the current Tetrimino in the given direction.
     * 
     * @param direction the direction to move
     */
    public void movePiece(Direction direction) {
        if (piece != null) {
            AudioManager.PIECE_MOVE.play();
            piece.move(direction);
            if (direction == Direction.DOWN) score++;
        }
        update();
    }

    /**
     * Drops the current Tetrimino to the bottom immediately.
     */
    public void dropPiece() {
        if (piece != null) {
            // AudioManager.HARD_DROP.play();
            int m = 0;
            while (piece.canMove(Direction.DOWN)) {
                piece.move(Direction.DOWN);
                m++;
            }
            score += 2 * m;
            updatePiece();
            update();
        }
    }

    /**
     * Checks whether the game end condition "Block Out" has been met. The game
     * is over if the piece occupies the same space as some non-empty part of
     * the matrix. This usually happens when a new piece is made.
     *
     * @return true if a blockout condition is detected
     */
    private boolean checkBlockOut() {
        if (piece == null) return false;

        // Check if game is already over
        if (isOver) return false;

        // Check every part of the piece
        // NOTE: p.x = row, p.y = col
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {
            int row = (int) p[i].getX();
            int col = (int) p[i].getY();
            if (row > 0) {
                // Check for Block Out condition
                if (matrix.isSet(row, col)) return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the game end condition "Lock Out" has been met. This
     * occurs when a piece is locked down at least partially above the top of
     * the Matrix.
     *
     * @return true if a lockout condition is detected
     */
    private boolean checkLockOut() {
        if (piece == null) return false;

        // Check if game is already over
        if (isOver) return false;

        // Check every part of the piece
        // NOTE: p.x = row, p.y = col
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {
            int row = (int) p[i].getX();

            // Check for Lock Out condition
            if (row < 0) return true;
        }

        return false;
    }

    /**
     * Ends the current game.
     */
    private void end() {
        isOver = true;
        piece = null;
        ghost = null;
    }

    /**
     * Whether this game is over or not.
     *
     * @return the game over state
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * Restarts the game from scratch.
     */
    public void restart() {
        goal = 5;
        level = 1;
        score = 0;
        matrix = new Matrix(20, 10);
        matrix.setPosition(100, 50);
        isOver = false;
        heldPiece = null;
        lastPieceHeld = false;
        nextPiece = generatePiece(null, 1, 2);
        piece = generatePiece(matrix, 0, matrix.getCols() / 2 - 1);
        updateGhost();
        display.update();
    }

    /**
     * Generate a new random piece at the specified location.
     *
     * @param row the row to set the center of the piece
     * @param col the column to set the center of the piece
     */
    private Tetrimino generatePiece(Matrix m, int row, int col) {
        switch ((int)(NUM_PIECES * Math.random())) {
            default:
            case 0: return new ZShape(row, col, m);
            case 1: return new OShape(row, col, m);
            case 2: return new JShape(row + 1, col + 1, m);
            case 3: return new TShape(row, col, m);
            case 4: return new SShape(row, col, m);
            case 5: return new IShape(row, col, m);
            case 6: return new LShape(row + 1, col - 1, m);
        }
    }

    /**
     * Runs all testing conditions and creates a new piece if need be.
     */
    public void update() {
        if (piece == null) {
            piece = nextPiece;
            piece.setMatrix(matrix);
            piece.setPosition(0, matrix.getCols() / 2 - 1);
            nextPiece = generatePiece(null, 1, 2);
            lastPieceHeld = false;
            if (checkBlockOut()) end();
        } else {
            updatePiece();
        }
        updateGhost();

        int numLinesCleared = matrix.checkRows();
        int pointsAwarded = 0;
        int lineClearsAwarded = 0;

        // TODO: determine if back-to-back
        boolean backToBack = false;

        switch (numLinesCleared) {
            case 1:
                AudioManager.LINE_CLEAR_1.play();
                pointsAwarded = 100 * level;
                lineClearsAwarded = 1;
                break;
            case 2:
                AudioManager.LINE_CLEAR_2.play();
                pointsAwarded = 300 * level;
                lineClearsAwarded = 3;
                break;
            case 3:
                AudioManager.LINE_CLEAR_3.play();
                pointsAwarded = 500 * level;
                lineClearsAwarded = 5;
                break;
            case 4:
                AudioManager.LINE_CLEAR_4.play();
                pointsAwarded = 800 * level;
                lineClearsAwarded = 8;
                break;
        }

        if (backToBack) {
            // AudioManager.BACK_TO_BACK.play();
            // TODO: keep track of points better, to include points earned from
            // hard and soft drops
            lineClearsAwarded += lineClearsAwarded;
            pointsAwarded += pointsAwarded;
        }

        score += pointsAwarded;

        // Assign high score if current is best
        if (score > highScore) highScore = score;

        if (goal - lineClearsAwarded <= 0) {
            // AudioManager.LEVEL_UP.play();
            level++;
            goal = level * 5;
        } else {
            goal -= lineClearsAwarded;
        }

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
        // When the piece reaches 'ground', set Matrix positions corresponding
        // to frozen piece and then release the piece
        if (!piece.canMove(Direction.DOWN)) {
            if (checkLockOut()) end();
            else piece.lockDown();
            piece = null;
            ghost = null;
        }
    }

    /**
     * Rotate the piece.
     */
    public void rotatePiece() {
        if (piece != null) {
            AudioManager.PIECE_MOVE.play();
            piece.rotate();
        }
        update();
    }
}
