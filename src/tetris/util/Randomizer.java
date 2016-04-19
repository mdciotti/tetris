package tetris.util;

/**
 * Created by max on 2016-04-17.
 */
public class Randomizer {

    private int[] queue;
    private int queueSize;
    private int last;

    public Randomizer(int size) {
        queueSize = size;
        queue = new int[queueSize];
        last = 0;
    }

    /**
     * Create a randomized array of integers 0 <= n < queueSize with no repeats.
     */
    private void generateQueue() {
        for (int i = 0; i < queueSize; i++ ) {
            int j = (int) Math.floor(Math.random() * (i + 1));
            if (j != i) queue[i] = queue[j];
            queue[j] = i;
        }

        last = queueSize - 1;
    }

    public int next() {
        last -= 1;

        // Create a new queue when empty
        if (last < 0) generateQueue();

        // Return the last item in the queue
        return queue[last];
    }
}
