package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.DOUBLE_ANGLE;

/**
 * Angle with single length legs.
 */
public class DoubleAngle extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
    add(new Field(FIELD_SIZE, 0, GRID_SIZE));
    add(new Field(FIELD_SIZE, GRID_SIZE, 0));
  }

  public DoubleAngle() {
    super(DOUBLE_ANGLE);
  }
}
