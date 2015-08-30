package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.DOUBLE_CHUNK;

/**
 * 2 x 2 piece.
 */
public class DoubleChunk extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
    add(new Field(FIELD_SIZE, 0, GRID_SIZE));
    add(new Field(FIELD_SIZE, GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, GRID_SIZE, GRID_SIZE));
  }

  public DoubleChunk() {
    super(DOUBLE_CHUNK);
  }
}
