package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Create and control the game Tetris.
 */
public class Tetris extends JPanel {

    private Game game;

    private Font titleFont = new Font("Dosis", Font.BOLD, 32);
    private Font bodyFont = new Font("Dosis", Font.PLAIN, 20);

    /**
     * Sets up the parts for the Tetris game, display and user control.
     */
    public Tetris() {
        game = new Game(this);
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
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Draw the overlay background
            g2d.setColor(ColorScheme.BASE_07.color);
            g2d.fillRect(0, 150, getWidth(), 150);

            // Draw the overlay title
            String titleText = "G A M E   O V E R";
            g2d.setFont(titleFont);
            g2d.setColor(ColorScheme.BASE_02.color);
            FontMetrics fm = g2d.getFontMetrics(titleFont);
            int w = fm.stringWidth(titleText);
            g2d.drawString(titleText, (getWidth() - w) / 2, 200);
            
            // Draw the overlay body text
            String bodyText1 = "press any key to play again";
            String bodyText2 = "or press q to quit the game";
            g2d.setFont(bodyFont);
            g2d.setColor(ColorScheme.BASE_03.color);
            FontMetrics bfm = g2d.getFontMetrics(bodyFont);
            w = bfm.stringWidth(bodyText1);
            g2d.drawString(bodyText1, (getWidth() - w) / 2, 240);
            w = bfm.stringWidth(bodyText2);
            g2d.drawString(bodyText2, (getWidth() - w) / 2, 270);
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
