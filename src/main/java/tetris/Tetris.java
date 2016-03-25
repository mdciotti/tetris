package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
        setBackground(ColorScheme.BASE_02.color);
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

        // Convert Graphics into Graphics2D for better rendering effects
        Graphics2D g2d = (Graphics2D) g;

        // Turn on anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (game.isGameOver()) {
            // Draw a shadow over the entire window
            Color shade = ColorScheme.BASE_00.color;
            g2d.setColor(new Color(shade.getRed(), shade.getGreen(), shade.getBlue(), 128));
            g2d.fillRect(0, 0, 400, 550);
            
            // Draw the overlay background
            g2d.setColor(ColorScheme.BASE_05.color);
            g2d.fillRect(0, 150, 400, 150);

            // Draw the overlay title
            g2d.setFont(new Font("Menlo", Font.PLAIN, 32));
            g2d.setColor(ColorScheme.BASE_01.color);
            g2d.drawString("G A M E   O V E R", 40, 200);
            
            // Draw the overlay subtext
            g2d.setFont(new Font("Menlo", Font.PLAIN, 20));
            g2d.setColor(ColorScheme.BASE_03.color);
            g2d.drawString("press any key to play again", 40, 240);
            g2d.drawString("or press q to quit the game", 40, 270);
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
