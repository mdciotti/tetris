package tetris;

import java.awt.*;

/**
 * Created by max on 2016-03-27.
 */
public class TetriminoField extends InfoField {

    private Matrix matrix;

    private Tetrimino piece;

    public TetriminoField(String name) {
        setTitle(name);
        this.height = 100;

        matrix = new Matrix(4, 4);
    }

    public void setTetrimino(Tetrimino tetrimino) {
        this.piece = tetrimino;
        if (this.piece != null) {
            for (int i = 0; i < this.piece.cells.length; i++) {
                this.piece.cells[i].setMatrix(matrix);
            }
        }
    }

    public void draw(Graphics2D g2d, int x, int y) {
        super.draw(g2d, x, y);

        matrix.setPosition(x, y);

        // Draw Tetrimino
        if (piece != null) {
            piece.draw(g2d);
        }
    }
}
