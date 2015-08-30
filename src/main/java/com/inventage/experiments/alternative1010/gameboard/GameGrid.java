package com.inventage.experiments.alternative1010.gameboard;

import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;
import javafx.scene.Group;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * The game grid maintains the grid and the state of its items.
 */
public class GameGrid extends Group {

  public static final int FIELD_SIZE = 56;
  public static final int SPACE = 4;

  private Field[][] baseFields;
  private Field[][] fields;

  private GameBoard gameBoard;
  private int columns;
  private int rows;

  public GameGrid(GameBoard gameBoard, int columns, int rows) {
    this.gameBoard = gameBoard;
    this.columns = columns;
    this.rows = rows;
    initializeWith(columns, rows);
    registerDropListener();

  }

  private void registerDropListener() {

    setOnDragEntered(event -> {
      System.out.println("entered");
    });

    setOnDragExited(event -> {
      System.out.println("exited");
    });

    setOnDragOver(event -> {
      event.acceptTransferModes(TransferMode.MOVE);
      event.consume();
    });

    setOnDragDropped(event -> {
      DraggablePiece piece = retrievePieceFrom(event);

      Tuple<Integer, Integer> gridPosition = getGridPositionFor(event.getX(), event.getY());
      System.out.println(piece + " on " + event.getX() + "/" + event.getY() + "(" + (gridPosition.first + 1) + "," + (gridPosition.second + 1) + ")");
      System.out.println("dropped");

      for (Field f : getFieldsFor(piece, event)) {
        if (f != null) {
          f.setColor(Color.RED);
        }
      }

      gameBoard.cycleItem();
    });

    setOnMouseDragOver(event -> {
      event.consume();
      System.out.println("mouse over");
    });

  }

  private DraggablePiece retrievePieceFrom(DragEvent dragEvent) {
    String pieceId = dragEvent.getDragboard().getString();
    return getPieceBy(pieceId);
  }

  private DraggablePiece getPieceBy(String pieceId) {
    return gameBoard.getPieceFor(pieceId);
  }

  private List<Field> getFieldsFor(DraggablePiece piece, DragEvent dragEvent) {
    return getFieldsFor(piece, dragEvent.getX(), dragEvent.getY());
  }

  private List<Field> getFieldsFor(DraggablePiece piece, double centerX, double centerY) {
    List<Field> result = newArrayList();

    double topLeftX = centerX - piece.getBoundsInParent().getWidth()/2;
    double topLeftY = centerY - piece.getBoundsInParent().getHeight()/2;

    List<Tuple<Double, Double>> childrenPosition = piece.getTransformedChildrenPosition();

    for (Tuple<Double, Double> t : childrenPosition) {
      result.add(getFieldFor(topLeftX + t.first, topLeftY + t.second));
    }

    return result;
  }

  private void initializeWith(int columns, int rows) {
    setBackgroundWith(columns, rows);
    baseFields = new Field[columns][rows];
    fields = new Field[columns][rows];
    for (int i = 0; i < columns; i++) {
      for (int j = 0; j < rows; j++) {
        Field f = new Field(FIELD_SIZE, i * (FIELD_SIZE + SPACE), j * (FIELD_SIZE + SPACE));
        baseFields[i][j] = f;
        getChildren().add(f);
      }
    }
  }

  private void setBackgroundWith(int columns, int rows) {
    Rectangle background = new Rectangle(columns * (FIELD_SIZE + SPACE) - SPACE, rows * (FIELD_SIZE + SPACE) - SPACE, Color.WHITE);
    background.toBack();
    getChildren().add(background);
  }

  public Tuple<Integer, Integer> getGridPositionFor(double x, double y) {
    return new Tuple<>(((int) (x / (FIELD_SIZE + SPACE))), ((int) (y / (FIELD_SIZE + SPACE))));
  }

  public Field getFieldFor(double x, double y) {
    if (isInRange(x, y)) {
      Tuple<Integer, Integer> position = getGridPositionFor(x, y);
      return baseFields[position.first][position.second];
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
