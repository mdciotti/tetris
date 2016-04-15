package tetris.gui;

import tetris.Tetris;
import tetris.screens.Screen;
import tetris.util.GaussianBlur;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Created by max on 2016-03-27.
 */
public class Modal implements KeyListener {

    // The title of this modal
    private String title;

    // The lines of body text in this modal
    private String[] body;

    // Whether this modal is currently visible
    private boolean visible = false;

    // The height of the modal window
    private int height;

    // Whether to draw a shade over the window when the modal is visible
    private boolean windowShade = true;

    // Whether to blur the contents behind the modal window
    private boolean blurBackground = true;
    private BufferedImage bgBlur;
    GaussianBlur blurEffect;

    // A reference to the screen onto which this modal window should be drawn
    protected Screen screen;

    // Set up default (fallback) fonts
    private static Font titleFont = new Font("Letter Gothic Std", Font.BOLD, 32);
    protected static Font bodyFont = new Font("Letter Gothic Std", Font.PLAIN, 20);

    public Modal(String title, Screen s) {
        screen = s;
        setTitle(title);
        body = new String[2];
        setHeight(150);

        blurEffect = new GaussianBlur(8, 3);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String line1, String line2) {
        this.body[0] = line1;
        this.body[1] = line2;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (visible && blurBackground) {
            // Blur the background behind the modal
            Tetris display = screen.getDisplay();
            BufferedImage buffer = display.getScreenBuffer();
            bgBlur = blurEffect.apply(buffer);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public int getHeight() { return height; }

    public void setHeight(int h) { this.height = h; }

    public static void setTitleFont(Font font) {
        titleFont = font;
    }

    public static void setBodyFont(Font font) {
        bodyFont = font;
    }

    public void keyPressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    /**
     * Draws this modal on the window.
     *
     * @param g2d the Graphics2D context onto which we should draw
     */
    public void draw(Graphics2D g2d) {

        int width = screen.getWidth();
        int height = screen.getHeight();

        if (blurBackground) {
            // Draw the blurred background
            g2d.drawImage(bgBlur, 0, 0, null);
        }

        if (windowShade) {
            // Draw a shadow over the entire window
            Color shade = ColorScheme.BASE_00.color;
            g2d.setColor(new Color(shade.getRed(), shade.getGreen(), shade.getBlue(), 128));
            g2d.fillRect(0, 0, width, height);
        }

        int y = (height - this.height) / 2;

        // Draw the overlay background
        g2d.setColor(ColorScheme.BASE_07.color);
        g2d.fillRect(0, y, width, this.height);

        // Draw the overlay title
        g2d.setFont(titleFont);
        g2d.setColor(ColorScheme.BASE_02.color);
        FontMetrics fm = g2d.getFontMetrics(titleFont);
        int w = fm.stringWidth(title);
        g2d.drawString(title, (width - w) / 2, y + 50);

        // Draw the overlay body text

        g2d.setFont(bodyFont);
        g2d.setColor(ColorScheme.BASE_03.color);
        FontMetrics bfm = g2d.getFontMetrics(bodyFont);

        for (int i = 0; i < body.length; i++) {
            w = bfm.stringWidth(body[i]);
            g2d.drawString(body[i], (width - w) / 2, y + 90 + i * 30);
        }
    }
}
