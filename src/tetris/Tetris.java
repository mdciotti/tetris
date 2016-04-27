package tetris;

import tetris.gui.*;
import tetris.gui.TextField;
import tetris.screens.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.EnumMap;

import javax.swing.JPanel;


/**
 * Create and control the game Tetris.
 */
public class Tetris extends JPanel implements KeyListener, ComponentListener {

    private Screen currentScreen;
    private EnumMap<ScreenType, Screen> screens;
    private BufferedImage screenBuffer;
    private GameWindow window;

    public final int WIDTH = 500;
    public final int HEIGHT = 420;

    /**
     * Sets up the parts for the Tetris game, display and user control.
     */
    public Tetris() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        GameAction.setDisplay(this);

        // Create window
        window = new GameWindow("Tetris", this);

        setBackground(ColorScheme.BASE_02.color);

        // Create screen buffer image
        screenBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        // Create screens
        screens = new EnumMap<>(ScreenType.class);
        screens.put(ScreenType.MAIN_MENU, new MenuScreen(this));
        screens.put(ScreenType.GAME, new GameScreen(this));
        screens.put(ScreenType.TOP_SCORES, new TopScoreScreen(this));
        screens.put(ScreenType.OPTIONS, new OptionScreen(this));
        screens.put(ScreenType.ABOUT, new AboutScreen(this));
        setScreen(ScreenType.MAIN_MENU);

        addComponentListener(this);
    }

    public GameWindow getWindow() {
        return window;
    }

    public BufferedImage getScreenBuffer() {
        return screenBuffer;
    }

    public void transitionScreen(ScreenType s, Direction dir) {
        Screen screen = screens.get(s);
        if (screen != null) {
            TransitionScreen transition = new TransitionScreen(this);
            transition.setDirection(dir);
            transition.setCurrent(currentScreen);
            transition.setNext(screen);
            // transition.setDuration(0.5);
            transition.load();
            currentScreen = transition;

            // Run transition in separate thread
            Thread transitionThread = new Thread(transition);
            transitionThread.start();
        }
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
        render();
        repaint();
    }

    public void render() {
        Graphics sg = screenBuffer.getGraphics();

        if (currentScreen != null) {
            currentScreen.draw(sg);
        }
    }

    /**
     * Paint the current state of the game.
     * 
     * @param g the AWT Graphics context with which to draw
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(screenBuffer, 0, 0, null);
    }

    public void componentHidden(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentResized(ComponentEvent e) {
        screenBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        update();
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
     * Make the display full screen.
     */
    public void toggleFullScreen() {
//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//
//        if (gd.isFullScreenSupported()) {
//            if (isFullScreen) {
//                fullScreenWindow.remove(this);
//                window.add(this);
//                gd.setFullScreenWindow(null);
//            } else {
//                window.remove(this);
//                fullScreenWindow.add(this);
//                gd.setFullScreenWindow(fullScreenWindow);
//            }
//        } else {
//            System.err.println("Full screen is not supported on this device.");
//        }

        if (System.getProperty("os.name").contains("Mac OS X")) {
            // Attempt to toggle OS X full-screen window mode
            com.apple.eawt.Application.getApplication().requestToggleFullScreen(window);
        } else {
            System.err.println("Full screen not support on your platform" +
                " (" + System.getProperty("os.name") + ")");
        }
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
            OptionList.setBodyFont(new Font("Dosis", Font.PLAIN, 30));
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
