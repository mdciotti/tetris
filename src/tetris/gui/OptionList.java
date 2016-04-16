package tetris.gui;

import tetris.AudioManager;
import tetris.Direction;
import tetris.ScoreManager;
import tetris.Tetris;
import tetris.screens.ScreenType;

import java.awt.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by max on 2016-04-12.
 */
public class OptionList implements Serializable {

    private Tetris display;

    private int selectedIndex = 0;

    private int currentPage = 1;

    private static int numOptionsPerPage = 7;

    private ArrayList<OptionItem> options;

    // Set up default (fallback) fonts
    private static Font bodyFont = new Font("Letter Gothic Std", Font.PLAIN, 30);



    private class ScreenOptionItem extends OptionItem {
        private ScreenType screen;
        private Direction transitionDirection;

        public ScreenOptionItem(String name, ScreenType s, Direction d) {
            setName(name);
            screen = s;
            transitionDirection = d;
        }

        public void select() {
            display.transitionScreen(screen, transitionDirection);
        }
    }

    public OptionList(Tetris tetris) {
        display = tetris;
        options = new ArrayList<>();
    }

    public Tetris getDisplay() {
        return display;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNumOptionsPerPage() {
        return numOptionsPerPage;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void add(OptionItem o) {
        options.add(o);
        o.parent = this;
    }

    public void select() {
        options.get(selectedIndex).select();
    }

    public void scrollToStart() {
        selectedIndex = 0;
        currentPage = 1;
    }

    public void scrollToEnd() {
        selectedIndex = options.size() - 1;
        currentPage = (options.size() / numOptionsPerPage) + 1;
    }

    public void moveDown() {
        selectedIndex += 1;
        if (selectedIndex >= numOptionsPerPage * currentPage) currentPage += 1;
        if (selectedIndex >= options.size()) scrollToStart();
        AudioManager.play(AudioManager.PIECE_MOVE);
        display.update();
    }

    public void moveUp() {
        selectedIndex -= 1;
        if (selectedIndex < numOptionsPerPage * (currentPage - 1)) currentPage -= 1;
        if (selectedIndex < 0) scrollToEnd();
        AudioManager.play(AudioManager.PIECE_MOVE);
        display.update();
    }

    public void keyLeft() {
        // Delegate to option item
        options.get(selectedIndex).keyLeft();
    }

    public void keyRight() {
        // Delegate to option item
        options.get(selectedIndex).keyRight();
    }

    public static void setBodyFont(Font font) {
        bodyFont = font;
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(bodyFont);
        g2d.setColor(ColorScheme.BASE_03.color);
        FontMetrics bfm = g2d.getFontMetrics(bodyFont);

        // Draw "no options" text
        if (options.isEmpty()) {
            int w = bfm.stringWidth("no options");
            g2d.drawString("no options", (display.getWidth() - w) / 2, 200);
            return;
        }

        // Draw all options
        int startIndex = (currentPage - 1) * numOptionsPerPage;
        int endIndex = currentPage * numOptionsPerPage;
        for (int i = startIndex; i < endIndex; i++) {
            if (i >= options.size()) break;
            options.get(i).draw(g2d, i);
        }
    }
}

