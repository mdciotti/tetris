package tetris.screens;

import tetris.*;
import tetris.gui.*;
import tetris.gui.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


/**
 * Encapsulates all drawing and events for the Tetris Game, such as user events
 * (key strokes) as well as periodic timer events.
 */
public class GameScreen extends Screen implements ActionListener {

    private Tetris display;

    private Game game;
    private Timer timer;

    private TextField score, topScore, level, goal;
    private TetriminoField nextPiece, hold;

    private Modal paused;
    private TextInputModal gameOver;

    // The number of ticks per second
    public static double START_SPEED = 1.0;

    // The multiplier amount by which the speed increases every level
    public static double SPEED_GROWTH_FACTOR = 1.15;

    /**
     * Sets up the parts for the Tetris game, display and user control.
     */
    public GameScreen(Tetris display) {
        registerType(ScreenType.GAME);
        this.display = display;

        double delay = GameScreen.START_SPEED; // in milliseconds
        timer = new Timer((int) delay, this);

        // Create the game
        game = new Game(display);

        // Information Fields
        score = new TextField("SCORE", 60);
        topScore = new TextField("TOP SCORE", 60);
        level = new TextField("LEVEL", 60);
        goal = new TextField("GOAL", 60);
        nextPiece = new TetriminoField("NEXT");
        hold = new TetriminoField("HOLD");

        // Modals
        gameOver = new TextInputModal("G A M E   O V E R");
        gameOver.setBody("enter your name to save score",
                "or press esc to quit to the menu");
        paused = new Modal("P A U S E D");
        paused.setBody("press escape to resume play",
                "or press q to quit to the menu");
    }

    public void load() {
        game.start();
        timer.start();

        gameOver.setVisible(false);
        paused.setVisible(false);
    }

    public void unload() {
        game.reset();
        timer.stop();

        gameOver.setVisible(false);
        paused.setVisible(false);
    }

    /**
     * Updates the screen.
     */
    public void update() {
        if (game != null) {
            if (game.isOver() && !gameOver.isVisible()) {
//                gameOver.setBody("you scored 10 points");
                gameOver.setInputText("Anonymous");
                gameOver.setVisible(true);
            } else if (!game.isOver()) {
                gameOver.setVisible(false);
            }

            paused.setVisible(game.isPaused());
        }
    }

    /**
     * Paint the current state of the game.
     *
     * @param g the AWT Graphics context with which to draw
     */
    public void draw(Graphics g) {

        // Draw the actual game
        game.draw(g);

        // Convert Graphics into Graphics2D for better rendering effects
        Graphics2D g2d = (Graphics2D) g;

        // Turn on anti-aliasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate sidebar offsets
        int rightSide = display.getWidth() / 2 + 130;
        int leftSide = display.getWidth() / 2 - 230;

        // Draw score
        score.setValue(Integer.toString(game.getScore()));
        score.draw(g2d, rightSide, 420 - 160);

        // Draw top score
        topScore.setValue(Integer.toString(game.getHighScore()));
        topScore.draw(g2d, rightSide, 420 - 80);

        // Draw level
        level.setValue(Integer.toString(game.getLevel()));
        level.draw(g2d, leftSide, 420 - 160);

        // Draw goal
        goal.setValue(Integer.toString(game.getGoal()));
        goal.draw(g2d, leftSide, 420 - 80);

        // Draw nextPiece
        nextPiece.setTetrimino(game.getNextPiece());
        nextPiece.draw(g2d, rightSide, 20);

        // Draw hold
        hold.setTetrimino(game.getHeldPiece());
        hold.draw(g2d, leftSide, 20);

        // Draw Game Over modal
        if (gameOver.isVisible()) gameOver.draw(g2d, display.getWidth(), display.getHeight());

        // Draw pause modal
        if (paused.isVisible()) paused.draw(g2d, display.getWidth(), display.getHeight());
    }

    /**
     * Responds to special keys being pressed. Currently just responds to the
     * space key and the Q (quit) key.
     *
     * @param e the KeyEvent containing info about the key pressed
     */
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!game.isOver()) {
                game.pause();
                if (game.isPaused()) timer.stop();
                else timer.start();
            }
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
            gameOver.keyPressed(e);
            display.update();

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                // Quit to menu (don't save score)
                display.transitionScreen(ScreenType.MAIN_MENU, Direction.RIGHT);
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // Save score and quit to menu
                ScoreManager.add(gameOver.getTextInput(), game.getScore());
                display.transitionScreen(ScreenType.MAIN_MENU, Direction.RIGHT);
            }
        } else if (game.isPaused()) {
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                // Quit to menu
                display.transitionScreen(ScreenType.MAIN_MENU, Direction.RIGHT);
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if (game.isOver()) {
            gameOver.keyTyped(e);
            display.update();
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
            double speed = GameScreen.START_SPEED * Math.pow(
                    GameScreen.SPEED_GROWTH_FACTOR,
                    game.getLevel());
            timer.setDelay((int) Math.round(1000.0 / speed));
        }
    }
}
