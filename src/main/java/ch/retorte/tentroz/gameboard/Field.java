package ch.retorte.tentroz.gameboard;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static ch.retorte.tentroz.gameboard.piece.ColorPalette.GAMEGRID_PIECE_INACTIVE;

/**
 * Models a single square field.
 */
public class Field extends Rectangle implements Cloneable {

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

  void markAsGarbage() {
    garbage = true;
  }

  boolean isGarbage() {
    return garbage;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    super.clone();
    return new Field(getWidth(), getX(), getY());
  }
}
