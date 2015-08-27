package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;

/**
 * The 1 x 5 block.
 */
public class FiveLongBlock extends DraggablePiece {

  private static List<Field> fields = new ArrayList<>();

  static {
    fields.add(new Field(FIELD_SIZE, 0, 0));
    fields.add(new Field(FIELD_SIZE, 1 * GRID_SIZE, 0));
    fields.add(new Field(FIELD_SIZE, 2 * GRID_SIZE, 0));
    fields.add(new Field(FIELD_SIZE, 3 * GRID_SIZE, 0));
    fields.add(new Field(FIELD_SIZE, 4 * GRID_SIZE, 0));
  }

  public FiveLongBlock() {
    super(Color.BLANCHEDALMOND, fields);
  }
}
