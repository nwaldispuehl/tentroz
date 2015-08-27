package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;

import java.util.ArrayList;
import java.util.List;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.QUADRUPLE_BAR;

/**
 * The 1 x 4 piece.
 */
public class QuadrupleBar extends DraggablePiece {

  private static List<Field> fields = new ArrayList<>();

  static {
    fields.add(new Field(FIELD_SIZE, 0, 0));
    fields.add(new Field(FIELD_SIZE, GRID_SIZE, 0));
    fields.add(new Field(FIELD_SIZE, 2 * GRID_SIZE, 0));
    fields.add(new Field(FIELD_SIZE, 3 * GRID_SIZE, 0));
  }

  public QuadrupleBar() {
    super(QUADRUPLE_BAR, fields);
  }
}
