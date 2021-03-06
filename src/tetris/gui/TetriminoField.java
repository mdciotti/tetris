package tetris.gui;

import tetris.*;
import tetris.tetrimino.*;
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
        if (this.piece != null)
            this.piece.setMatrix(this.matrix);
    }

    public void draw(Graphics2D g2d, int x, int y) {
        super.draw(g2d, x, y);

        matrix.setPosition(x, y);

        // Draw Tetrimino
        if (piece != null) {
            // Calculate offset to center piece based on its dimensions
            Dimension pieceSize = piece.getDimension();
            Dimension pieceOffset = piece.getCentralOffset();
            int offsetX = pieceOffset.width + (this.width - pieceSize.width) / 2;
            int offsetY = pieceOffset.height + (this.height - pieceSize.height) / 2;
            // Fake centering by translating the underlying matrix
            matrix.setPosition(x + offsetX, y + offsetY);
            piece.draw(g2d);
        }
    }
}
