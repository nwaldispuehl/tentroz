package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.TRIPLE_ANGLE;

/**
 * Angled piece with legs of length 2.
 */
public class TripleAngle extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));

    add(new Field(FIELD_SIZE, 0, GRID_SIZE));
    add(new Field(FIELD_SIZE, 0, 2 * GRID_SIZE));

    add(new Field(FIELD_SIZE, GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, 2 * GRID_SIZE, 0));
  }

  public TripleAngle() {
    super(TRIPLE_ANGLE);
  }
}
