package tetris;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by max on 2016-04-01.
 */
public class MenuScreen extends Screen {
    private GameMenu menu;

    private Tetris display;

    public MenuScreen(Tetris display) {
        this.display = display;

        menu = new GameMenu(display, "T E T R I S");
        menu.addOption("play", ScreenType.GAME);
        menu.addOption("top scores", ScreenType.TOP_SCORES);
        menu.addOption("settings", ScreenType.SETTINGS);
        menu.addOption("quit", GameAction.QUIT);
    }

    public void draw(Graphics g) {
        menu.draw(g);
    }

    public void update() {}

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (menu.getParent() == null) {
                display.quit();
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
