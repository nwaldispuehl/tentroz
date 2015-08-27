package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;

import java.util.ArrayList;
import java.util.List;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.DOUBLE_ANGLE;

/**
 * Angle with single length legs.
 */
public class DoubleAngle extends DraggablePiece {

  private static List<Field> fields = new ArrayList<>();

  static {
    fields.add(new Field(FIELD_SIZE, 0, 0));
    fields.add(new Field(FIELD_SIZE, 0, GRID_SIZE));
    fields.add(new Field(FIELD_SIZE, GRID_SIZE, 0));
  }

  public DoubleAngle() {
    super(DOUBLE_ANGLE, fields);
  }
}
