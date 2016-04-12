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

    private StringBuilder textInput;
    private int caretIndex;
    private static final int MAX_INPUT_CHARS = 16;

    private static final Pattern alphanumeric = Pattern.compile("[a-zA-Z0-9 ]");

    public TextInputModal(String title) {
        super(title);
        setInputText("");

        setHeight(200);
    }

    public String getTextInput() { return textInput.toString(); }

    public void setInputText(String text) {
        int end = Math.min(text.length(), MAX_INPUT_CHARS);
        textInput = new StringBuilder(MAX_INPUT_CHARS);
        textInput.append(text.substring(0, end));
        // Move caret to end
        caretIndex = end;
    }

    /**
     * The enter key is pressed or an accept button is selected.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (caretIndex > 0) caretIndex--;
                break;
            case KeyEvent.VK_RIGHT:
                if (caretIndex < textInput.length()) caretIndex++;
                break;
            case KeyEvent.VK_UP:
                caretIndex = 0;
                break;
            case KeyEvent.VK_DOWN:
                caretIndex = textInput.length();
                break;
            case KeyEvent.VK_BACK_SPACE:
                if (caretIndex > 0) {
                    textInput.deleteCharAt(caretIndex - 1);
                    caretIndex -= 1;
                }
                break;
            case KeyEvent.VK_DELETE:
                if (caretIndex < textInput.length())
                    textInput.deleteCharAt(caretIndex);
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);

        char c = e.getKeyChar();
        Matcher m = alphanumeric.matcher(Character.toString(c));
        if (!e.isActionKey() && m.matches() && textInput.length() < MAX_INPUT_CHARS) {
            textInput.insert(caretIndex, c);
            caretIndex += 1;
        }
    }

    public void draw(Graphics2D g2d, int w, int h) {
        super.draw(g2d, w, h);

        int y = (h - getHeight()) / 2;
        int textField_x = (w - 200) / 2;
        int textField_y = y + getHeight() - 60;

        // Draw input text field
        g2d.setColor(ColorScheme.BASE_06.color);
        g2d.fillRect(textField_x, textField_y, 200, 40);

        // Draw input text
        g2d.setColor(ColorScheme.BASE_00.color);
        g2d.drawString(getTextInput(), textField_x + 10, textField_y + 30 - 5);

        // Draw caret
        g2d.setColor(ColorScheme.BASE_0D.color);
        FontMetrics fm = g2d.getFontMetrics(bodyFont);
        String textBeforeCaret = textInput.substring(0, caretIndex);
        int caret_xOffset = fm.stringWidth(textBeforeCaret);
        g2d.fillRect(textField_x + caret_xOffset + 10, textField_y + 5, 2, 30);
    }
}
