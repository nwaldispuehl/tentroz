package com.inventage.experiments.alternative1010.gameboard;

import com.inventage.experiments.alternative1010.gameboard.nextblocks.NextBlockContainer;
import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;
import com.inventage.experiments.alternative1010.gameboard.piece.PieceLibrary;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by nw on 26.08.15.
 */
public class GameBoard extends BorderPane {

  private static final String STYLE_SHEET_PATH = "styles.css";

  private BorderPane scorePane;
  private SimpleStringProperty score = new SimpleStringProperty("0");
  private GameGrid gameGrid;

  private NextBlockContainer nextBlockContainer;

  private PieceLibrary pieceLibrary = new PieceLibrary();

  private Map<String, DraggablePiece> pieces = newHashMap();

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

    gameGrid = new GameGrid(this, 10, 10);
    setCenter(gameGrid);

    nextBlockContainer = new NextBlockContainer(3);
    setBottom(nextBlockContainer);

    for (int i = 0; i < 3; i++) {
      DraggablePiece piece = pieceLibrary.nextRandomPiece();
      pieces.put(piece.getPieceId(), piece);


      nextBlockContainer.cycleIn(piece);
    }
  }

  private Label createScoreLabel() {
    Label scoreLabel = new Label();
    scoreLabel.setId("score");
    scoreLabel.textProperty().bind(score);
    return scoreLabel;
  }

  public DraggablePiece getPieceFor(String pieceId) {
    return pieces.get(pieceId);
  }

  public void cycleItem() {
    nextBlockContainer.cycleIn(pieceLibrary.nextRandomPiece());
  }

}
