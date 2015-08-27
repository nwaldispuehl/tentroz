package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;

import java.util.ArrayList;
import java.util.List;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.DOUBLE_BAR;

/**
 * 1 x 2 piece.
 */
public class DoubleBar extends DraggablePiece {

  private static List<Field> fields = new ArrayList<>();

  static {
    fields.add(new Field(FIELD_SIZE, 0, 0));
    fields.add(new Field(FIELD_SIZE, 0, GRID_SIZE));
  }

  public DoubleBar() {
    super(DOUBLE_BAR, fields);
  }
}
