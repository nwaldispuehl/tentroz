package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.DOUBLE_ANGLE;

/**
 * Angle with single length legs.
 */
public class DoubleAngle extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
    add(new Field(getFieldSize(), 0, getGridSize()));
    add(new Field(getFieldSize(), getGridSize(), 0));
  }

  public DoubleAngle(Double fieldSize) {
    super(fieldSize, DOUBLE_ANGLE);
  }
}
