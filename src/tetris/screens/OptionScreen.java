package tetris.screens;

import tetris.*;
import tetris.gui.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by max on 2016-04-01.
 */
public class OptionScreen extends Screen {

    OptionList options;

    OptionItem.Toggle fullScreen;

    public OptionScreen(Tetris display) {
        registerType(ScreenType.OPTIONS);
        this.display = display;

        options = new OptionList(display);

        // Full Screen Toggle
        fullScreen = new OptionItem.Toggle("Full screen", display.getWindow().isFullScreen());
        fullScreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameAction.FULL_SCREEN.perform();
            }
        });
        options.add(fullScreen);

        // Dark Mode Toggle
        options.add(new OptionItem.Toggle("Dark mode", false));

        // Music Volume Slider
        final OptionItem.Slider musicVolume = new OptionItem.Slider("Music volume", (float) AudioManager.getMusicVolume());
        musicVolume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.setMusicVolume(musicVolume.getValue());
                AudioManager.play(AudioManager.PIECE_MOVE);
            }
        });
        options.add(musicVolume);

        // Sound Effects Volume Slider
        final OptionItem.Slider soundVolume = new OptionItem.Slider("Sounds volume", (float) AudioManager.getSoundVolume());
        soundVolume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.setSoundVolume(soundVolume.getValue());
                AudioManager.play(AudioManager.PIECE_MOVE);
            }
        });
        options.add(soundVolume);
    }

    public void update() {
        fullScreen.setEnabled(display.getWindow().isFullScreen());
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
        String title = "OPTIONS";
        Font titleFont = new Font("Dosis", Font.BOLD, 36);
        g2d.setFont(titleFont);
        g2d.setColor(ColorScheme.BASE_02.color);
        g2d.drawString(title, 20, 76);

        options.draw(g);
    }

    public void load() { options.scrollToStart(); }
    public void unload() { options.scrollToStart(); }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                display.transitionScreen(ScreenType.MAIN_MENU, Direction.LEFT);
                break;
            case KeyEvent.VK_DOWN:
                options.moveDown();
                break;
            case KeyEvent.VK_UP:
                options.moveUp();
                break;
            case KeyEvent.VK_LEFT:
                options.keyLeft();
                break;
            case KeyEvent.VK_RIGHT:
                options.keyRight();
                break;
            case KeyEvent.VK_ENTER:
                options.select();
                display.update();
                break;
        }
    }
}
