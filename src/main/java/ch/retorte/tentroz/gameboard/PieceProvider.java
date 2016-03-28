package ch.retorte.tentroz.gameboard;

import ch.retorte.tentroz.gameboard.piece.DraggablePiece;

/**
 * Created by nw on 27.08.15.
 */
public interface PieceProvider {

  DraggablePiece nextRandomPiece() throws Exception;
}
