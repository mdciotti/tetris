package tetris.screens;

import tetris.*;
import tetris.gui.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by max on 2016-04-01.
 */
public class TopScoreScreen extends Screen {
    private ScoreList scores;

    private Tetris display;

    public TopScoreScreen(Tetris display) {
        registerType(ScreenType.TOP_SCORES);
        this.display = display;

        scores = new ScoreList(display);
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Turn on anti-aliasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g2d.setColor(ColorScheme.BASE_00.color);
        g2d.fillRect(0, 0, display.getWidth(), display.getHeight());

        // Draw the overlay background
        g2d.setColor(ColorScheme.BASE_07.color);
        g2d.fillRect(0, 20, display.getWidth(), display.getHeight() - 40);

        // Draw the menu title
        String title = "TOP SCORES";
        Font titleFont = new Font("Dosis", Font.BOLD, 36);
        g2d.setFont(titleFont);
        g2d.setColor(ColorScheme.BASE_02.color);
        g2d.drawString(title, 20, 76);

        scores.draw(g);
    }

    public void load() { scores.scrollToStart(); }
    public void unload() { scores.scrollToStart(); }

    public void update() {}

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                display.transitionScreen(ScreenType.MAIN_MENU);
                break;
            case KeyEvent.VK_DOWN:
                scores.moveDown();
                break;
            case KeyEvent.VK_UP:
                scores.moveUp();
                break;
        }
    }
}
