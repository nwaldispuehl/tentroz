package com.inventage.experiments.alternative1010.gameboard;

import javafx.scene.Group;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * The game grid maintains the grid and the state of its items.
 */
public class GameGrid extends Group {

  public static final int FIELD_SIZE = 56;
  public static final int SPACE = 4;

  private Field[][] fields;
  private int columns;
  private int rows;

  public GameGrid(int columns, int rows) {
    this.columns = columns;
    this.rows = rows;
    initializeWith(columns, rows);
    registerDropListener();
  }

  private void registerDropListener() {
    setOnDragOver(event -> {
      event.acceptTransferModes(TransferMode.MOVE);
      System.out.println("over");
    });

    setOnDragDropped(event -> {
      Dragboard dragboard = event.getDragboard();
      System.out.println("dropped");
    });

    setOnMouseClicked(event -> {
      System.out.println("done");
    });

  }

  private void initializeWith(int columns, int rows) {
    fields = new Field[columns][rows];
    for (int i = 0; i < columns; i++) {
      for (int j = 0; j < rows; j++) {
        Field f = new Field(FIELD_SIZE, i * (FIELD_SIZE + SPACE), j * (FIELD_SIZE + SPACE));
        fields[i][j] = f;
        getChildren().add(f);
      }
    }
  }

  public Field getFieldFor(double x, double y) {
    if (isInRange(x, y)) {
      return fields[((int) (x / (FIELD_SIZE + SPACE)))][((int) (y / (FIELD_SIZE + SPACE)))];
    }
    return null;
  }

  private boolean isInRange(double x, double y) {
    return isInHorizontalRange(x) && isInVerticalRange(y);
  }

  private boolean isInHorizontalRange(double x) {
    return 0 < x && x < rows * (FIELD_SIZE + SPACE);
  }

  private boolean isInVerticalRange(double y) {
    return 0 < y && y < columns * (FIELD_SIZE + SPACE);
  }

}
