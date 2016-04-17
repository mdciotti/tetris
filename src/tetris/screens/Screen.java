package tetris.screens;

import tetris.Tetris;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Abstracts the concept of a screen so that multiple screens can be defined
 * and swapped, each containing their own state and update/draw methods.
 */
public abstract class Screen implements KeyListener {

    protected Tetris display;

    private ScreenType type;

    public abstract void draw(Graphics g);
    public void update() {}

    public Tetris getDisplay() {
        return display;
    }

    public void registerType(ScreenType s) {
        type = s;
    }

    public ScreenType getType() {
        return type;
    }

    public int getWidth() {
        return display.getWidth();
    }

    public int getHeight() {
        return display.getHeight();
    }

    // Define default load and unload methods
    // Subclasses may override these for their own purposes
    public void load() {}

    /**
     * Unload is called after the screen has fully left the view (if
     * transitioning to a new screen) or immediately if a new screen is set
     * explicitly. Subclasses of Screen are expected but not required to
     * override this method with their own unloading code.
     */
    public void unload() {}

    /**
     * Responds to special keys being pressed.
     *
     * @param e the KeyEvent containing info about the key pressed
     */
    public void keyPressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
