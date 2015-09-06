package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.QUINTUPLE_BAR;

/**
 * The 1 x 5 piece.
 */
public class QuintupleBar extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
    add(new Field(FIELD_SIZE, GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, 2 * GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, 3 * GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, 4 * GRID_SIZE, 0));
  }

  public QuintupleBar() {
    super(QUINTUPLE_BAR);
  }
}
