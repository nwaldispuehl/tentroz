package com.inventage.experiments.alternative1010.gameboard;

import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;
import javafx.scene.Group;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
      DraggablePiece piece = retrievePieceFrom(event);
      if (allFieldsInGridFor(piece, event) && allFieldsFreeFor(piece, event)) {
        event.acceptTransferModes(TransferMode.MOVE);
      }

      event.consume();
    });

    setOnDragDropped(event -> {
      DraggablePiece piece = retrievePieceFrom(event);
      storeIntoFields(piece, event);
      gameBoard.dropItem(piece);
      countPointsFor(piece);
      checkForCompleteRows();
    });

    setOnMouseDragOver(event -> {
      event.consume();
      System.out.println("mouse over");
    });
  }



  private void storeIntoFields(DraggablePiece piece, DragEvent dragEvent) {
    for (Tuple<Integer, Integer> t : getPositionsFor(piece, dragEvent)) {
      Field field = createFieldWith(piece.getColor(), t.first, t.second);
      fields[t.first][t.second] = field;
      getChildren().add(field);
      field.toFront();
    }
  }

  private Field createFieldWith(Paint color, int col, int row) {
    Field field = new Field(FIELD_SIZE, col * (FIELD_SIZE + SPACE), row * (FIELD_SIZE + SPACE));
    field.setColor(color);
    return field;
  }

  private boolean allFieldsFreeFor(DraggablePiece piece, DragEvent dragEvent) {
    for (Tuple<Integer, Integer> t : getPositionsFor(piece, dragEvent)) {
      if (fields[t.first][t.second] != null) {
        return false;
      }
    }
    return true;
  }

  private boolean allFieldsInGridFor(DraggablePiece piece, DragEvent dragEvent) {
    double topLeftX = dragEvent.getX() - piece.getBoundsInParent().getWidth()/2;
    double topLeftY = dragEvent.getY() - piece.getBoundsInParent().getHeight()/2;

    for (Tuple<Double, Double> t : piece.getTransformedChildrenPosition()) {
      if (!isInRange(topLeftX + t.first, topLeftY + t.second)) {
        return false;
      }
    }
    return true;
  }



  private DraggablePiece retrievePieceFrom(DragEvent dragEvent) {
    String pieceId = dragEvent.getDragboard().getString();
    return getPieceBy(pieceId);
  }

  private DraggablePiece getPieceBy(String pieceId) {
    return gameBoard.getPieceFor(pieceId);
  }

  private List<Tuple<Integer, Integer>> getPositionsFor(DraggablePiece piece, DragEvent dragEvent) {
    return getPositionsFor(piece, dragEvent.getX(), dragEvent.getY());
  }

  private List<Tuple<Integer, Integer>> getPositionsFor(DraggablePiece piece, double centerX, double centerY) {
    List<Tuple<Integer, Integer>> result = newArrayList();

    double topLeftX = centerX - piece.getBoundsInParent().getWidth()/2;
    double topLeftY = centerY - piece.getBoundsInParent().getHeight()/2;

    for (Tuple<Double, Double> t : piece.getTransformedChildrenPosition()) {
      result.add(getGridPositionFor(topLeftX + t.first, topLeftY + t.second));
    }

    return result;
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
      result.add(getBaseFieldFor(topLeftX + t.first, topLeftY + t.second));
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

  public Field getBaseFieldFor(double x, double y) {
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

  private void countPointsFor(DraggablePiece piece) {
    gameBoard.count(piece.getPoints());
  }

  private void checkForCompleteRows() {
    // TODO
  }



}
