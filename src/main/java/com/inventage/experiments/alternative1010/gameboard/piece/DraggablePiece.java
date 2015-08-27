package com.inventage.experiments.alternative1010.gameboard.piece;

import com.inventage.experiments.alternative1010.gameboard.Field;
import com.inventage.experiments.alternative1010.gameboard.GameGrid;
import javafx.scene.Group;
import javafx.scene.paint.Paint;

import java.util.List;

/**
 * A piece is a unique combination of fields to some geometric structure.
 */
public abstract class DraggablePiece extends Group {

  private static final int ROTATION_DEGREES = 90;

  protected static final int GRID_SIZE = GameGrid.FIELD_SIZE + GameGrid.SPACE;

  double originalSceneX, originalSceneY;
  double orgTranslateX, orgTranslateY;

  protected DraggablePiece(Paint color, List<Field> fields) {
    getChildren().addAll(fields);
    for (Field f : fields) {
      f.setColor(color);
    }
  }

  public void rotate() {
    setRotate((getRotate() + 90) % 360);
  }

  public void setDraggable() {

    setOnDragDetected(event -> {
//      setManaged(false);
      startFullDrag();


//      Dragboard dragboard = node.startDragAndDrop(TransferMode.MOVE);
//      ClipboardContent content = new ClipboardContent();
//      content.put(DataFormat.FILES, node);
//      dragboard.setContent(content);

//      event.consume();
    });

    setOnMousePressed(event -> {
      setMouseTransparent(true);
    });

    setOnMouseReleased(event -> {
      setMouseTransparent(false);
      resetPosition();
    });



    setOnMousePressed(event -> {
      originalSceneX = event.getSceneX();
      originalSceneY = event.getSceneY();
      orgTranslateX = getTranslateX();
      orgTranslateY = getTranslateY();
    });

    setOnMouseDragged(event -> {
      double offsetX = event.getSceneX() - originalSceneX;
      double offsetY = event.getSceneY() - originalSceneY;

      setTranslateX(orgTranslateX + offsetX);
      setTranslateY(orgTranslateY + offsetY);
    });
  }

  private void resetPosition() {
    setTranslateX(0);
    setTranslateY(0);
  }

}
