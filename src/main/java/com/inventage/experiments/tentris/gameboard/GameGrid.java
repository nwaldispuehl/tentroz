package com.inventage.experiments.tentris.gameboard;

import com.google.common.collect.Maps;
import com.inventage.experiments.tentris.gameboard.piece.DraggablePiece;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.GAMEGRID_BACKGROUND;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.GAMEGRID_PIECE_ACTIVE;
import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.GAMEGRID_PIECE_INACTIVE;

/**
 * The game grid maintains the grid and the state of its items.
 */
public class GameGrid extends Group {

//  private static final int FIELD_SIZE = 30; // 56
//  private static final int SPACE = 2; // 4

  public static final double FIELD_SPACE_FACTOR = 0.1;

  private static final Duration GARBAGE_COLLECTION_PERIOD = Duration.seconds(10);
  private static final Duration CLEANING_SCALE_TRANSITION_DURATION = Duration.millis(200);

  private Field[][] baseFields;
  private Field[][] fields;

  private List<SpecialPoint> specialPoints = newArrayList();

  private GameBoard gameBoard;

  private double fieldSize;
  private double fieldSpace;

  private int columns;
  private int rows;

  public GameGrid(GameBoard gameBoard, double fieldSize, int columns, int rows) {
    this.gameBoard = gameBoard;
    this.fieldSize = fieldSize;
    this.fieldSpace = fieldSize * FIELD_SPACE_FACTOR;
    this.columns = columns;
    this.rows = rows;
    initializeWith(columns, rows);
    registerDropListener();
  }

  public double fieldSize() {
    return fieldSize;
  }

  public double fieldSpace() {
    return fieldSpace;
  }

  private void initializeWith(int columns, int rows) {
    setBackgroundWith(columns, rows);
    baseFields = new Field[columns][rows];
    fields = new Field[columns][rows];
    for (int i = 0; i < columns; i++) {
      for (int j = 0; j < rows; j++) {
        Field f = new Field(fieldSize, i * (fieldSize + fieldSpace), j * (fieldSize + fieldSpace));
        baseFields[i][j] = f;
        getChildren().add(f);
      }
    }
    startGarbageCollection();
  }

  private void setBackgroundWith(int columns, int rows) {
    Rectangle background = new Rectangle(columns * (fieldSize + fieldSpace) - fieldSpace, rows * (fieldSize + fieldSpace) - fieldSpace, GAMEGRID_BACKGROUND);
    background.toBack();
    getChildren().add(background);
  }

  public Tuple<Integer, Integer> getGridPositionFor(double x, double y) {
    return new Tuple<>(((int) (x / (fieldSize + fieldSpace))), ((int) (y / (fieldSize + fieldSpace))));
  }

  private void registerDropListener() {

    setOnDragExited(event -> clearDropTarget());

    setOnDragOver(event -> {
      clearDropTarget();

      DraggablePiece piece = retrievePieceFrom(event);
      if (allFieldsInGridFor(piece, event) && allFieldsFreeFor(piece, event)) {
        event.acceptTransferModes(TransferMode.MOVE);
        markDropTargetOf(piece, event);
      }
      event.consume();
    });

    setOnDragDropped(event -> {
      DraggablePiece piece = retrievePieceFrom(event);

      if (allFieldsInGridFor(piece, event) && allFieldsFreeFor(piece, event)) {
        storeIntoFields(piece, event);
        checkAndRemoveCompleteRowsWith(piece, event);
        checkForEmptyField();
        gameBoard.dropItem(piece, specialPoints.toArray(new SpecialPoint[0]));
        specialPoints.clear();
      }
      event.consume();
    });

    setOnMouseDragOver(event -> event.consume());
  }

  private void checkForEmptyField() {
    if (isFieldEmpty()) {
      specialPoints.add(SpecialPoint.COMPLETELY_EMPTY);
    }
  }

  private boolean isFieldEmpty() {
    for (int i = 0; i < fields.length; i++) {
      for (int j = 0; j < fields[i].length; j++) {
        if (fields[i][j] != null) {
          return false;
        }
      }
    }
    return true;
  }

  private void markDropTargetOf(DraggablePiece piece, DragEvent dragEvent) {
    for (Tuple<Integer, Integer> p : getPositionsFor(piece, dragEvent)) {
      baseFields[p.first][p.second].setColor(GAMEGRID_PIECE_ACTIVE);
    }
  }

  private void clearDropTarget() {
    for (int i = 0; i < baseFields.length; i++) {
      for (int j = 0; j < baseFields[i].length; j++) {
        baseFields[i][j].setColor(GAMEGRID_PIECE_INACTIVE);
      }
    }
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
    Field field = new Field(fieldSize, col * (fieldSize + fieldSpace), row * (fieldSize + fieldSpace));
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

  private boolean isInRange(double x, double y) {
    return isInHorizontalRange(x) && isInVerticalRange(y);
  }

  private boolean isInHorizontalRange(double x) {
    return 0 < x && x < rows * (fieldSize + fieldSpace);
  }

  private boolean isInVerticalRange(double y) {
    return 0 < y && y < columns * (fieldSize + fieldSpace);
  }

  public void checkAndRemoveCompleteRowsWith(DraggablePiece piece, DragEvent dragEvent) {
    Map<Integer, Integer> rowsToClean = Maps.newHashMap();
    Map<Integer, Integer> colsToClean = Maps.newHashMap();

    for (Tuple<Integer, Integer> p : getPositionsFor(piece, dragEvent)) {
      if (isRowFull(p.second)) {
        rowsToClean.put(p.second, p.first);
      }

      if (isColumnFull(p.first)) {
        colsToClean.put(p.first, p.second);
      }
    }

    checkForSpecialPointsIn(rowsToClean, colsToClean);

    for (Map.Entry<Integer, Integer> e : rowsToClean.entrySet()) {
      cleanRowStartingFrom(e.getKey(), e.getValue());
    }

    for (Map.Entry<Integer, Integer> e : colsToClean.entrySet()) {
      cleanColumnStartingFrom(e.getKey(), e.getValue());
    }

  }

  private void checkForSpecialPointsIn(Map<Integer, Integer> rowsToClean, Map<Integer, Integer> colsToClean) {
    if (1 < rowsToClean.size()) {
      specialPoints.add(SpecialPoint.getForRows(rowsToClean.size()));
    }
    if (1 < colsToClean.size()) {
      specialPoints.add(SpecialPoint.getForRows(colsToClean.size()));
    }
  }

  private void cleanRowStartingFrom(int row, int startFromCol) {
    for (int i = 0; i < columns; i++) {
      if (0 <= startFromCol - i) {
        enqueueCleaningOf(startFromCol - i, row);
      }
      if (startFromCol + i < columns) {
        enqueueCleaningOf(startFromCol + i, row);
      }
    }
  }

  private void cleanColumnStartingFrom(int column, int startFromRow) {
    for (int i = 0; i < rows; i++) {
      if (0 <= startFromRow - i) {
        enqueueCleaningOf(column, startFromRow - i);
      }
      if (startFromRow + i < rows) {
        enqueueCleaningOf(column, startFromRow + i);
      }
    }
  }

  private void enqueueCleaningOf(int column, int row) {
    final Field field = fields[column][row];
    if (field != null) {
      fields[column][row] = null;
      field.markAsGarbage();
      scheduleMinimizingTransitionTaskFor(field);
    }
  }

  private boolean isRowFull(int row) {
    for (int i = 0; i < columns; i++) {
      if (fields[i][row] == null) {
        return false;
      }
    }
    return true;
  }

  private boolean isColumnFull(int column) {
    for (int i = 0; i < rows; i++) {
      if (fields[column][i] == null) {
        return false;
      }
    }
    return true;
  }

  private void scheduleMinimizingTransitionTaskFor(final Field field) {
    new Task<Object>(){
      @Override
      protected Object call() throws Exception {
        if (field != null) {
          ScaleTransition st = new ScaleTransition(CLEANING_SCALE_TRANSITION_DURATION, field);
          st.setFromX(1);
          st.setFromY(1);
          st.setToX(0);
          st.setToY(0);
          st.play();
        }
        return null;
      }
    }.run();
  }


  private synchronized void startGarbageCollection() {
      Platform.setImplicitExit(false);
      ScheduledService<Object> svc = new ScheduledService<Object>() {
        protected Task<Object> createTask() {
          return new Task<Object>() {
            protected Object call() {
              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  for (Node n : newArrayList(getChildren())) {
                    if (n instanceof Field && ((Field) n).isGarbage()) {
                      getChildren().remove(n);
                    }
                  }
                }
              });
              return null;
            }
          };
        }
      };
      svc.setPeriod(GARBAGE_COLLECTION_PERIOD);
      svc.start();
  }

}
