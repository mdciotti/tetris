package tetris;

import java.awt.*;

/**
 * Created by max on 2016-03-27.
 */
abstract public class InfoField {

    private String title;

    protected int height;
    protected int width = 100;

    protected static Font titleFont = new Font("Letter Gothic Std", Font.PLAIN, 20);

    public void setTitle(String title) {
        this.title = title;
    }

    public static void setTitleFont(Font font) {
        titleFont = font;
    }

    public void draw(Graphics2D g2d, int x, int y) {
        // Draw background
        g2d.setColor(ColorScheme.BASE_00.color);
        g2d.fillRect(x, y, width, height);

        // Draw title
        g2d.setFont(titleFont);
        g2d.setColor(ColorScheme.BASE_04.color);
        FontMetrics fm = g2d.getFontMetrics(titleFont);
        int w = fm.stringWidth(title);
        g2d.drawString(title, x + (width - w) / 2, y + 8);
    }
}
