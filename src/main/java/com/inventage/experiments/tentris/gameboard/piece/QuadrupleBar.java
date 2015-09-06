package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.QUADRUPLE_BAR;

/**
 * The 1 x 4 piece.
 */
public class QuadrupleBar extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
    add(new Field(FIELD_SIZE, GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, 2 * GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, 3 * GRID_SIZE, 0));
  }

  public QuadrupleBar() {
    super(QUADRUPLE_BAR);
  }
}
