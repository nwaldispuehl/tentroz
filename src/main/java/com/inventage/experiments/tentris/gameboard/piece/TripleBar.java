package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.TRIPLE_BAR;

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
    super(fieldSize, TRIPLE_BAR);
  }
}
