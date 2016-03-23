package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Create and control the game Tetris.
 */
public class Tetris extends JPanel {

    private Game game;

    /**
     * Sets up the parts for the Tetris game, display and user control.
     */
    public Tetris() {
        game = new Game(this);
        JFrame f = new JFrame("The Tetris Game");
        f.add(this);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(400, 550);
        f.setVisible(true);
        EventController ec = new EventController(game);
        f.addKeyListener(ec);
        setBackground(Color.GRAY);
    }

    /**
     * Updates the display.
     */
    public void update() {
        repaint();
    }

    /**
     * Paint the current state of the game.
     * 
     * @param g the AWT Graphics context with which to draw
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
        if (game.isGameOver()) {
            g.setFont(new Font("Palatino", Font.BOLD, 40));
            g.setColor(Color.BLACK);
            g.drawString("GAME OVER", 80, 300);
        }
    }

    /**
     * Entry point to the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Tetris();
    }
}
