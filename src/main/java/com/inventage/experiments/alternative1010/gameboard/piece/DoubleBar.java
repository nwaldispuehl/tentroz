package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.DOUBLE_BAR;

/**
 * 1 x 2 piece.
 */
public class DoubleBar extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
    add(new Field(FIELD_SIZE, 0, GRID_SIZE));
  }

  public DoubleBar() {
    super(DOUBLE_BAR);
  }
}
