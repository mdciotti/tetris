package tetris.screens;

import tetris.*;
import tetris.gui.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by max on 2016-04-01.
 */
public class MenuScreen extends Screen {
    private BasicMenu menu;
    private String title;
    private Tetris display;
    private static Font titleFont = new Font("Letter Gothic Std", Font.BOLD, 48);

    public MenuScreen(Tetris display) {
        this.display = display;
        title = "T E T R I S";

        menu = new BasicMenu(display);
        menu.addOption("play", ScreenType.GAME);
        menu.addOption("top scores", ScreenType.TOP_SCORES);
        menu.addOption("settings", ScreenType.SETTINGS);
        menu.addOption("quit", GameAction.QUIT);
    }

    public static void setTitleFont(Font font) {
        titleFont = font;
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
        g2d.setFont(titleFont);
        g2d.setColor(ColorScheme.BASE_02.color);
        FontMetrics fm = g2d.getFontMetrics(titleFont);
        int w = fm.stringWidth(title);
        g2d.drawString(title, (display.getWidth() - w) / 2, 120);

        menu.draw(g);
    }

    public void update() {}

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (menu.getParent() == null) {
                Tetris.quit();
            }
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                menu.moveDown();
                break;
            case KeyEvent.VK_UP:
                menu.moveUp();
                break;
            case KeyEvent.VK_ENTER:
                menu.select();
                break;
        }
    }
}
