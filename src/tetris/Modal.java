package tetris;

import java.awt.*;

/**
 * Created by max on 2016-03-27.
 */
public class Modal {

    // The title of this modal
    private String title;

    // The lines of body text in this modal
    private String body;

    // Whether this modal is currently visible
    private boolean visible = false;

    // Whether to draw a shade over the window when the modal is visible
    private boolean windowShade = true;

    // Set up default (fallback) fonts
    private static Font titleFont = new Font("Letter Gothic Std", Font.BOLD, 32);
    private static Font bodyFont = new Font("Letter Gothic Std", Font.PLAIN, 20);

    public Modal(String title) {
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public static void setTitleFont(Font font) {
        titleFont = font;
    }

    public static void setBodyFont(Font font) {
        bodyFont = font;
    }

    /**
     * Draws this modal on the window.
     *
     * @param g2d the Graphics2D context onto which we should draw
     * @param width the width of the window we're drawing to
     * @param height the height of the window we're drawing to
     */
    public void draw(Graphics2D g2d, int width, int height) {
        if (windowShade) {
            // Draw a shadow over the entire window
            Color shade = ColorScheme.BASE_00.color;
            g2d.setColor(new Color(shade.getRed(), shade.getGreen(), shade.getBlue(), 128));
            g2d.fillRect(0, 0, width, height);
        }

        // Draw the overlay background
        g2d.setColor(ColorScheme.BASE_07.color);
        g2d.fillRect(0, 150, width, 150);

        // Draw the overlay title
        g2d.setFont(titleFont);
        g2d.setColor(ColorScheme.BASE_02.color);
        FontMetrics fm = g2d.getFontMetrics(titleFont);
        int w = fm.stringWidth(title);
        g2d.drawString(title, (width - w) / 2, 200);

        // Draw the overlay body text
        String bodyText1 = "press any key to play again";
        String bodyText2 = "or press q to quit the game";
        g2d.setFont(bodyFont);
        g2d.setColor(ColorScheme.BASE_03.color);
        FontMetrics bfm = g2d.getFontMetrics(bodyFont);
        w = bfm.stringWidth(bodyText1);
        g2d.drawString(bodyText1, (width - w) / 2, 240);
        w = bfm.stringWidth(bodyText2);
        g2d.drawString(bodyText2, (width - w) / 2, 270);
    }
}
