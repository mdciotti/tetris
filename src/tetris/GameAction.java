package tetris;

/**
 * Created by max on 2016-04-01.
 */
public enum GameAction {
    NONE { public void perform() {} },
    QUIT {
        public void perform() {
            Tetris.quit();
        }
    };

    private static Tetris display;

    public static void setDisplay(Tetris display) {
        GameAction.display = display;
    }

    public abstract void perform();
}
