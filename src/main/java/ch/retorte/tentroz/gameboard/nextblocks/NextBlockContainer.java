package ch.retorte.tentroz.gameboard.nextblocks;

import ch.retorte.tentroz.gameboard.piece.DraggablePiece;
import ch.retorte.tentroz.gameboard.GameGrid;
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
  private double fieldSize;
  private int slots;


  //---- Constructor

  public NextBlockContainer(double fieldSize, int slots) {
    this.fieldSize = fieldSize;
    this.slots = slots;

    initialize();
    addPieceContainer();
  }


  //---- Methods

  private void initialize() {
    setId(CONTAINER_ID);

    double preferredSize = (fieldSize + (fieldSize * GameGrid.FIELD_SPACE_FACTOR)) * 5 + 20;
    setPrefHeight(preferredSize);
    setPrefWidth(preferredSize);
    setPadding(new Insets(10, 0, 0, 0));
  }

  private void addPieceContainer() {
    nextBlocksPane.alignmentProperty().setValue(Pos.CENTER);
    nextBlocksPane.setSpacing(20);
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
    borderPane.setPadding(new Insets(5,5,5,5));
    HBox.setHgrow(borderPane, Priority.ALWAYS);
    return borderPane;
  }

  private void makeFirstItemDraggableAndRotatable() {
    BorderPane borderPane = (BorderPane) nextBlocksPane.getChildren().get(0);
    DraggablePiece piece = (DraggablePiece) borderPane.getCenter();

    borderPane.setOpacity(1);
    borderPane.setOnMouseClicked(e -> piece.rotate());

    piece.setDraggable();
  }

  private void makeFollowingItemsTransparent() {
    if (2 <= nextBlocksPane.getChildren().size()) {
      nextBlocksPane.getChildren().get(1).setOpacity(0.4);
    }

    if (3 <= nextBlocksPane.getChildren().size()) {
      nextBlocksPane.getChildren().get(2).setOpacity(0.1);
    }
  }

  public DraggablePiece getFirst() {
    BorderPane borderPane = (BorderPane) nextBlocksPane.getChildren().get(0);
    return (DraggablePiece) borderPane.getCenter();
  }

  public void deactivate() {
    setDisabled(true);
    getFirst().setOnMouseClicked(event -> {});
  }
}
