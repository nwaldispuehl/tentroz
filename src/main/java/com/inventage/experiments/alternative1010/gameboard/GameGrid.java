package com.inventage.experiments.alternative1010.gameboard;

import com.google.common.collect.Maps;
import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.GAMEGRID_BACKGROUND;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.GAMEGRID_PIECE_ACTIVE;
import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.GAMEGRID_PIECE_INACTIVE;

/**
 * The game grid maintains the grid and the state of its items.
 */
public class GameGrid extends Group {

  public static final int FIELD_SIZE = 56;
  public static final int SPACE = 4;

  private static final double CLEANING_SCALE_TRANSITION_DURATION_MS = 200;

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
    Rectangle background = new Rectangle(columns * (FIELD_SIZE + SPACE) - SPACE, rows * (FIELD_SIZE + SPACE) - SPACE, GAMEGRID_BACKGROUND);
    background.toBack();
    getChildren().add(background);
  }

  public Tuple<Integer, Integer> getGridPositionFor(double x, double y) {
    return new Tuple<>(((int) (x / (FIELD_SIZE + SPACE))), ((int) (y / (FIELD_SIZE + SPACE))));
  }

  private void registerDropListener() {

    setOnDragExited(event -> {
      clearDropTarget();
    });

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
      storeIntoFields(piece, event);
      gameBoard.dropItem(piece);
      checkAndRemoveCompleteRowsWith(piece, event);
    });

    setOnMouseDragOver(event -> {
      event.consume();
      System.out.println("mouse over");
    });
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

    for (Map.Entry<Integer, Integer> e : rowsToClean.entrySet()) {
      cleanRowStartingFrom(e.getKey(), e.getValue());
    }

    for (Map.Entry<Integer, Integer> e : colsToClean.entrySet()) {
      cleanColumnStartingFrom(e.getKey(), e.getValue());
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

      new Task<Object>(){
        @Override
        protected Object call() throws Exception {
          if (field != null) {

            ScaleTransition st = new ScaleTransition(Duration.millis(CLEANING_SCALE_TRANSITION_DURATION_MS), field);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(0);
            st.setToY(0);
            st.play();



            // TODO: Children are not removed. Why?
//            GameGrid.this.getChildren().remove(field);

//            ScheduledService<Object> svc = new ScheduledService<Object>() {
//              protected Task<Object> createTask() {
//                return new Task<Object>() {
//                  protected Object call() {
//                    GameGrid.this.getChildren().remove(field);
//                    return null;
//                  }
//                };
//              }
//            };
//            svc.setDelay(Duration.millis(CLEANING_SCALE_TRANSITION_DURATION_MS));
//            svc.start();
          }
          return null;
        }
      }.run();
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



}
