package ch.retorte.tentroz.gameboard.piece;

import ch.retorte.tentroz.gameboard.Field;

import static ch.retorte.tentroz.gameboard.piece.ColorPalette.QUINTUPLE_BAR;

/**
 * The 1 x 5 piece.
 */
public class QuintupleBar extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
    add(new Field(getFieldSize(), getGridSize(), 0));
    add(new Field(getFieldSize(), 2 * getGridSize(), 0));
    add(new Field(getFieldSize(), 3 * getGridSize(), 0));
    add(new Field(getFieldSize(), 4 * getGridSize(), 0));
  }

  public QuintupleBar(Double fieldSize) {
    super(fieldSize, QUINTUPLE_BAR);
  }
}
