package ch.retorte.tentroz.gameboard.sound;

import java.net.URL;

public class ResourceUtil {

  public URL getUrlFrom(String resourceFileName) {
      return getClass().getClassLoader().getResource(resourceFileName);
  }
}
