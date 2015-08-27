package com.inventage.experiments.alternative1010.gameboard;

import com.google.common.collect.Queues;
import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;
import com.inventage.experiments.alternative1010.gameboard.piece.PieceLibrary;
import com.inventage.experiments.alternative1010.gameboard.piece.TripleChunk;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nw on 26.08.15.
 */
public class GameBoard extends BorderPane {

  private static final String STYLE_SHEET_PATH = "styles.css";

  private BorderPane scorePane;
  private SimpleStringProperty score = new SimpleStringProperty("0");
  private GameGrid gameGrid;
  private GraphicsContext gc;
  private HBox nextBlocksPane;

  private PieceLibrary pieceLibrary = new PieceLibrary();

  private Queue<DraggablePiece> pieceQueue = new LinkedBlockingQueue<>(3);

  public GameBoard(double width, double height) {
    initializeGameBoardWith(width, height);
    setPrefWidth(width);
    setPrefHeight(height);
  }

  private void initializeGameBoardWith(double width, double height) {
    getStylesheets().add(getClass().getClassLoader().getResource(STYLE_SHEET_PATH).toExternalForm());

    scorePane = new BorderPane();
    scorePane.setPrefHeight(80);
    scorePane.setCenter(createScoreLabel());
    setTop(scorePane);

    gameGrid = new GameGrid(10, 10);
    setCenter(gameGrid);

    BorderPane nextBlocksPaneContainer = new BorderPane();
    nextBlocksPaneContainer.setId("nextBlockPaneContainer");
    nextBlocksPaneContainer.setPrefHeight((GameGrid.FIELD_SIZE + GameGrid.SPACE) * 5 + 20);
    nextBlocksPaneContainer.setPadding(new Insets(10, 0, 0, 0));
    setBottom(nextBlocksPaneContainer);

    nextBlocksPane = new HBox();
    nextBlocksPane.alignmentProperty().setValue(Pos.CENTER);
    nextBlocksPane.setSpacing(20);

    for (int i = 0; i < 3; i++) {
      DraggablePiece piece = pieceLibrary.nextRandomPiece();
      pieceQueue.add(piece);

      if (i == 0) {
        piece.setDraggable();
        piece.setOnMouseClicked(event -> {piece.rotate();});
      }

//      nextBlocksPane.getChildren().add(new BorderPane(new TripleChunk()));
//      nextBlocksPane.clipProperty().setValue(Nod);
    }

    for (DraggablePiece p : pieceQueue) {
      BorderPane borderPane = new BorderPane(p);
      borderPane.layout();
      HBox.setHgrow(borderPane, Priority.ALWAYS);
      nextBlocksPane.getChildren().add(borderPane);
    }

    nextBlocksPaneContainer.setCenter(nextBlocksPane);

    setPadding(new Insets(10, 10, 10, 10));
  }

  private Label createScoreLabel() {
    Label scoreLabel = new Label();
    scoreLabel.setId("score");
    scoreLabel.textProperty().bind(score);
    return scoreLabel;
  }

}
