package com.inventage.experiments.tentris;

import com.inventage.experiments.tentris.gameboard.GameBoard;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Main application file.
 */
public class TenTris extends Application {

  GameBoard gameBoard;

  @Override
  public void start(Stage primaryStage) throws Exception {

    if (isARMDevice()) {
      primaryStage.setFullScreen(true);
      primaryStage.setFullScreenExitHint("");
    }

    double fieldSize = calculateFieldSizeFromScreenSize();
    gameBoard = new GameBoard(fieldSize);

    primaryStage.setTitle("10Tris");
    primaryStage.setScene(createScene());
    primaryStage.show();
  }

  private double calculateFieldSizeFromScreenSize() {
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    return Math.floor(Math.min(visualBounds.getHeight()/22, visualBounds.getWidth()/16));
  }

  private boolean isARMDevice() {
    return System.getProperty("os.arch").toUpperCase().contains("ARM");
  }

  private Scene createScene() {
    Pane root = new Pane();
    root.getChildren().add(gameBoard);
    Scene scene = new Scene(root);
    registerExitListenerIn(scene);

    if (Platform.isSupported(ConditionalFeature.INPUT_TOUCH)) {
      scene.setCursor(Cursor.NONE);
    }

    return scene;
  }

  private void registerExitListenerIn(Scene scene) {
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
