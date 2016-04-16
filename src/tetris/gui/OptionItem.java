package tetris.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public abstract class OptionItem {

    // The displayed name of this option item
    private String name;

    // A description of this option item (not used)
    private String description;

    // A reference to the parent option list, set by the option list when adding
    // option items to it
    OptionList parent;

    // A registry of the registered action listeners for change events
    private HashSet<ActionListener> actionListeners = new HashSet<>();

    // A counter for unique event IDs
    private int eventID = 0;

    public void select() {}
    public void keyLeft() {}
    public void keyRight() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String desc) { description = desc; }

    protected void fireEvent() {
        parent.getDisplay().update();

        ActionEvent e = new ActionEvent(this, eventID, null);
        for (ActionListener a : actionListeners)
            a.actionPerformed(e);
        eventID += 1;
    }

    public void addActionListener(ActionListener a) {
        actionListeners.add(a);
    }

    public void removeActionListener(ActionListener a) {
        actionListeners.remove(a);
    }

    public void draw(Graphics2D g2d, int i) {

        int screenWidth = parent.getDisplay().getWidth();
        int startIndex = (parent.getCurrentPage() - 1) * parent.getNumOptionsPerPage();
        int y = 100 + (i - startIndex) * 40;
        int pad = 20;
        int w = screenWidth - pad * 2;

        if (i == parent.getSelectedIndex()) {
            // Draw selection indication
            g2d.setColor(ColorScheme.BASE_0D.color);
            g2d.fillRect(pad, y, w, 40);
            g2d.setColor(ColorScheme.BASE_07.color);
        } else if (i % 2 == 1) {
            // Draw banded rows
            Color shade = ColorScheme.BASE_06.color;
            g2d.setColor(new Color(shade.getRed(), shade.getGreen(), shade.getBlue(), 96));
            g2d.fillRect(pad, y, w, 40);
            g2d.setColor(ColorScheme.BASE_03.color);
        } else {
            g2d.setColor(ColorScheme.BASE_03.color);
        }

        // Draw option name
        g2d.drawString(name, pad * 2, y + 30);
    }

    public static class Toggle extends OptionItem {
        private boolean enabled;

        public Toggle(String name, boolean initial) {
            setName(name);
            enabled = initial;
        }

        // Toggle
        public void select() {
            enabled = !enabled;
            this.fireEvent();
        }

        public void draw(Graphics2D g2d, int i) {
            super.draw(g2d, i);

            int pad = 20;
            int endX = parent.getDisplay().getWidth() - pad * 2;
            int startIndex = (parent.getCurrentPage() - 1) * parent.getNumOptionsPerPage();
            int y = 100 + (i - startIndex) * 40;

            if (enabled) g2d.fillOval(endX - 10, y + 10, 20, 20);
            else g2d.drawOval(endX - 10, y + 10, 20, 20);
        }
    }

    public static class Slider extends OptionItem {
        private float value;
        private float step;

        public Slider(String name, float initial) {
            setName(name);
            value = initial;
            step = 0.1f;
        }

        public float getValue() {
            return value;
        }

        public void keyLeft() {
            // Decrement
            if (value >= step) value -= step;
            else value = 0.0f;
            this.fireEvent();
        }

        public void keyRight() {
            // Increment
            if (value <= 1.0f - step) value += step;
            else value = 1.0f;
            this.fireEvent();
        }

        public void draw(Graphics2D g2d, int i) {
            super.draw(g2d, i);

            int pad = 20;
            int endX = parent.getDisplay().getWidth() - pad * 2;
            int startIndex = (parent.getCurrentPage() - 1) * parent.getNumOptionsPerPage();
            int y = 100 + (i - startIndex) * 40;

            int slider_w = 100;
            g2d.fillRect(endX - slider_w, y + 19, slider_w, 2);

            int val_x = Math.round(value * slider_w);
            g2d.fillOval(endX - slider_w + val_x - 10, y + 10, 20, 20);
        }
    }
}
