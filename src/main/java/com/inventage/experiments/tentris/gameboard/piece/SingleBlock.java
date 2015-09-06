package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;

import static com.inventage.experiments.tentris.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.SINGLE;

/**
 * The 1 x 1 block.
 */
public class SingleBlock extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
  }

  public SingleBlock() {
    super(SINGLE);
  }
}
