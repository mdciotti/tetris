package tetris;

import java.awt.*;

/**
 * Created by max on 2016-03-27.
 */
public class TextField extends InfoField {

    private String value;

    private static Font valueFont = new Font("Letter Gothic Std", Font.PLAIN, 32);

    public TextField(String name, int height) {
        setTitle(name);
        this.height = height;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void setValueFont(Font font) {
        valueFont = font;
    }

    public void draw(Graphics2D g2d, int x, int y) {
        super.draw(g2d, x, y);

        // Draw value
        g2d.setFont(valueFont);
        g2d.setColor(ColorScheme.BASE_07.color);
        FontMetrics fm = g2d.getFontMetrics(valueFont);
        int w = fm.stringWidth(value);
        g2d.drawString(value, x + (width - w) / 2, y + 45);
    }
}
