package com.inventage.experiments.tentris.gameboard;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static com.inventage.experiments.tentris.gameboard.piece.ColorPalette.GAMEGRID_PIECE_INACTIVE;

/**
 * Models a single square field.
 */
public class Field extends Rectangle {

  private boolean garbage = false;

  public Field(double size, double x, double y) {
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

  public void markAsGarbage() {
    garbage = true;
  }

  public boolean isGarbage() {
    return garbage;
  }
}
