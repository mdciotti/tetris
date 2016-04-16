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

    private static double musicVolume = 0.8;
    private static double soundVolume = 1.0;

    private static boolean muted = false;

    public static double getMusicVolume() {
        return musicVolume;
    }

    public static double getSoundVolume() {
        return soundVolume;
    }

    public static void setMusicVolume(double v) {
        musicVolume = Math.max(0.0, Math.min(v, 1.0));
    }

    public static void setSoundVolume(double v) {
        soundVolume = Math.max(0.0, Math.min(v, 1.0));
    }

    public static void toggleMute() {
        TinySound.setGlobalVolume(muted ? 0.0 : 1.0);
    }

    public static void play(Sound s) {
        s.play(soundVolume);
    }

    public static void play(Music m, boolean loop) {
        // Reduce volume (why is it so loud?)
        m.play(loop, 0.5 * musicVolume);
    }

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
