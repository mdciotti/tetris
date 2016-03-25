package tetris;

import java.awt.Color;

/**
 * Define the color scheme for the game.
 */
public enum ColorScheme {
    // Base16 Paraiso
    // https://chriskempson.github.io/base16/

    BASE_00 (0x2f1e2e),
    BASE_01 (0x41323f),
    BASE_02 (0x4f424c),
    BASE_03 (0x776e71),
    BASE_04 (0x8d8687),
    BASE_05 (0xa39e9b),
    BASE_06 (0xb9b6b0),
    BASE_07 (0xe7e9db),

    BASE_08 (0xef6155),
    BASE_09 (0xf99b15),
    BASE_0A (0xfec418),
    BASE_0B (0x48b685),
    BASE_0C (0x5bc4bf),
    BASE_0D (0x06b6ef),
    BASE_0E (0x815ba4),
    BASE_0F (0xe96ba8);

    public final Color color;

    ColorScheme(int colorCode) {
        this.color = new Color(colorCode);
    }
}
