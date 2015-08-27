package com.inventage.experiments.alternative1010.gameboard;

import com.inventage.experiments.alternative1010.gameboard.piece.FiveLongBlock;
import com.inventage.experiments.alternative1010.gameboard.piece.FourBlock;
import com.inventage.experiments.alternative1010.gameboard.piece.SingleBlock;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;

/**
 * Created by nw on 26.08.15.
 */
public class GameBoard extends BorderPane {

  private static final String STYLE_SHEET_PATH = "styles.css";

  private BorderPane scorePane;
  private SimpleStringProperty score = new SimpleStringProperty("0");
  private GameGrid gameGrid;
  private GraphicsContext gc;
  private BorderPane nextBlocksPane;

  public GameBoard(double width, double height) {
    initializeGameBoardWith(width, height);
  }

  private void initializeGameBoardWith(double width, double height) {
//    URL resource = getClass().getResource(STYLE_SHEET_PATH);
//    getStylesheets().add(resource.toExternalForm());

    scorePane = new BorderPane();
    scorePane.setPrefHeight(80);
    scorePane.setCenter(createScoreLabel());
    setTop(scorePane);

    gameGrid = new GameGrid(10, 10);
    setCenter(gameGrid);

    nextBlocksPane = new BorderPane();
    nextBlocksPane.setPrefHeight(280);
    setBottom(nextBlocksPane);

    nextBlocksPane.setCenter(new SingleBlock());
    nextBlocksPane.setLeft(new FourBlock());
    nextBlocksPane.setRight(new FiveLongBlock());

    setPadding(new Insets(10, 10, 10, 10));
  }

  private Label createScoreLabel() {
    Label scoreLabel = new Label();
    scoreLabel.setId("score");
    scoreLabel.textProperty().bind(score);
    return scoreLabel;
  }

}
