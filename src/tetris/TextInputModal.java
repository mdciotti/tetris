package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by max on 2016-04-09.
 */
public class TextInputModal extends Modal implements ActionListener {

    private String textInput;
    private int caretIndex;

    private static final Pattern alphanumeric = Pattern.compile("[a-zA-Z0-9 ]");

    public TextInputModal(String title) {
        super(title);
        textInput = "";
        caretIndex = 0;

        setHeight(200);
    }

    public String getTextInput() { return textInput; }

    /**
     * The enter key is pressed or an accept button is selected.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                // TODO: delete at caret
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);

        //System.out.print(e.getKeyChar());
        char c = e.getKeyChar();
        Matcher m = alphanumeric.matcher(Character.toString(c));
        if (!e.isActionKey() && m.matches()) {
            // TODO: insert at caret index
            textInput += c;
            // TODO: limit to max input length
        }
    }

    public void draw(Graphics2D g2d, int w, int h) {
        super.draw(g2d, w, h);

        int y = (h - getHeight()) / 2;

        int textField_x = (w - 200) / 2;
        g2d.setColor(ColorScheme.BASE_06.color);
        g2d.fillRect(textField_x, y + getHeight() - 60, 200, 40);

        g2d.setColor(ColorScheme.BASE_00.color);
        g2d.drawString(textInput, textField_x + 20, y + getHeight() - 60 + 30 - 5);

        // TODO: draw caret at x = stringWidth(textBeforeCaret);
    }
}
