package ch.retorte.tentroz.gameboard.piece;

import ch.retorte.tentroz.gameboard.Field;

import static ch.retorte.tentroz.gameboard.piece.ColorPalette.DOUBLE_CHUNK;

/**
 * 2 x 2 piece.
 */
public class DoubleChunk extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
    add(new Field(getFieldSize(), 0, getGridSize()));
    add(new Field(getFieldSize(), getGridSize(), 0));
    add(new Field(getFieldSize(), getGridSize(), getGridSize()));
  }

  public DoubleChunk(Double fieldSize) {
    super(fieldSize, DOUBLE_CHUNK);
  }
}
