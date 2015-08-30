package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.TRIPLE_BAR;

/**
 * 1 x 3 piece.
 */
public class TripleBar extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
    add(new Field(FIELD_SIZE, 0, GRID_SIZE));
    add(new Field(FIELD_SIZE, 0, 2 * GRID_SIZE));
  }

  public TripleBar() {
    super(TRIPLE_BAR);
  }
}
