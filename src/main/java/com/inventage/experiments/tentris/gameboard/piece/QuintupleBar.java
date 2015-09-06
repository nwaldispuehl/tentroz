package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.QUINTUPLE_BAR;

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
