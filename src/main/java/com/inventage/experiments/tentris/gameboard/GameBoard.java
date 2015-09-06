package com.inventage.experiments.tentris.gameboard;

import com.inventage.experiments.tentris.gameboard.nextblocks.NextBlockContainer;
import com.inventage.experiments.tentris.gameboard.piece.DraggablePiece;
import com.inventage.experiments.tentris.gameboard.piece.PieceLibrary;
import com.inventage.experiments.tentris.gameboard.sound.ResourceUtil;
import com.inventage.experiments.tentris.gameboard.sound.SoundManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Game control.
 */
public class GameBoard extends StackPane {

  private static final String STYLE_SHEET_PATH = "styles.css";
  private static final String SPECIAL_POINTS_ID = "specialPoints";

  ResourceUtil resourceUtil = new ResourceUtil();
  SoundManager soundManager = new SoundManager();
  URL dropSound = resourceUtil.getUrlFrom("drop.aiff");

  private BorderPane backgroundPane;
  private BorderPane scorePane;
  private SimpleIntegerProperty score = new SimpleIntegerProperty(0);
  private SimpleIntegerProperty highScore = new SimpleIntegerProperty(0);
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

    backgroundPane = new BorderPane();
    getChildren().add(backgroundPane);

    scorePane = new BorderPane();
    scorePane.setPrefHeight(80);
    scorePane.setCenter(createScorePane());
    backgroundPane.setTop(scorePane);

    gameGrid = new GameGrid(this, 10, 10);
    backgroundPane.setCenter(gameGrid);

    nextBlockContainer = new NextBlockContainer(3);
    backgroundPane.setBottom(nextBlockContainer);

    for (int i = 0; i < 3; i++) {
      cycleInNewRandomItem();
    }
  }

  private Pane createScorePane() {
    HBox scorePane = new HBox();
    scorePane.setId("scorePane");
    scorePane.alignmentProperty().setValue(Pos.CENTER);
    scorePane.setSpacing(10);

    Label scoreLabel = new Label();
    scoreLabel.setId("score");
    scoreLabel.textProperty().bindBidirectional(score, new NumberStringConverter());
    scorePane.getChildren().add(scoreLabel);

    scorePane.getChildren().add(new Label("—"));

    Label highScoreLabel = new Label();
    highScoreLabel.setId("highScore");
    highScoreLabel.textProperty().bindBidirectional(highScore, new NumberStringConverter());
    scorePane.getChildren().add(highScoreLabel);

    return scorePane;
  }

  public DraggablePiece getPieceFor(String pieceId) {
    return pieces.get(pieceId);
  }

  public void cycleInNewRandomItem() {
    currentPiece = pieceLibrary.nextRandomPiece();
    pieces.put(currentPiece.getPieceId(), currentPiece);
    nextBlockContainer.cycleIn(currentPiece);
  }

  public void dropItem(DraggablePiece piece, SpecialPoint... specialPoints) {
    soundManager.play(dropSound);
    countPointsOf(piece);
    processSpecialPoints(specialPoints);

    removeFromList(piece);
    cycleInNewRandomItem();
    checkForFinishedGame();
  }

  private void processSpecialPoints(SpecialPoint[] specialPoints) {
    int i = 0;
    for (SpecialPoint sp : specialPoints) {
      count(sp.points());
      displaySpecialPointIn(sp, 1 + 600 * i);
      i++;
    }
  }

  private void displaySpecialPointIn(SpecialPoint specialPoint, double ms) {
    Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(ms),
        actionEvent -> Platform.runLater(
            new Runnable() {
              @Override
              public void run() {
                displayOverlayText("+" + String.valueOf(specialPoint.points()));
                if (specialPoint.getSoundEffectFileName() != null) {
                  soundManager.play(resourceUtil.getUrlFrom(specialPoint.getSoundEffectFileName()));
                }
              }
            }
        )
    ));
    timeline.play();
  }

  private void displayOverlayText(String string) {
    BorderPane specialPointsContainer = new BorderPane();
    specialPointsContainer.setMouseTransparent(true);
    getChildren().add(specialPointsContainer);

    Text specialPoints = new Text(string);
    specialPoints.setMouseTransparent(true);
    specialPoints.setId(SPECIAL_POINTS_ID);
    specialPointsContainer.setCenter(specialPoints);

    FadeTransition fadeIn = new FadeTransition(Duration.millis(200), specialPoints);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.play();

    FadeTransition fadeOut = new FadeTransition(Duration.millis(400), specialPoints);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    fadeOut.setDelay(Duration.millis(400));
    fadeOut.play();

    ScaleTransition enlarge = new ScaleTransition(Duration.millis(1200), specialPoints);
    enlarge.setFromX(1);
    enlarge.setFromY(1);
    enlarge.setToX(2);
    enlarge.setToY(2);
    enlarge.play();

    Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(1500),
        actionEvent -> getChildren().remove(specialPointsContainer))
    );
    timeline.play();
  }

  private void countPointsOf(DraggablePiece piece) {
    count(piece.getPoints());
  }

  private void count(Integer points) {
    score.set(score.get() + points);
    if (highScore.get() < score.get()) {
      highScore.set(score.get());
    }
  }

  private void removeFromList(DraggablePiece piece) {
    pieces.remove(piece.getId());
  }

  private void checkForFinishedGame() {
    // TODO
  }
}
