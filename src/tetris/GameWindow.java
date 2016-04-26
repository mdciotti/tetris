package tetris;

//import com.apple.eawt.AppEvent;
//import com.apple.eawt.FullScreenListener;
//import com.apple.eawt.FullScreenUtilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by max on 2016-04-16.
 */
public class GameWindow extends JFrame /*implements FullScreenListener */{

    private Tetris display;

    private boolean fullScreen = false;

    public GameWindow(String title, Tetris display) {
        this.display = display;

        add(display);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        pack();
        setMinimumSize(new Dimension(getWidth(), getHeight()));
        // Center window in screen
        setLocationRelativeTo(null);
        setVisible(true);
        addKeyListener(display);

//        FullScreenUtilities.setWindowCanFullScreen(this, true);
//        FullScreenUtilities.addFullScreenListenerTo(this, this);
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

//    @Override
//    public void windowEnteringFullScreen(AppEvent.FullScreenEvent fse) {
//        fullScreen = true;
//        display.componentResized(null);
//    }
//
//    @Override
//    public void windowEnteredFullScreen(AppEvent.FullScreenEvent fse) {}
//
//    @Override
//    public void windowExitingFullScreen(AppEvent.FullScreenEvent fse) {
//        fullScreen = false;
//        display.componentResized(null);
//    }
//
//    @Override
//    public void windowExitedFullScreen(AppEvent.FullScreenEvent fse) {}
}
