package tetris.screens;

/**
 * Created by max on 2016-04-14.
 */
public enum Easing {
    LINEAR {
        @Override
        public double easeIn(double t) { return t; }
        @Override
        public double easeOut(double t) { return t; }
    },
    CUBIC {
        @Override
        public double easeIn(double t) {
            return Math.pow(t, 3.0);
        }
        @Override
        public double easeOut(double t) {
            return 1.0 - Math.pow(1.0 - t, 3.0);
        }
    },
    EXPONENTIAL {
        @Override
        public double easeIn(double t) {
            return Math.pow(2, 10.0 * (t - 1.0));
        }
        public double easeOut(double t) {
            return 1.0 - Math.pow(2, -10.0 * t);
        }
    };

    public abstract double easeIn(double t);
    public abstract double easeOut(double t);
}
