package ch.retorte.tentroz.gameboard.piece;

import ch.retorte.tentroz.gameboard.Field;

/**
 * 1 x 3 piece.
 */
public class TripleBar extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
    add(new Field(getFieldSize(), getGridSize(), 0));
    add(new Field(getFieldSize(), 2 * getGridSize(), 0));
  }

  public TripleBar(Double fieldSize) {
    super(fieldSize, ColorPalette.TRIPLE_BAR);
  }
}
