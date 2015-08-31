package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;
import com.inventage.experiments.alternative1010.gameboard.GameGrid;
import com.inventage.experiments.alternative1010.gameboard.Tuple;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

/**
 * A piece is a unique combination of fields to some geometric structure.
 */
public abstract class DraggablePiece extends Region {

  private static final int ROTATION_DEGREES = 90;

  protected static final int GRID_SIZE = GameGrid.FIELD_SIZE + GameGrid.SPACE;

  private Paint color;

  private UUID id;

  protected DraggablePiece(Paint color) {
    this.color = color;
    id = UUID.randomUUID();
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
    return largestX + GameGrid.FIELD_SIZE;
  }

  @Override
  protected double computePrefHeight(double width) {
    double largestY = 0;
    for (Field f : getFields()) {
      if (largestY <= f.getY()) {
        largestY = f.getY();
      }
    }
    return largestY + GameGrid.FIELD_SIZE;
  }

  private Collection<Field> getFields() {
    return getChildren().stream().map(n -> (Field) n).collect(Collectors.toList());
  }

  public void rotate() {
    getTransforms().add(new Rotate(ROTATION_DEGREES, getBoundsInLocal().getWidth()/2, getBoundsInLocal().getHeight()/2));
    requestLayout();
  }


  public void animateToSmaller() {
    getChildren().forEach(n -> animateToSmaller(n));
  }

  private void animateToSmaller(Node n) {
    ScaleTransition st = new ScaleTransition(Duration.millis(200), n);
    st.setFromX(1f);
    st.setFromY(1f);

    st.setToX(0.9f);
    st.setToY(0.9f);

    st.play();
  }

  public void animateToNormal() {
    getChildren().forEach(n -> animateToNormal(n));
  }

  private void animateToNormal(Node n) {
    ScaleTransition st = new ScaleTransition(Duration.millis(200), n);
    st.setFromX(0.9f);
    st.setFromY(0.9f);

    st.setToX(1f);
    st.setToY(1f);

    st.play();
  }

  public void setDraggable() {
    setOnDragDetected(event -> {
//      startFullDrag();
      startDragAndDrop(TransferMode.MOVE);

      Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(getPieceId());

      SnapshotParameters params = new SnapshotParameters();
      params.setFill(Color.color(1, 1, 1, 0));
      dragboard.setDragView(snapshot(params, null));
      dragboard.setDragViewOffsetX(getBoundsInParent().getWidth() / 2);
      dragboard.setDragViewOffsetY(getBoundsInParent().getHeight() / 2);
      dragboard.setContent(content);
      event.consume();
    });
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

  private void addHalfBlockSize(List<Tuple<Double, Double>> positions) {
    double halfGridSize = GRID_SIZE/2;
    for (Tuple<Double, Double> t : positions) {
      t.first += halfGridSize;
      t.second += halfGridSize;
    }
  }


}
