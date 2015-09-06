package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.SINGLE;

/**
 * The 1 x 1 block.
 */
public class SingleBlock extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
  }

  public SingleBlock(Double fieldSize) {
    super(fieldSize, SINGLE);
  }
}
