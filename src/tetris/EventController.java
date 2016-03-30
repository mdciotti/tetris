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

    // Current game: matrix and current piece
    private Game game;
    private Timer timer;

    /**
     * Creates an EventController to handle key and timer events.
     * 
     * @param game the game this is controlling
     */
    public EventController(Game game) {
        this.game = game;
        double delay = Tetris.START_SPEED; // in milliseconds
        timer = new Timer((int) delay, this);
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
            AudioManager.stopAll();
            ((JFrame) e.getSource()).dispose();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!game.isOver()) {
                game.pause();
                if (game.isPaused()) timer.stop();
                else timer.start();
            }
            return;
        }

        // Handle user input events only if the game is not over
        if (!game.isOver() && !game.isPaused()) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_X:
            case KeyEvent.VK_UP:
                game.rotatePiece();
                break;
            case KeyEvent.VK_DOWN:
                game.movePiece(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                game.movePiece(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                game.movePiece(Direction.RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                game.dropPiece();
                break;
            case KeyEvent.VK_C:
            case KeyEvent.VK_SHIFT:
                 game.holdPiece();
                break;
            }
        } else if (game.isOver()) {
            // Restart game on keypress
            game.restart();
            timer.setInitialDelay(1000);
            timer.restart();
        }
    }

    /**
     * Updates the game periodically based on a timer event.
     * 
     * @param e the ActionEvent passed to this handler
     */
    public void actionPerformed(ActionEvent e) {
        if (game.isOver()) timer.stop();
        else {
            game.tick();
            double speed = Tetris.START_SPEED * Math.pow(
                    Tetris.SPEED_GROWTH_FACTOR,
                    game.getLevel());
            timer.setDelay((int) Math.round(1000.0 / speed));
        }
    }
}
