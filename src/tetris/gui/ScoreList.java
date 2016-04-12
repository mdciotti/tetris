package tetris.gui;

import tetris.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by max on 2016-04-01.
 */
public class ScoreList {

    private Tetris display;

    private int selectedIndex = 0;

    private int currentPage = 1;

    private static int numScoresPerPage = 7;

    // Set up default (fallback) fonts
    private static Font bodyFont = new Font("Letter Gothic Std", Font.PLAIN, 30);

    public ScoreList(Tetris display) {
        this.display = display;
    }

    public void scrollToStart() {
        selectedIndex = 0;
        currentPage = 1;
    }

    public void scrollToEnd() {
        selectedIndex = ScoreManager.getNumScores() - 1;
        currentPage = (ScoreManager.getNumScores() / numScoresPerPage) + 1;
    }

    public void moveDown() {
        selectedIndex += 1;
        if (selectedIndex >= numScoresPerPage * currentPage) currentPage += 1;
        if (selectedIndex >= ScoreManager.getNumScores()) scrollToStart();
        AudioManager.PIECE_MOVE.play();
        display.update();
    }

    public void moveUp() {
        selectedIndex -= 1;
        if (selectedIndex < numScoresPerPage * (currentPage - 1)) currentPage -= 1;
        if (selectedIndex < 0) scrollToEnd();
        AudioManager.PIECE_MOVE.play();
        display.update();
    }

    public static void setBodyFont(Font font) {
        bodyFont = font;
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(bodyFont);
        g2d.setColor(ColorScheme.BASE_03.color);
        FontMetrics bfm = g2d.getFontMetrics(bodyFont);

        // Set up number formatting
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);

        // Draw "no scores" text
        if (ScoreManager.getNumScores() == 0) {
            int w = bfm.stringWidth("no scores");
            g2d.drawString("no scores", (display.getWidth() - w) / 2, 200);
            return;
        }

        // Draw all scores
        int startIndex = (currentPage - 1) * numScoresPerPage;
        int endIndex = currentPage * numScoresPerPage;
        for (int i = startIndex; i < endIndex; i++) {
            if (i >= ScoreManager.getNumScores()) break;

            PlayerScore o = ScoreManager.get(i);

            int pad = 20;
            int w = display.getWidth() - pad * 2;
            int y = 100 + (i - startIndex) * 40;
            String numeral = nf.format(i + 1);

            if (i == selectedIndex) {
                // Draw selection indication
                g2d.setColor(ColorScheme.BASE_0D.color);
                g2d.fillRect(pad, y, w, 40);
                g2d.setColor(ColorScheme.BASE_07.color);
            } else if (i % 2 == 0) {
                // Draw banded rows
                Color shade = ColorScheme.BASE_06.color;
                g2d.setColor(new Color(shade.getRed(), shade.getGreen(), shade.getBlue(), 96));
                g2d.fillRect(pad, y, w, 40);
                g2d.setColor(ColorScheme.BASE_03.color);
            } else {
                g2d.setColor(ColorScheme.BASE_03.color);
            }

            // Draw numeral
            g2d.drawString(numeral, pad * 2, y + 30);

            // Draw player name
            w = bfm.stringWidth(numeral);
            g2d.drawString(o.getPlayer(), pad * 2 + w + pad, y + 30);

            // Draw score
            String score = nf.format(o.getScore());
            w = bfm.stringWidth(score);
            g2d.drawString(score, (display.getWidth() - w) - pad * 2, y + 30);
        }
    }
}
