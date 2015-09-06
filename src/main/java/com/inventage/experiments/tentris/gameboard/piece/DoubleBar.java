package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.DOUBLE_BAR;

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
