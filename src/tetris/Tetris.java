package tetris;

import java.awt.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Create and control the game Tetris.
 */
public class Tetris extends JPanel {

    private Game game;

    private TextField score, topScore, level, goal;
    private TetriminoField nextPiece, hold;

    private Modal gameOver;

    // The number of ticks per second
    public static double START_SPEED = 1.0;

    // The multiplier amount by which the speed increases every level
    public static double SPEED_GROWTH_FACTOR = 1.1;

    /**
     * Sets up the parts for the Tetris game, display and user control.
     */
    public Tetris() {

        // Create the game
        game = new Game(this);

        // Information Fields
        score = new TextField("SCORE", 60);
        topScore = new TextField("TOP SCORE", 60);
        level = new TextField("LEVEL", 60);
        goal = new TextField("GOAL", 60);
        nextPiece = new TetriminoField("NEXT");
        hold = new TetriminoField("HOLD");

        // Modals
        gameOver = new Modal("G A M E   O V E R");

        // Create window
        JFrame f = new JFrame("Tetris");
        f.add(this);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 420));
        setMinimumSize(new Dimension(500, 420));
        f.setResizable(false);
        f.pack();
        f.setVisible(true);
        EventController ec = new EventController(game);
        f.addKeyListener(ec);
        setBackground(ColorScheme.BASE_02.color);
    }

    /**
     * Updates the display.
     */
    public void update() {
        if (game != null) {
            if (game.isOver()) {
                gameOver.setBody("you scored 10 points");
                gameOver.setVisible(true);
            } else {
                gameOver.setVisible(false);
            }
        }
        repaint();
    }

    /**
     * Paint the current state of the game.
     * 
     * @param g the AWT Graphics context with which to draw
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the actual game
        game.draw(g);

        // Convert Graphics into Graphics2D for better rendering effects
        Graphics2D g2d = (Graphics2D) g;

        // Turn on anti-aliasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate sidebar offsets
        int rightSide = getWidth() / 2 + 130;
        int leftSide = getWidth() / 2 - 230;

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
        if (gameOver.isVisible()) gameOver.draw(g2d, getWidth(), getHeight());
    }

    private static void loadResources() {
        AudioManager.init();

        try {
            ClassLoader cl = Tetris.class.getClassLoader();
            InputStream dosisRegular = cl.getResourceAsStream("resources/dosis/Dosis-Regular.otf");
            InputStream dosisBold = cl.getResourceAsStream("resources/dosis/Dosis-Bold.otf");
            System.out.println("Loaded fonts");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, dosisRegular));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, dosisBold));
            Modal.setTitleFont(new Font("Dosis", Font.BOLD, 32));
            Modal.setBodyFont(new Font("Dosis", Font.PLAIN, 20));
            InfoField.setTitleFont(new Font("Dosis", Font.BOLD, 20));
            TextField.setValueFont(new Font("Dosis", Font.PLAIN, 32));
        } catch (Exception e) {
            System.err.println("Failed to load fonts.");
            e.printStackTrace();
        }
    }

    /**
     * Entry point to the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        loadResources();
        new Tetris();
    }
}
