package ch.retorte.tentroz;

import ch.retorte.tentroz.gameboard.GameBoard;
import ch.retorte.tentroz.gameboard.sound.ResourceUtil;
import ch.retorte.tentroz.platform.PlatformInformation;
import ch.retorte.tentroz.startscreen.StartScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static ch.retorte.tentroz.platform.PlatformInformation.*;

/**
 * Main application file.
 */
public class TenTroz extends Application {

  //---- Static

  public static final String STYLE_SHEET_PATH = "styles.css";

  public static final String APPLICATION_TITLE = "TenTroz";

  public static final String APPLICATION_ICON_512 = "tentroz_512x512.png";
  public static final String APPLICATION_ICON_256 = "tentroz_256x256.png";
  public static final String APPLICATION_ICON_128 = "tentroz_128x128.png";
  public static final String APPLICATION_ICON_64 = "tentroz_64x64.png";


  //---- Fields

  private Scene mainScene;
  private BorderPane rootPane;

  private double fieldSize;
  private GameBoard gameBoard;

  private ResourceUtil resourceUtil = new ResourceUtil();


  //---- Methods

  @Override
  public void start(Stage primaryStage) throws Exception {
    setApplicationIconsIn(primaryStage);
    setApplicationTitleIn(primaryStage);

    if (isARMDevice()) {
      setToFullscreen(primaryStage);
    }

    createMainSceneIn(primaryStage);
    calculateFieldSizeFromScreenSize();

    createStartScreen();

    primaryStage.show();
  }

  public void createStartScreen() {
    rootPane.setCenter(new StartScreen(() -> startNewGame()));
  }



  private void setApplicationIconsIn(Stage stage) {
    stage.getIcons().add(new Image(resourceUtil.getUrlFrom(APPLICATION_ICON_512).toString()));
    stage.getIcons().add(new Image(resourceUtil.getUrlFrom(APPLICATION_ICON_256).toString()));
    stage.getIcons().add(new Image(resourceUtil.getUrlFrom(APPLICATION_ICON_128).toString()));
    stage.getIcons().add(new Image(resourceUtil.getUrlFrom(APPLICATION_ICON_64).toString()));
  }

  private void setApplicationTitleIn(Stage primaryStage) {
    primaryStage.setTitle(APPLICATION_TITLE);
  }

  private void setToFullscreen(Stage primaryStage) {
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
  }

  private void calculateFieldSizeFromScreenSize() {
    fieldSize = Math.floor(Math.min(getViewportHeight()/22, getViewportWidth()/16));
  }

  private void createMainSceneIn(Stage primaryStage) {
    rootPane = new BorderPane();

    mainScene = new Scene(rootPane);
    registerExitListenerIn(mainScene);

    if (isTouchEnabled()) {
      mainScene.setCursor(Cursor.NONE);
    }

    primaryStage.setScene(mainScene);
  }

  public void startNewGame() {
    gameBoard = new GameBoard(fieldSize, () -> createStartScreen());
    rootPane.setCenter(gameBoard);

    rootPane.setPrefHeight(PlatformInformation.getViewportHeight());
    mainScene.getWindow().sizeToScene();
  }





  private void registerExitListenerIn(Scene scene) {
    Platform.setImplicitExit(true);
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });
  }

  public static final void main(String[] args) {
    launch(args);
  }



}
