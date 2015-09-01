package com.inventage.experiments.alternative1010.gameboard;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static com.inventage.experiments.alternative1010.gameboard.piece.ColorPalette.GAMEGRID_PIECE_INACTIVE;

/**
 * Models a single square field.
 */
public class Field extends Rectangle {

  public Field(int size, int x, int y) {
    setX(x);
    setY(y);
    setWidth(size);
    setHeight(size);
    setArcWidth(size/8);
    setArcHeight(size/8);
    setFill(GAMEGRID_PIECE_INACTIVE);
  }

  public void setColor(Paint value) {
    setFill(value);
  }

}
