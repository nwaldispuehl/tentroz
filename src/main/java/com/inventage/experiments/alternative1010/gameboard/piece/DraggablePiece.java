package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;
import com.inventage.experiments.alternative1010.gameboard.GameGrid;
import com.inventage.experiments.alternative1010.gameboard.Tuple;
import javafx.animation.ScaleTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

/**
 * A piece is a unique combination of fields to some geometric structure.
 */
public abstract class DraggablePiece extends Group {

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
  }

  private Rotate rotate90Deg = new Rotate(90, 0, 0);

  public void rotate() {
//    getTransforms().add(rotate90Deg);
    getChildren().forEach(f -> f.getTransforms().add(rotate90Deg));
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
      startFullDrag();
      startDragAndDrop(TransferMode.MOVE);

      Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(getPieceId());

      SnapshotParameters params = new SnapshotParameters();
      params.setFill(Color.color(1,1,1,0));
      dragboard.setDragView(snapshot(params, null));
      dragboard.setDragViewOffsetX(getBoundsInParent().getWidth()/2);
      dragboard.setDragViewOffsetY(getBoundsInParent().getHeight()/2);
      dragboard.setContent(content);
      event.consume();
    });

  }


  public List<Tuple<Double,Double>> getTransformedChildrenPosition() {

    // TODO: This unfortunately does not yet produce usable data...

    double width = getBoundsInParent().getWidth();
    double height = getBoundsInParent().getHeight();

    List<Tuple<Double,Double>> result = newArrayList();

    for (Node n : getChildrenUnmodifiable()) {
      Field f = (Field) n;

      Point2D point2D = f.localToParent(f.getX(), f.getY());
      double x = point2D.getX() ;
      double y = point2D.getY() ;

      Bounds bounds = f.getBoundsInParent();

      System.out.println(x + " " + y);
//      result.add(new Tuple<>(x + (bounds.getWidth() / 2), y + (bounds.getHeight() / 2)));
      result.add(new Tuple<>(x , y));
    }
    return result;
  }



}
