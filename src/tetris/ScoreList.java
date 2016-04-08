package tetris;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by max on 2016-04-01.
 */
public class ScoreList {

    private Tetris display;

    private ArrayList<PlayerScore> scores;

    private int selectedIndex = 0;

    private int currentPage = 1;

    private int numScores = 0;

    private static int numScoresPerPage = 7;

    // Set up default (fallback) fonts
    private static Font bodyFont = new Font("Letter Gothic Std", Font.PLAIN, 30);

    /**
     * Define a player score. A single topscore screen will have a few of these.
     */
    private class PlayerScore implements Comparable<PlayerScore> {
        private String player;
        private int score;
        public PlayerScore(String player, int score) {
            this.player = player;
            this.score = score;
        }

        /**
         * Sort high-to-low.
         */
        public int compareTo(PlayerScore ps) {
            return ps.score - score;
        }
    }

    public ScoreList(Tetris display) {
        this.display = display;
        scores = new ArrayList<>();
    }

    public void addScore(String player, int score) {
        scores.add(new PlayerScore(player, score));
        Collections.sort(scores);
        numScores++;
    }

    public void moveDown() {
        selectedIndex += 1;
        if (selectedIndex >= numScoresPerPage * currentPage) currentPage += 1;
        if (selectedIndex >= numScores) {
            selectedIndex = 0;
            currentPage = 1;
        }
        AudioManager.PIECE_MOVE.play();
        display.update();
    }

    public void moveUp() {
        selectedIndex -= 1;
        if (selectedIndex < numScoresPerPage * (currentPage - 1)) currentPage -= 1;
        if (selectedIndex < 0) {
            selectedIndex = numScores - 1;
            currentPage = (numScores / numScoresPerPage) + 1;
        }
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
        if (numScores == 0) {
            int w = bfm.stringWidth("no scores");
            g2d.drawString("no scores", (display.getWidth() - w) / 2, 200);
            return;
        }

        // Draw all scores
        int startIndex = (currentPage - 1) * numScoresPerPage;
        int endIndex = currentPage * numScoresPerPage;
        for (int i = startIndex; i < endIndex; i++) {
            if (i >= numScores) break;

            PlayerScore o = scores.get(i);
            g2d.setColor((i == selectedIndex) ?
                    ColorScheme.BASE_0D.color :
                    ColorScheme.BASE_03.color);

            int pad = 20;
            int y = 126 + (i - startIndex) * 40;
            int w;
            String numeral = nf.format(i + 1);

            // Draw numeral
            g2d.drawString(numeral, pad, y);

            // Draw player name
            w = bfm.stringWidth(numeral);
            g2d.drawString(o.player, pad + w + pad, y);

            // TODO: Draw dots
            w = bfm.stringWidth(o.player);

            // Draw score
            String score = nf.format(o.score);
            w = bfm.stringWidth(score);
            g2d.drawString(score, (display.getWidth() - w) - pad, y);
        }
    }
}
