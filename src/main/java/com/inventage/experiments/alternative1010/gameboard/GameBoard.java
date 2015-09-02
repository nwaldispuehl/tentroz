package com.inventage.experiments.alternative1010.gameboard;

import com.inventage.experiments.alternative1010.gameboard.nextblocks.NextBlockContainer;
import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;
import com.inventage.experiments.alternative1010.gameboard.piece.PieceLibrary;
import com.inventage.experiments.alternative1010.gameboard.sound.ResourceUtil;
import com.inventage.experiments.alternative1010.gameboard.sound.SoundManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Game control.
 */
public class GameBoard extends BorderPane {

  private static final String STYLE_SHEET_PATH = "styles.css";

  ResourceUtil resourceUtil = new ResourceUtil();
  SoundManager soundManager = new SoundManager();
  URL dropSound = resourceUtil.getUrlFrom("drop.aiff");

  private BorderPane scorePane;
  private SimpleIntegerProperty score = new SimpleIntegerProperty(0);
  private SimpleStringProperty information = new SimpleStringProperty();
  private GameGrid gameGrid;

  private NextBlockContainer nextBlockContainer;

  private PieceLibrary pieceLibrary = new PieceLibrary();

  private Map<String, DraggablePiece> pieces = newHashMap();
  private DraggablePiece currentPiece;

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
    scorePane.setRight(createInformationLabel());
    setTop(scorePane);

    gameGrid = new GameGrid(this, 10, 10);
    setCenter(gameGrid);

    nextBlockContainer = new NextBlockContainer(3);
    setBottom(nextBlockContainer);

    for (int i = 0; i < 3; i++) {
      cycleInNewRandomItem();
    }
  }



  private Label createScoreLabel() {
    Label scoreLabel = new Label();
    scoreLabel.setId("score");
    scoreLabel.textProperty().bindBidirectional(score, new NumberStringConverter());
    return scoreLabel;
  }

  private Node createInformationLabel() {
    Label informationLabel = new Label();
    informationLabel.setId("information");
    informationLabel.textProperty().bindBidirectional(information);
    return informationLabel;
  }

  public DraggablePiece getPieceFor(String pieceId) {
    return pieces.get(pieceId);
  }

  public void cycleInNewRandomItem() {
    currentPiece = pieceLibrary.nextRandomPiece();
    pieces.put(currentPiece.getPieceId(), currentPiece);
    nextBlockContainer.cycleIn(currentPiece);
  }

  public void dropItem(DraggablePiece piece) {
    soundManager.play(dropSound);
    countPointsOf(piece);
    removeFromList(piece);
    cycleInNewRandomItem();
    checkForFinishedGame();

    // Update information
    information.setValue("Children: " + gameGrid.getChildren().size());
  }


  private void countPointsOf(DraggablePiece piece) {
    count(piece.getPoints());
  }

  private void count(Integer points) {
    score.set(score.get() + points);
  }

  private void removeFromList(DraggablePiece piece) {
    pieces.remove(piece.getId());
  }

  private void checkForFinishedGame() {
    // TODO
  }
}
