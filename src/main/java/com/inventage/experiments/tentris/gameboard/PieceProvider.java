package com.inventage.experiments.tentris.gameboard;

import com.inventage.experiments.tentris.gameboard.piece.DraggablePiece;

/**
 * Created by nw on 27.08.15.
 */
public interface PieceProvider {

  DraggablePiece nextRandomPiece() throws Exception;
}
