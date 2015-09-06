package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.TRIPLE_ANGLE;

/**
 * Angled piece with legs of length 2.
 */
public class TripleAngle extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));

    add(new Field(getFieldSize(), 0, getGridSize()));
    add(new Field(getFieldSize(), 0, 2 * getGridSize()));

    add(new Field(getFieldSize(), getGridSize(), 0));
    add(new Field(getFieldSize(), 2 * getGridSize(), 0));
  }

  public TripleAngle(Double fieldSize) {
    super(fieldSize, TRIPLE_ANGLE);
  }
}
