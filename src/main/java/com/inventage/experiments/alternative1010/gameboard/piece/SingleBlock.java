package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;

/**
 * The 1 x 1 block.
 */
public class SingleBlock extends DraggablePiece {

  private static List<Field> fields = new ArrayList<>();

  static {
    fields.add(new Field(FIELD_SIZE, 0, 0));
  }

  public SingleBlock() {
    super(Color.RED, fields);
  }
}
