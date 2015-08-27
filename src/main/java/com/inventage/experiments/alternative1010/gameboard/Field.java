package com.inventage.experiments.alternative1010.gameboard;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Created by nw on 26.08.15.
 */
public class Field extends Rectangle {

  public Field(int size, int x, int y) {
    setX(x);
    setY(y);
    setWidth(size);
    setHeight(size);
    setArcWidth(size/8);
    setArcHeight(size/8);
    setFill(Color.LIGHTGRAY);
  }

  public void setColor(Paint value) {
    setFill(value);
  }
}
