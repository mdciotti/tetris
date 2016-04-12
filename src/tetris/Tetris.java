package tetris;

import tetris.gui.*;
import tetris.gui.TextField;
import tetris.screens.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Create and control the game Tetris.
 */
public class Tetris extends JPanel implements KeyListener {

    private Screen currentScreen;
    private EnumMap<ScreenType, Screen> screens;

    /**
     * Sets up the parts for the Tetris game, display and user control.
     */
    public Tetris() {

        // Create screens
        screens = new EnumMap<>(ScreenType.class);
        screens.put(ScreenType.MAIN_MENU, new MenuScreen(this));
        screens.put(ScreenType.GAME, new GameScreen(this));
        screens.put(ScreenType.TOP_SCORES, new TopScoreScreen(this));
        setScreen(ScreenType.MAIN_MENU);

        GameAction.setDisplay(this);

        // Create window
        JFrame f = new JFrame("Tetris");
        f.add(this);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 420));
        setMinimumSize(new Dimension(500, 420));
        f.setResizable(false);
        f.pack();
        // Center window in screen
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.addKeyListener(this);
        setBackground(ColorScheme.BASE_02.color);
    }

    /**
     * Sets the current screen to the one of the specified type.
     *
     * @param s the screen type to switch to
     */
    public void setScreen(ScreenType s) {
        Screen screen = screens.get(s);

        if (screen != null) {
            if (currentScreen != null) currentScreen.unload();
            currentScreen = screen;
            currentScreen.load();
            update();
        }
    }

    /**
     * Updates the current screen.
     */
    public void update() {
        currentScreen.update();
        repaint();
    }

    /**
     * Paint the current state of the game.
     * 
     * @param g the AWT Graphics context with which to draw
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentScreen != null) {
            currentScreen.draw(g);
        }
    }

    /**
     * Delegates key presses to the current screen.
     *
     * @param e the KeyEvent containing info about the key pressed
     */
    public void keyPressed(KeyEvent e) {
        // Send to current screen
        currentScreen.keyPressed(e);
    }

    // We don't use these but must implement them to satisfy the interface
    public void keyReleased(KeyEvent e) {
        currentScreen.keyReleased(e);
    }
    public void keyTyped(KeyEvent e) {
        currentScreen.keyTyped(e);
    }

    /**
     * Quits the game immediately.
     */
    public static void quit() {
        AudioManager.stopAll();
        System.exit(0);
    }

    /**
     * Loads all application resources.
     */
    private static void loadResources() {
        AudioManager.init();

        try {
            ClassLoader cl = Tetris.class.getClassLoader();
            InputStream dosisRegular = cl.getResourceAsStream("resources/dosis/Dosis-Regular.otf");
            InputStream dosisBold = cl.getResourceAsStream("resources/dosis/Dosis-Bold.otf");
            System.out.println("Loaded fonts");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, dosisRegular));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, dosisBold));
            Modal.setTitleFont(new Font("Dosis", Font.BOLD, 32));
            Modal.setBodyFont(new Font("Dosis", Font.PLAIN, 20));
            MenuScreen.setTitleFont(new Font("Dosis", Font.BOLD, 48));
            BasicMenu.setBodyFont(new Font("Dosis", Font.PLAIN, 30));
            ScoreList.setBodyFont(new Font("Dosis", Font.PLAIN, 30));
            InfoField.setTitleFont(new Font("Dosis", Font.BOLD, 20));
            TextField.setValueFont(new Font("Dosis", Font.PLAIN, 32));
            ScoreManager.init();
        } catch (Exception e) {
            System.err.println("Failed to load fonts.");
            e.printStackTrace();
        }
    }

    /**
     * Entry point to the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        loadResources();
        new Tetris();
    }
}
