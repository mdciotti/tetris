package tetris.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Abstracts the concept of a screen so that multiple screens can be defined
 * and swapped, each containing their own state and update/draw methods.
 */
public abstract class Screen implements KeyListener {

    public abstract void draw(Graphics g);
    public abstract void update();

    // Define default load and unload methods
    // Subclasses may override these for their own purposes
    public void load() {}
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
