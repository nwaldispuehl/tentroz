package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.QUADRUPLE_BAR;

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
