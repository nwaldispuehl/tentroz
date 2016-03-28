package ch.retorte.tentroz.platform;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Collects data about the platform the application is run on.
 */
public class PlatformInformation {

  public static boolean isTouchEnabled() {
    return Platform.isSupported(ConditionalFeature.INPUT_TOUCH);
  }

  public static boolean isARMDevice() {
    return System.getProperty("os.arch").toUpperCase().contains("ARM");
  }

  public static double getViewportWidth() {
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    return visualBounds.getWidth();
  }

  public static double getViewportHeight() {
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    return visualBounds.getHeight();
  }
}
