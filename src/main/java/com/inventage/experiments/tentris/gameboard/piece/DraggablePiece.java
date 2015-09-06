package com.inventage.experiments.tentris.gameboard.piece;

import com.inventage.experiments.tentris.gameboard.Field;
import com.inventage.experiments.tentris.gameboard.GameGrid;
import com.inventage.experiments.tentris.gameboard.Tuple;
import javafx.animation.RotateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

/**
 * A piece is a unique combination of fields to some geometric structure.
 */
public abstract class DraggablePiece extends Region {

  private static final int[] ROTATION_ANGLES = new int[] {0, 90, 180, 270};
  private static final int ROTATION_DEGREES = 90;

  private static final double ROTATION_DURATION_MS = 100;

  private double fieldSize;
  private double fieldSpace;
  private Paint color;
  private UUID id;
  private int currentRotationSetting = 1;

  protected DraggablePiece(double fieldSize, Paint color) {
    this.fieldSize = fieldSize;
    this.fieldSpace = fieldSize * GameGrid.FIELD_SPACE_FACTOR;
    this.color = color;
    id = UUID.randomUUID();
  }

  protected double getFieldSize() {
    return fieldSize;
  }

  protected double getGridSize() {
    return fieldSize + fieldSpace;
  }

  public String getPieceId() {
    return id.toString();
  }

  protected void add(Field field) {
    getChildren().add(field);
    field.setColor(color);
    recalculateSize();
  }

  public Paint getColor() {
    return color;
  }

  public Integer getPoints() {
    return getFields().size();
  }

  private void recalculateSize() {
    setMaxWidth(computePrefWidth(0));
    setMaxHeight(computePrefHeight(0));
  }

  @Override
  protected double computePrefWidth(double height) {
    double largestX = 0;
    for (Field f : getFields()) {
      if (largestX <= f.getX()) {
        largestX = f.getX();
      }
    }
    return largestX + fieldSize;
  }

  @Override
  protected double computePrefHeight(double width) {
    double largestY = 0;
    for (Field f : getFields()) {
      if (largestY <= f.getY()) {
        largestY = f.getY();
      }
    }
    return largestY + fieldSize;
  }

  private Collection<Field> getFields() {
    Collection<Field> result = new ArrayList<>();
    for (Node n : getChildren()) {
      result.add((Field) n);
    }

    return result;
  }

  public void rotate() {
    RotateTransition t = new RotateTransition(Duration.millis(ROTATION_DURATION_MS), this);

    t.setFromAngle(getRotate());
    t.setToAngle(ROTATION_ANGLES[currentRotationSetting++ % ROTATION_ANGLES.length]);
    t.play();

    requestLayout();
  }

  public void setDraggable() {
    setOnDragDetected(event -> {
//      startFullDrag();
      startDragAndDrop(TransferMode.MOVE);

      Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(getPieceId());
      dragboard.setContent(content);

      dragboard.setDragView(snapshot(getSnapshotParameters(), null));
      dragboard.setDragViewOffsetX(getBoundsInParent().getWidth() / 2);
      dragboard.setDragViewOffsetY(getBoundsInParent().getHeight() / 2);

      event.consume();
    });
  }

  private SnapshotParameters getSnapshotParameters() {
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.color(1, 1, 1, 0));
    return params;
  }

  public void setRotatable() {
    setOnMouseClicked(event -> rotate());
  }

  public List<Tuple<Double,Double>> getTransformedChildrenPosition() {
    List<Tuple<Double,Double>> result = newArrayList();

    for (Field f : getFields()) {
      Point2D point2D = localToParent(f.getX(), f.getY());
      result.add(new Tuple<>(point2D.getX(), point2D.getY()));
    }

    normalize(result);
    addHalfBlockSize(result);

    return result;
  }

  private void normalize(List<Tuple<Double, Double>> positions) {
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    for (Tuple<Double, Double> t : positions) {
      if (t.first < minX) {
        minX = t.first;
      }
      if (t.second < minY) {
        minY = t.second;
      }
    }
    for (Tuple<Double, Double> t : positions) {
      t.first -= minX;
      t.second -= minY;
    }

  }

  /**
   * This moves the point into the center of the fields.
   */
  private void addHalfBlockSize(List<Tuple<Double, Double>> positions) {
    double halfGridSize = getGridSize()/2;
    for (Tuple<Double, Double> t : positions) {
      t.first += halfGridSize;
      t.second += halfGridSize;
    }
  }


}
