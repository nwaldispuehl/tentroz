package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.TRIPLE_CHUNK;

/**
 * 3 x 3 piece.
 */
public class TripleChunk extends DraggablePiece {

  {
    add(new Field(FIELD_SIZE, 0, 0));
    add(new Field(FIELD_SIZE, 0, GRID_SIZE));
    add(new Field(FIELD_SIZE, 0, 2 * GRID_SIZE));

    add(new Field(FIELD_SIZE, GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, GRID_SIZE, GRID_SIZE));
    add(new Field(FIELD_SIZE, GRID_SIZE, 2 * GRID_SIZE));

    add(new Field(FIELD_SIZE, 2 * GRID_SIZE, 0));
    add(new Field(FIELD_SIZE, 2 * GRID_SIZE, GRID_SIZE));
    add(new Field(FIELD_SIZE, 2 * GRID_SIZE, 2 * GRID_SIZE));
  }

  public TripleChunk() {
    super(TRIPLE_CHUNK);
  }
}
