package com.inventage.experiments.alternative1010.gameboard;

import com.inventage.experiments.alternative1010.gameboard.piece.DraggablePiece;

/**
 * Created by nw on 27.08.15.
 */
public interface PieceProvider {

  DraggablePiece nextRandomPiece() throws Exception;
}
