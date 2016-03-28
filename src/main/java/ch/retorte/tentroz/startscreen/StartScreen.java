package ch.retorte.tentroz.startscreen;

import ch.retorte.tentroz.ActionListener;
import ch.retorte.tentroz.TenTroz;
import ch.retorte.tentroz.gameboard.sound.ResourceUtil;
import ch.retorte.tentroz.highscore.HighScore;
import ch.retorte.tentroz.highscore.HighScoreManager;
import ch.retorte.tentroz.platform.PlatformInformation;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static ch.retorte.tentroz.platform.PlatformInformation.getViewportHeight;
import static ch.retorte.tentroz.platform.PlatformInformation.getViewportWidth;

/**
 * The start screen holds the title, highscore and game controls.
 */
public class StartScreen extends VBox {

  //---- Static

  private static final double ELEMENT_SPACING = 20;


  //---- Fields

  private ResourceUtil resourceUtil = new ResourceUtil();

  private HighScoreManager highScoreManager = HighScoreManager.getManager();


  //---- Constructor

  public StartScreen(ActionListener startGameAction) {
    super(ELEMENT_SPACING);
    setMaxWidth(PlatformInformation.getViewportWidth());

    loadStylesheet();
    setPadding(new Insets(20));
    addCentered(createTitleIcon());
    addCentered(createTitle());
    addCentered(createSubTitle());
    addCentered(createHighScoreList());
    addCentered(createStartGameButtonWith(startGameAction));
  }


  //---- Methods

  private void loadStylesheet() {
    getStylesheets().add(getClass().getClassLoader().getResource(TenTroz.STYLE_SHEET_PATH).toExternalForm());
  }

  private Node createTitleIcon() {
    ImageView titleImage = new ImageView(resourceUtil.getUrlFrom(TenTroz.APPLICATION_ICON_256).toString());

    titleImage.maxWidth((getViewportWidth()/4));
    titleImage.maxHeight((getViewportHeight()/4));

    titleImage.setId("titleImage");
    return titleImage;
  }

  private Node createTitle() {
    Text title = new Text(TenTroz.APPLICATION_TITLE);
    title.setId("applicationTitle");
    return title;
  }

  private Node createSubTitle() {
    Text subTitle = new Text("A well-known block game\nand 1010 clone-mashup.");
    subTitle.setId("applicationSubTitle");
    return subTitle;
  }

  private Node createHighScoreList() {
    HighScore currentHighScore = highScoreManager.getHighScore();
    if (currentHighScore != null) {
      return new Text("Current high score: " + currentHighScore.getScore());
    }

    return null;
  }

  private Node createStartGameButtonWith(ActionListener startGameAction) {
    Button startButton = new Button("Start game");
    startButton.setId("startButton");

    startButton.setOnMouseClicked(event -> {
      startGameAction.onAction();
    });

    return startButton;
  }

  private void addCentered(Node node) {
    getChildren().add(new BorderPane(node));
  }

}
