package tetris.gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by max on 2016-04-26.
 */
public class TextBox {
    private ArrayList<String> paragraphs;
    private int width;
    private int height;
    private ArrayList<String> lines;
    private Font textFont;
    private int lineHeight = 30;
    private int fontSize = 20;
    private Color textColor = ColorScheme.BASE_04.color;

    private int lastComputedWidth = 0;

    public TextBox(int w, int h) {
        width = w;
        height = h;
        textFont = new Font("Dosis", Font.PLAIN, fontSize);
        paragraphs = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public void addParagraph(String p) {
        paragraphs.add(p);
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    private synchronized void wrapLines(FontMetrics fm) {
        // Only re-calculate line wrapping if width has been changed
        if (width != lastComputedWidth) {

            lines.clear();
            for (String p : paragraphs) {
                String[] tokens = p.split("[ -]");
                String str = "";

                int numChars = 0;
                int i = 0;
                while (i < tokens.length) {
                    String token = tokens[i];
                    int w = fm.stringWidth(str + token);
                    if (w > width) {
                        lines.add(str);
                        str = "";
                    } else {
                        str += token;
                        numChars += token.length();

                        // Re-add delimiter
                        if (i != tokens.length - 1)
                            str += p.charAt(numChars++);

                        i += 1;
                    }
                }
                // Add last line
                lines.add(str);
            }

            lastComputedWidth = width;
        }
    }

    public void draw(Graphics2D g2d, int x, int y) {

        g2d.setFont(textFont);
        g2d.setColor(textColor);
        wrapLines(g2d.getFontMetrics(textFont));

        if (lines != null) {
            int i = 1;
            for (String line : lines) {
                g2d.drawString(line, x, y + i * lineHeight);
                i += 1;
            }
        }
    }
}
