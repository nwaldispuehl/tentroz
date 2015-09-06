package com.inventage.experiments.tentris.gameboard;

/**
 * Enumeration of additional points.
 */
public enum SpecialPoint {

  COMPLETELY_EMPTY(50, null),
  TWO_ROWS_COMPLETE(5, null),
  THREE_ROWS_COMPLETE(10, null),
  FOUR_ROWS_COMPLETE(20, null),
  FIVE_ROWS_COMPLETE(40, null);

  private int points;
  private String soundEffectFileName;

  SpecialPoint(int points, String soundEffectFileName) {
    this.points = points;
    this.soundEffectFileName = soundEffectFileName;
  }

  public int points() {
    return points;
  }

  public String getSoundEffectFileName() {
    return soundEffectFileName;
  }

  public static SpecialPoint getForRows(int rows) {
    switch (rows) {
      case 2:
        return TWO_ROWS_COMPLETE;
      case 3:
        return THREE_ROWS_COMPLETE;
      case 4:
        return FOUR_ROWS_COMPLETE;
      case 5:
        return FIVE_ROWS_COMPLETE;
      default:
        return null;
    }

  }
}
