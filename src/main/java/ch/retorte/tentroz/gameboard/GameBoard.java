package ch.retorte.tentroz.gameboard;

import ch.retorte.tentroz.ActionListener;
import ch.retorte.tentroz.TenTroz;
import ch.retorte.tentroz.gameboard.nextblocks.NextBlockContainer;
import ch.retorte.tentroz.gameboard.piece.DraggablePiece;
import ch.retorte.tentroz.gameboard.piece.PieceLibrary;
import ch.retorte.tentroz.gameboard.sound.ResourceUtil;
import ch.retorte.tentroz.gameboard.sound.SoundManager;
import ch.retorte.tentroz.highscore.HighScore;
import ch.retorte.tentroz.highscore.HighScoreManager;
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

import java.math.BigInteger;
import java.net.URL;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Game control.
 */
public class GameBoard extends StackPane {

  //---- Static

  private static final int ROWS = 10;
  private static final int COLUMNS = 10;

  private static final String SPECIAL_POINTS_ID = "specialPoints";
  private final ActionListener finishGameAction;


  //---- Fields

  private ResourceUtil resourceUtil = new ResourceUtil();
  private SoundManager soundManager = new SoundManager();
  private URL dropSound = resourceUtil.getUrlFrom("drop.aiff");

  private HighScoreManager highScoreManager = HighScoreManager.getManager();

  private BorderPane backgroundPane;
  private BorderPane scorePane;
  private SimpleIntegerProperty score = new SimpleIntegerProperty(0);
  private SimpleIntegerProperty highScore = new SimpleIntegerProperty(0);
  private GameGrid gameGrid;

  private NextBlockContainer nextBlockContainer;

  private PieceLibrary pieceLibrary;

  private Map<String, DraggablePiece> pieces = newHashMap();
  private DraggablePiece currentPiece;


  //---- Constructor

  public GameBoard(double fieldSize, ActionListener finishGameAction) {
    this.finishGameAction = finishGameAction;

    pieceLibrary = new PieceLibrary(fieldSize);
    initializeGameBoardWith(fieldSize);

    // The width is roughly 16 x the field size.
    setPrefWidth(COLUMNS * fieldSize + 6 * fieldSize);

    // The height is roughly 21 x the field size.
    setPrefHeight(ROWS * fieldSize + 3 * fieldSize + 8 * fieldSize);
  }


  //---- Methods

  private void initializeGameBoardWith(double fieldSize) {
    getStylesheets().add(getClass().getClassLoader().getResource(TenTroz.STYLE_SHEET_PATH).toExternalForm());

    initializeHighScoreListener();
    initializeHighScore();

    backgroundPane = new BorderPane();
    getChildren().add(backgroundPane);

    scorePane = new BorderPane();
    scorePane.setPrefHeight(3 * fieldSize);
    scorePane.setCenter(createScorePane());
    backgroundPane.setTop(scorePane);

    gameGrid = new GameGrid(this, fieldSize, COLUMNS, ROWS);
    backgroundPane.setCenter(gameGrid);

    nextBlockContainer = new NextBlockContainer(fieldSize, 3);
    backgroundPane.setBottom(nextBlockContainer);

    for (int i = 0; i < 3; i++) {
      cycleInNewRandomItem();
    }
  }

  private void initializeHighScoreListener() {
    highScore.addListener(observable -> {
      updateHighScoreWith(((SimpleIntegerProperty) observable).get());
    });
  }

  private void initializeHighScore() {
    HighScore previousHighScore = highScoreManager.getHighScore();
    if (previousHighScore != null) {
      highScore.set(previousHighScore.getScore().intValue());
    }
  }

  private void updateHighScoreWith(int currentScore) {
    HighScore highScore = highScoreManager.getHighScore();
    if (highScore == null || highScore.getScore().intValue() < currentScore) {
      highScoreManager.setHightScore(HighScore.createFrom("", BigInteger.valueOf(currentScore)));
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
//    soundManager.play(dropSound);
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
//                  soundManager.play(resourceUtil.getUrlFrom(specialPoint.getSoundEffectFileName()));
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
    if (!gameGrid.isSpaceLeftFor(nextBlockContainer.getFirst())) {
      displayOverlayText("You've lost!");
      deactivateNextBlockContainer();
    }
  }

  private void deactivateNextBlockContainer() {
    nextBlockContainer.deactivate();
  }
}
