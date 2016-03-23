package tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Handles events for the Tetris Game, such as user events (key strokes) as well
 * as periodic timer events.
 */
public class EventController extends KeyAdapter implements ActionListener {

    // Current game: grid and current piece
    private Game game;
    private Timer timer;

    // Wait 0.8 s every time the piece moves down, increase to slow it down
    private static final double PIECE_MOVE_TIME = 0.8;

    private boolean gameOver;

    /**
     * Creates an EventController to handle key and timer events.
     * 
     * @param game the game this is controlling
     */
    public EventController(Game game) {
        this.game = game;
        gameOver = false;
        double delay = 1000 * PIECE_MOVE_TIME; // in milliseconds
        timer = new Timer((int) delay, this);
        // If multiple events pending, bunch them to one event
        timer.setCoalesce(true);
        timer.start();
    }

    /**
     * Responds to special keys being pressed. Currently just responds to the
     * space key and the Q (quit) key.
     * 
     * @param e the KeyEvent containing info about the key pressed
     */
    public void keyPressed(KeyEvent e) {
        // If key pressed was 'Q', quit the game
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            timer.stop();
            ((JFrame) e.getSource()).dispose();
        }

        // Handle user input events only if the game is not over
        if (!gameOver) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                handleMove(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                handleMove(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                handleMove(Direction.RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                game.rotatePiece();
                break;
            }
        }
    }

    /**
     * Updates the game periodically based on a timer event.
     * 
     * @param e the ActionEvent passed to this handler
     */
    public void actionPerformed(ActionEvent e) {
        handleMove(Direction.DOWN);
    }

    /**
     * Update the game by moving in the given direction.
     * 
     * @param direction the Direction that was pressed
     */
    private void handleMove(Direction direction) {
        game.movePiece(direction);
        gameOver = game.isGameOver();
        if (gameOver)
            timer.stop();
    }
}
