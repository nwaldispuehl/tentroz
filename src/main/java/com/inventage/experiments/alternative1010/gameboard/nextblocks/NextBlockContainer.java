package com.inventage.experiments.alternative1010.gameboard.nextblocks;

import com.inventage.experiments.alternative1010.gameboard.GameGrid;
import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Container for the next blocks.
 */
public class NextBlockContainer extends BorderPane {

  //---- Static

  private static final String CONTAINER_ID = "nextBlockPaneContainer";

  //---- Fields

  private HBox nextBlocksPane = new HBox();
  private int slots;

  //---- Constructor

  public NextBlockContainer(int slots) {
    this.slots = slots;
    initialize();
    addPieceContainer();
  }

  //---- Methods

  private void initialize() {
    setId(CONTAINER_ID);
    setPrefHeight((GameGrid.FIELD_SIZE + GameGrid.SPACE) * 5 + 20);
    setPadding(new Insets(10, 0, 0, 0));
  }

  private void addPieceContainer() {
    nextBlocksPane.alignmentProperty().setValue(Pos.CENTER);
    nextBlocksPane.setSpacing(40);
    setCenter(nextBlocksPane);
  }

  public void cycleIn(DraggablePiece draggablePiece) {
    if (slots <= nextBlocksPane.getChildren().size()) {
      nextBlocksPane.getChildren().remove(0);
    }
    nextBlocksPane.getChildren().add(createPaneFor(draggablePiece));
    makeFirstItemDraggableAndRotatable();
    makeFollowingItemsTransparent();
  }

  private BorderPane createPaneFor(DraggablePiece draggablePiece) {
    BorderPane borderPane = new BorderPane(draggablePiece);
    borderPane.requestLayout();
    HBox.setHgrow(borderPane, Priority.ALWAYS);
    return borderPane;
  }

  private void makeFirstItemDraggableAndRotatable() {
    nextBlocksPane.getChildren().get(0).setOpacity(1);
    DraggablePiece piece = (DraggablePiece) ((BorderPane) nextBlocksPane.getChildren().get(0)).getCenter();
    piece.setDraggable();
    piece.setOnMouseClicked(event -> piece.rotate());
  }

  private void makeFollowingItemsTransparent() {
    if (2 <= nextBlocksPane.getChildren().size()) {
      nextBlocksPane.getChildren().get(1).setOpacity(0.4);
    }

    if (3 <= nextBlocksPane.getChildren().size()) {
      nextBlocksPane.getChildren().get(2).setOpacity(0.1);
    }
  }
}
