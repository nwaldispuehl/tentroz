package com.inventage.experiments.tentris.gameboard.sound;

import java.net.URL;

public class ResourceUtil {

  public URL getUrlFrom(String resourceFileName) {
      return getClass().getClassLoader().getResource(resourceFileName);
  }
}
