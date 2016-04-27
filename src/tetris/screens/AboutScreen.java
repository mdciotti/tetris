package tetris.screens;

import tetris.Direction;
import tetris.Tetris;
import tetris.gui.ColorScheme;
import tetris.gui.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by max on 2016-04-16.
 */
public class AboutScreen extends Screen {

    private TextBox text;

    public AboutScreen(Tetris display) {
        registerType(ScreenType.ABOUT);
        this.display = display;
        text = new TextBox(display.getWidth() - 40, display.getHeight() - 140);
        text.addParagraph("Tetriminos is not affiliated with The Tetris Company or Tetris Holdings LLC.");
        text.addParagraph("Made by Brandon Popson and Maxwell Ciotti");
        text.addParagraph("v0.8.0");
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
        String title = "ABOUT";
        Font titleFont = new Font("Dosis", Font.BOLD, 36);
        g2d.setFont(titleFont);
        g2d.setColor(ColorScheme.BASE_02.color);
        g2d.drawString(title, 20, 76);

        // Draw textbox
        text.setSize(display.getWidth() - 40, display.getHeight() - 140);
        text.draw(g2d, 20, 100);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                display.transitionScreen(ScreenType.OPTIONS, Direction.LEFT);
                break;
        }
    }
}
