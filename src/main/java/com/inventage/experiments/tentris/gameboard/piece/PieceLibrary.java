package com.inventage.experiments.tentris.gameboard.piece;

import com.google.common.collect.Lists;
import com.inventage.experiments.tentris.gameboard.PieceProvider;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Random;

/**
 * Holds all available pieces and retrieves them randomly.
 */
public class PieceLibrary implements PieceProvider {

  private List<Class<? extends DraggablePiece>> availablePieces = Lists.newArrayList();
  private double fieldSize;

  {
    // Block
    availablePieces.add(SingleBlock.class);

    // Bars
    availablePieces.add(DoubleBar.class);
    availablePieces.add(TripleBar.class);
    availablePieces.add(QuadrupleBar.class);
    availablePieces.add(QuintupleBar.class);

    // Chunks
    availablePieces.add(DoubleChunk.class);
    availablePieces.add(TripleChunk.class);

    // Angles
    availablePieces.add(DoubleAngle.class);
    availablePieces.add(TripleAngle.class);
  }

  Random random;

  public PieceLibrary(double fieldSize) {
    this.fieldSize = fieldSize;
    random = new Random();
  }

  public PieceLibrary(double fieldSize, long seed) {
    this.fieldSize = fieldSize;
    random = new Random(seed);
  }

  @Override
  public DraggablePiece nextRandomPiece() {
    try {
      return getRandomPiece();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private DraggablePiece getRandomPiece() throws Exception {
    Class<? extends DraggablePiece> randomPieceClass = availablePieces.get(random.nextInt(availablePieces.size()));
    DraggablePiece result = createInstanceOf(randomPieceClass);
    return result;
  }

  private DraggablePiece createInstanceOf(Class c) throws Exception {
    Constructor<?> constructor = c.getConstructor(Double.class);
    return (DraggablePiece) constructor.newInstance(fieldSize);
  }
}
