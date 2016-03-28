package ch.retorte.tentroz.gameboard.piece;

import ch.retorte.tentroz.gameboard.Field;

import static ch.retorte.tentroz.gameboard.piece.ColorPalette.DOUBLE_BAR;

/**
 * 1 x 2 piece.
 */
public class DoubleBar extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
    add(new Field(getFieldSize(), 0, getGridSize()));
  }

  public DoubleBar(Double fieldSize) {
    super(fieldSize, DOUBLE_BAR);
  }
}
