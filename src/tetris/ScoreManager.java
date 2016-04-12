package tetris;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by max on 2016-04-09.
 */
public class ScoreManager {

    private static ArrayList<PlayerScore> scores = new ArrayList<>();

    private static final String FILE_NAME = "scores.dat";

    public ScoreManager() {}

    public static void add(String player, int score) {
        scores.add(new PlayerScore(player, score));
        // TODO: use binary search for fast insertion to sorted list
        //Collections.binarySearch(scores, )
        Collections.sort(scores);
        save();
    }

    public static PlayerScore get(int i) {
        return scores.get(i);
    }

    public static int getNumScores() {
        return scores.size();
    }

    public static void save() {
        FileOutputStream fileOut;
        ObjectOutputStream objOut;

        try {
            fileOut = new FileOutputStream(FILE_NAME);
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(scores);
            objOut.close();
            fileOut.close();
            System.out.println("Successfully saved scores.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        FileInputStream fileIn;
        ObjectInputStream objIn;

        try {
            fileIn = new FileInputStream(FILE_NAME);
            objIn = new ObjectInputStream(fileIn);
            scores = (ArrayList<PlayerScore>) objIn.readObject();
            objIn.close();
            fileIn.close();
            System.out.println("Successfully deserialized scores.");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
            return;
        }
    }
}
