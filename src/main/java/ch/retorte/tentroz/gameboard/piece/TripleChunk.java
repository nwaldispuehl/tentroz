package ch.retorte.tentroz.gameboard.piece;

import ch.retorte.tentroz.gameboard.Field;

import static ch.retorte.tentroz.gameboard.piece.ColorPalette.TRIPLE_CHUNK;

/**
 * 3 x 3 piece.
 */
public class TripleChunk extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
    add(new Field(getFieldSize(), 0, getGridSize()));
    add(new Field(getFieldSize(), 0, 2 * getGridSize()));

    add(new Field(getFieldSize(), getGridSize(), 0));
    add(new Field(getFieldSize(), getGridSize(), getGridSize()));
    add(new Field(getFieldSize(), getGridSize(), 2 * getGridSize()));

    add(new Field(getFieldSize(), 2 * getGridSize(), 0));
    add(new Field(getFieldSize(), 2 * getGridSize(), getGridSize()));
    add(new Field(getFieldSize(), 2 * getGridSize(), 2 * getGridSize()));
  }

  public TripleChunk(Double fieldSize) {
    super(fieldSize, TRIPLE_CHUNK);
  }
}
