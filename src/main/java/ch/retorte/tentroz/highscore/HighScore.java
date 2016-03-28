package ch.retorte.tentroz.highscore;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * High score data item.
 */
public class HighScore implements Serializable, Comparable<HighScore> {

  private String name;
  private BigInteger score;
  private Date date;

  public HighScore(String name, BigInteger score, Date date) {
    this.name = name;
    this.score = score;
    this.date = date;
  }

  public static HighScore createFrom(String name, BigInteger score) {
    return new HighScore(name, score, new Date());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigInteger getScore() {
    return score;
  }

  public void setScore(BigInteger score) {
    this.score = score;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public int compareTo(HighScore o) {
    return this.score.compareTo(o.score);
  }
}
