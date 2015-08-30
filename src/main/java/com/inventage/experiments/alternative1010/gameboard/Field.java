package com.inventage.experiments.alternative1010.gameboard;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Models a single square field.
 */
public class Field extends Rectangle {

  public static final String DEFAULT_COLOR = "#dddddd";

  public Field(int size, int x, int y) {
    setX(x);
    setY(y);
    setWidth(size);
    setHeight(size);
    setArcWidth(size/8);
    setArcHeight(size/8);
    setFill(Color.web(DEFAULT_COLOR));
  }

  public void setColor(Paint value) {
    setFill(value);
  }
}
