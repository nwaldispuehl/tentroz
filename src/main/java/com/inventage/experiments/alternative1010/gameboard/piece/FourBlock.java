package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.inventage.experiments.alternative1010.gameboard.GameGrid.FIELD_SIZE;

/**
 * Created by nw on 26.08.15.
 */
public class FourBlock extends DraggablePiece {

  private static List<Field> fields = new ArrayList<>();

  static {
    fields.add(new Field(FIELD_SIZE, 0, 0));
    fields.add(new Field(FIELD_SIZE, 0, GRID_SIZE));
    fields.add(new Field(FIELD_SIZE, GRID_SIZE, 0));
    fields.add(new Field(FIELD_SIZE, GRID_SIZE, GRID_SIZE));
  }

  public FourBlock() {
    super(Color.GREEN, fields);
  }
}
