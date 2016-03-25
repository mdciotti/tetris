package tetris;

import java.awt.Color;

/**
 * Define the color scheme for the game.
 */
public enum ColorScheme {
    // Base16 Default
    // https://chriskempson.github.io/base16/

    BASE_00 (0x181818),
    BASE_01 (0x282828),
    BASE_02 (0x383838),
    BASE_03 (0x585858),
    BASE_04 (0xB8B8B8),
    BASE_05 (0xD8D8D8),
    BASE_06 (0xE8E8E8),
    BASE_07 (0xF8F8F8),

    BASE_08 (0xAB4642),
    BASE_09 (0xDC9656),
    BASE_0A (0xF7CA88),
    BASE_0B (0xA1B56C),
    BASE_0C (0x86C1B9),
    BASE_0D (0x7CAFC2),
    BASE_0E (0xBA8BAF),
    BASE_0F (0xA16946);

    public final Color color;

    ColorScheme(int colorCode) {
    	this.color = new Color(colorCode);
    }
}
