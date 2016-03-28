package ch.retorte.tentroz.gameboard.piece;

import ch.retorte.tentroz.gameboard.Field;

import static ch.retorte.tentroz.gameboard.piece.ColorPalette.QUADRUPLE_BAR;

/**
 * The 1 x 4 piece.
 */
public class QuadrupleBar extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
    add(new Field(getFieldSize(), getGridSize(), 0));
    add(new Field(getFieldSize(), 2 * getGridSize(), 0));
    add(new Field(getFieldSize(), 3 * getGridSize(), 0));
  }

  public QuadrupleBar(Double fieldSize) {
    super(fieldSize, QUADRUPLE_BAR);
  }
}
