package tetris;

import java.io.Serializable;

/**
 * Define a player score. A single topscore screen will have a few of these.
 */
public class PlayerScore implements Comparable<PlayerScore>, Serializable {

    private String player;

    private int score;

    public PlayerScore(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    /**
     * Sort high-to-low.
     */
    public int compareTo(PlayerScore ps) {
        return ps.score - score;
    }
}
