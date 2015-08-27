package com.inventage.experiments.alternative1010;

import com.inventage.experiments.alternative1010.gameboard.GameBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main application file.
 */
public class Alternative1010 extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Alternative 1010");
    primaryStage.setScene(createScene());
    primaryStage.show();
  }

  private Scene createScene() {
    Pane root = new Pane();
    root.getChildren().add(new GameBoard(920, 800));
    Scene scene = new Scene(root);
    return scene;
  }

  public static final void main(String[] args) {
    launch(args);
  }

}
