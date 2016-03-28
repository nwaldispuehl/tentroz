package ch.retorte.tentroz.gameboard.piece;

import ch.retorte.tentroz.gameboard.Field;

import static ch.retorte.tentroz.gameboard.piece.ColorPalette.SINGLE;

/**
 * The 1 x 1 block.
 */
public class SingleBlock extends DraggablePiece {

  {
    add(new Field(getFieldSize(), 0, 0));
  }

  public SingleBlock(Double fieldSize) {
    super(fieldSize, SINGLE);
  }
}
