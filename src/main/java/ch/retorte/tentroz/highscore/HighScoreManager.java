package ch.retorte.tentroz.highscore;


import com.gluonhq.charm.down.common.PlatformFactory;

import java.io.*;

/**
 * Manages the high score(s) of the game.
 */
public class HighScoreManager {

  //---- Static

  private static final String HIGH_SCORE_FILE = "/tentroz/highscore";

  private static HighScoreManager instance;

  //---- Fields

  private HighScore currentHighScore;
  private File highScorePath;

  private HighScoreManager() {
    prepareHighScorePath();
  }

  //---- Methods

  public static HighScoreManager getManager() {
    if (instance == null) {
      instance = new HighScoreManager();
    }
    return instance;
  }

  public void setHightScore(HighScore highScore) {
    currentHighScore = highScore;
    persist(highScore);
  }

  public HighScore getHighScore() {
    if (currentHighScore == null) {
      currentHighScore = retrieveHighScore();
    }
    return currentHighScore;
  }

  private void prepareHighScorePath() {
    try {
      File privateStorage = PlatformFactory.getPlatform().getPrivateStorage();
      highScorePath = new File(privateStorage, HIGH_SCORE_FILE);
      if (!highScorePath.exists()) {
        highScorePath.getParentFile().mkdirs();
        highScorePath.createNewFile();
      }
    }
    catch (IOException e) {
      throw new RuntimeException("Not able to get high score file.");
    }
  }

  private synchronized void persist(HighScore highScore) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(highScorePath);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(highScore);
      objectOutputStream.close();

    }
    catch(Exception ex){
      throw new RuntimeException("Not able to write high score.");
    }
  }

  private synchronized HighScore retrieveHighScore() {
    try {
      FileInputStream fileInputStream = new FileInputStream(highScorePath);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      Object result = objectInputStream.readObject();
      objectInputStream.close();
      return (HighScore) result;

    }
    catch(Exception ex){
      return null;
    }
  }
}
