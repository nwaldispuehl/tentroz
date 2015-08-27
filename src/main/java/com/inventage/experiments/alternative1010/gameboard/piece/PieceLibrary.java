package com.inventage.experiments.alternative1010.gameboard.piece;

import com.google.common.collect.Lists;
import com.inventage.experiments.alternative1010.gameboard.PieceProvider;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Random;

/**
 * Holds all available pieces and retrieves them randomly.
 */
public class PieceLibrary implements PieceProvider {

  private int[] rotationAngles = new int[] {0, 90, 180, 270};

  private List<Class<? extends DraggablePiece>> availablePieces = Lists.newArrayList();

  {
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

  public PieceLibrary() {
    random = new Random();
  }

  public PieceLibrary(long seed) {
    random = new Random(seed);
  }

  @Override
  public DraggablePiece nextRandomPiece() {

    DraggablePiece piece = null;
    try {
      piece = getRandomPiece();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    piece.setRotate(rotationAngles[random.nextInt(rotationAngles.length)]);
    System.out.println(piece);
    return piece;
  }

  private DraggablePiece getRandomPiece() throws Exception {
    Class<? extends DraggablePiece> randomPieceClass = availablePieces.get(random.nextInt(availablePieces.size()));
    DraggablePiece result = createInstanceOf(randomPieceClass);
    return result;
  }

  private DraggablePiece createInstanceOf(Class c) throws Exception {
    Constructor<?> constructor = c.getConstructor();
    return (DraggablePiece) constructor.newInstance();
  }
}
