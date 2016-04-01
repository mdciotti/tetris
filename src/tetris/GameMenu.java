package tetris;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by max on 2016-04-01.
 */
public class GameMenu {

    private Tetris display;

    private String title;

    private GameMenu parent;

    private ArrayList<MenuOption> options;

    private int selectedIndex = 0;

    private int numOptions = 0;

    // Set up default (fallback) fonts
    private static Font titleFont = new Font("Letter Gothic Std", Font.BOLD, 48);
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

    public GameMenu(Tetris display, String title) {
        this.display = display;
        this.title = title;
        options = new ArrayList<>();
    }

    public void setParent(GameMenu p) {
        parent = p;
    }

    public GameMenu getParent() {
        return parent;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static void setTitleFont(Font font) {
        titleFont = font;
    }

    public static void setBodyFont(Font font) {
        bodyFont = font;
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

        // Draw the menu options
        g2d.setFont(bodyFont);
        g2d.setColor(ColorScheme.BASE_03.color);
        FontMetrics bfm = g2d.getFontMetrics(bodyFont);

        for (int i = 0; i < options.size(); i++) {
            MenuOption o = options.get(i);
            g2d.setColor((i == selectedIndex) ?
                    ColorScheme.BASE_0D.color :
                    ColorScheme.BASE_03.color);
            w = bfm.stringWidth(o.title);
            g2d.drawString(o.title, (display.getWidth() - w) / 2, 200 + i * 40);
        }
    }
}
