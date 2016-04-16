package tetris.screens;

import tetris.*;
import java.awt.*;

/**
 * Created by max on 2016-04-01.
 */
public class TransitionScreen extends Screen implements Runnable {

    private Tetris display;
    private Screen current, next;

    private Easing easing = Easing.EXPONENTIAL;

    // Target 60fps
    private long delay = 17;

    // The starting timestamp of the animation, used for calculating delta time
    // (in nanoseconds)
    private long t0;

    private double progression = 0.0;

    // The duration of the animation, in seconds
    private double duration = 0.5;

    // The direction to animate the screen transition
    private Direction direction = Direction.LEFT;

    public TransitionScreen(Tetris display) {
        registerType(ScreenType.TRANSITION);
        this.display = display;
        load();
    }

    public void setEasing(Easing e) {
        easing = e;
    }

    public void setCurrent(Screen s) {
        current = s;
    }

    public void setNext(Screen s) {
        next = s;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double seconds) {
        duration = seconds;
    }

    public void setDirection(Direction dir) {
        direction = dir;
    }

    public void draw(Graphics g) {
        int w = display.getWidth();
        int h = display.getHeight();
        int x = (int) Math.round(progression * w);
        int y = (int) Math.round(progression * h);

        switch (direction) {
            case LEFT:
                g.translate(-x, 0);
                if (current != null) current.draw(g);
                g.translate(w, 0);
                if (next != null) next.draw(g);
                break;
            case RIGHT:
                g.translate(-w + x, 0);
                if (next != null) next.draw(g);
                g.translate(w, 0);
                if (current != null) current.draw(g);
                break;
            case UP:
                g.translate(0, -h + y);
                if (next != null) next.draw(g);
                g.translate(0, h);
                if (current != null) current.draw(g);
                break;
            case DOWN:
                g.translate(0, -y);
                if (current != null) current.draw(g);
                g.translate(0, h);
                if (next != null) next.draw(g);
                break;
        }
    }

    public void load() {
        progression = 0.0;
        t0 = System.nanoTime();
    }

    public void update() {
        long now = System.nanoTime();
        double dt = (now - t0) / 1E9d;
        progression = easing.easeOut(dt / duration);
        display.render();
        display.repaint();
        if (dt >= duration) {
            display.setScreen(next.getType());
            current.unload();
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        try {
            while (true) {
                update();
                Thread.currentThread().sleep(delay);
            }
        } catch (InterruptedException e) {
            //System.out.println("Interrupt");
        }
    }
}
