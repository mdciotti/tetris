package tetris;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 * Created by max on 2016-03-26.
 */
public class AudioManager {

    public static Music THEME_A;

    public static Sound PIECE_MOVE, LINE_CLEAR_1, LINE_CLEAR_2, LINE_CLEAR_3,
            LINE_CLEAR_4, HOLD, NO_HOLD, HARD_DROP;

    public static void init() {
        TinySound.init();

        THEME_A = TinySound.loadMusic("resources/music/theme_a.wav");
        PIECE_MOVE = TinySound.loadSound("resources/sfx/tap.wav");
        LINE_CLEAR_1 = TinySound.loadSound("resources/sfx/pop.wav");
        LINE_CLEAR_2 = TinySound.loadSound("resources/sfx/pop2.wav");
        LINE_CLEAR_3 = TinySound.loadSound("resources/sfx/pop3.wav");
        LINE_CLEAR_4 = TinySound.loadSound("resources/sfx/pop4.wav");
        HOLD = TinySound.loadSound("resources/sfx/scuff.wav");
        NO_HOLD = TinySound.loadSound("resources/sfx/dink.wav");
        HARD_DROP = TinySound.loadSound("resources/sfx/thump.wav");
    }

    public static void stopAll() {
        TinySound.shutdown();
    }
}
