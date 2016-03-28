package ch.retorte.tentroz.gameboard;

/**
 * A tuple.
 */
public class Tuple<U, V> {

  public U first;
  public V second;

  public Tuple(U first, V second) {
    this.first = first;
    this.second = second;
  }

}
