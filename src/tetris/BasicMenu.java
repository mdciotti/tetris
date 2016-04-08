package tetris;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by max on 2016-04-01.
 */
public class BasicMenu {

    private Tetris display;

    private BasicMenu parent;

    private ArrayList<MenuOption> options;

    private int selectedIndex = 0;

    private int numOptions = 0;

    // Set up default (fallback) fonts
    private static Font bodyFont = new Font("Letter Gothic Std", Font.PLAIN, 30);

    /**
     * Define a menu option. A single menu will have a few of these.
     */
    private abstract class MenuOption {
        private String title;
        public void setTitle(String title) {
            this.title = title;
        };
        public abstract void select();
    }

    /**
     * A screen menu option takes the user to a new screen.
     */
    private class ScreenMenuOption extends MenuOption {
        private ScreenType screen;

        public ScreenMenuOption(String title, ScreenType s) {
            setTitle(title);
            screen = s;
        }

        public void select() {
            display.setScreen(screen);
        }
    }

    /**
     * An action menu option performs an action.
     */
    private class ActionMenuOption extends MenuOption {
        private GameAction action;

        public ActionMenuOption(String title, GameAction a) {
            setTitle(title);
            action = a;
        }

        public void select() {
            action.perform();
        }
    }

    public BasicMenu(Tetris display) {
        this.display = display;
        options = new ArrayList<>();
    }

    public void setParent(BasicMenu p) {
        parent = p;
    }

    public BasicMenu getParent() {
        return parent;
    }

    public void addOption(String title, ScreenType s) {
        options.add(new ScreenMenuOption(title, s));
        numOptions++;
    }

    public void addOption(String title, GameAction a) {
        options.add(new ActionMenuOption(title, a));
        numOptions++;
    }

    public void moveDown() {
        selectedIndex += 1;
        if (selectedIndex >= numOptions) selectedIndex -= numOptions;
        AudioManager.PIECE_MOVE.play();
        display.update();
    }

    public void moveUp() {
        selectedIndex -= 1;
        if (selectedIndex < 0) selectedIndex += numOptions;
        AudioManager.PIECE_MOVE.play();
        display.update();
    }

    public void select() {
        AudioManager.LINE_CLEAR_1.play();
        options.get(selectedIndex).select();
    }

    public static void setBodyFont(Font font) {
        bodyFont = font;
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Draw the menu options
        g2d.setFont(bodyFont);
        g2d.setColor(ColorScheme.BASE_03.color);
        FontMetrics bfm = g2d.getFontMetrics(bodyFont);

        for (int i = 0, w; i < options.size(); i++) {
            MenuOption o = options.get(i);
            g2d.setColor((i == selectedIndex) ?
                    ColorScheme.BASE_0D.color :
                    ColorScheme.BASE_03.color);
            w = bfm.stringWidth(o.title);
            g2d.drawString(o.title, (display.getWidth() - w) / 2, 200 + i * 40);
        }
    }
}
