import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Entry point/launcher for the game. Includes all logic for the menu.
 */
public class Main extends Application {
  /**
   * Constant variables
   */
  private final int HEIGHT = 256 * 4;
  private final int WIDTH = 256 * 4;
  public static final int MAX_ATOMS = 5;
  public static final int MAX_RAYS = 6;
  public static final int MAX_MARKERS = 8;

  /**
   * Player classes
   */
  private static Setter setter;
  private static Experimenter experimenter;

  /**
   * Game assets
   */
  private static Group group = new Group();
  public static ArrayList<Atom> atoms = new ArrayList<>();
  public static ArrayList<Torch> torchs = new ArrayList<>();
  public static ArrayList<Ray> rays = new ArrayList<>();
  public static ArrayList<Flag> flags = new ArrayList<>();
  public static ArrayList<Marker> markers = new ArrayList<>();
  public static Text absorptionsDisplay = new Text();
  public static Text scoreDisplay = new Text();
  public static Button replayBtn = new Button("Replay");

  /**
   * Conventional main function
   */
  public static void main(String[] args) { launch(args); }

  /**
   * JavaFX start function
   */
  @Override
  public void start(Stage primaryStage) {
    /**
     * Menu display objects
     */
    StackPane root = new StackPane();
    Button startBtn = new Button("Play");
    Button instructBtn = new Button("Instructions");
    Button exitBtn = new Button("X");
    Button nextBtn = new Button("next");
    Label menuTitle = new Label("BlackBox+");
    Scene scene = new Scene(root, HEIGHT, WIDTH);
    DropShadow dropShadow = new DropShadow();
    AtomicBoolean nextBtnPressed = new AtomicBoolean(false);

    /**
     * Style elements
     */
    root.setStyle("-fx-background-color: black;");
    /*  String path = "C:/Users/pinto/Downloads/atom.mp4";
      Media media = new Media(new File(path).toURI().toString());
      MediaPlayer mediaPlayer = new MediaPlayer(media);
      MediaView mediaView = new MediaView(mediaPlayer);

      mediaView.setFitWidth(800);
      mediaView.setFitHeight(600);

      mediaPlayer.play();
    */

    dropShadow.setColor(Color.WHITE);
    dropShadow.setOffsetX(3);
    dropShadow.setOffsetY(3);
    menuTitle.setEffect(dropShadow);
    scene.getStylesheets().add(
        getClass().getResource("styles.css").toExternalForm());
    startBtn.getStyleClass().add("button");
    instructBtn.getStyleClass().add("button");
    replayBtn.getStyleClass().add("button");
    menuTitle.setFont(Font.font("Verdana", 100));
    menuTitle.setStyle("-fx-text-fill: white");
    menuTitle.setOpacity(0);
    startBtn.setOpacity(0);
    instructBtn.setOpacity(0);
    absorptionsDisplay.setFont(Font.font("Arial", 20));
    absorptionsDisplay.setStyle("-fx-font-weight: bold");
    scoreDisplay.setFont(Font.font("Arial", 30));
    scoreDisplay.setStyle("-fx-font-weight: bold");
    System.out.println("Stylesheet fetched successfully");

    /**
     * Transitions
     */
    FadeTransition titleFadeIn =
        new FadeTransition(Duration.seconds(6), menuTitle);
    titleFadeIn.setFromValue(0.0);
    titleFadeIn.setToValue(1.0);
    titleFadeIn.play();

    FadeTransition buttonFadeIn =
        new FadeTransition(Duration.seconds(5), startBtn);
    buttonFadeIn.setFromValue(0.0);
    buttonFadeIn.setToValue(1.0);
    buttonFadeIn.play();

    FadeTransition instructFadeIn =
        new FadeTransition(Duration.seconds(5), instructBtn);
    instructFadeIn.setFromValue(0.0);
    instructFadeIn.setToValue(1.0);
    instructFadeIn.play();

    /**
     * Text elements
     */
    Text text = new Text("Setter Instructions");
    text.setFont(
        Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
    text.setFill(Color.BLACK);

    Text text2 = new Text("Experimenter Instructions");
    text2.setFont(
        Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
    text2.setFill(Color.BLACK);

    Text setter = new Text(
        "\n\nSet up four 'atoms' by pressing a hexagon on the board.\n\n"
        + "Secretly work out ray path and announce to the experimenter\nthe "
        + "outcome of the ray.\n");
    setter.setFont(
        Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
    setter.setFill(Color.BLACK);

    Text experimenter = new Text(
        "\n\nDeduce position of atoms by sending in 'rays'.\n\n"
        + "Send a ray by pressing the triangle on the edge of the board.\n"
        + "\nWhen you believe you have located all the atoms, announce "
        + "the\nend of the round.");
    experimenter.setFont(
        Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
    experimenter.setFill(Color.BLACK);

    Rectangle background = new Rectangle(500, 300);
    background.setFill(Color.WHITE);
    background.setOpacity(0.9);

    /**
     * Button actions
     */
    startBtn.setOnAction(event -> {
      for (int i = 0; i < 3; i++)
        root.getChildren().remove(0);

      ingame(primaryStage, root);
    });
    startBtn.setScaleX(1.5);
    startBtn.setScaleY(1.5);

    instructBtn.setOnAction(event -> {
      root.getChildren().add(background);
      root.getChildren().add(text);
      root.getChildren().add(setter);
      root.getChildren().add(exitBtn);
      root.getChildren().add(nextBtn);
    });

    nextBtn.setOnAction(event -> {
      root.getChildren().remove(text);
      root.getChildren().remove(setter);
      root.getChildren().remove(nextBtn);
      root.getChildren().add(text2);
      root.getChildren().add(experimenter);
      nextBtnPressed.set(true);
    });

    exitBtn.setOnAction(event -> {
      if (nextBtnPressed.get()) {
        root.getChildren().remove(experimenter);
        root.getChildren().remove(text2);
      } else if (!nextBtnPressed.get()) {
        root.getChildren().remove(text);
        root.getChildren().remove(setter);
        root.getChildren().remove(nextBtn);
      }
      root.getChildren().remove(background);
      root.getChildren().remove(exitBtn);
      nextBtnPressed.set(false);
    });

    replayBtn.setOnAction(event -> {
      root.getChildren().clear();
      root.getChildren().add(absorptionsDisplay);
      root.getChildren().add(scoreDisplay);
      root.getChildren().add(replayBtn);

      scoreDisplay.setFill(Color.TRANSPARENT);
      replayBtn.setVisible(false);
      replayBtn.setDisable(true);

      clearAssets();

      ingame(primaryStage, root);
    });
    replayBtn.setScaleZ(200);
    replayBtn.setVisible(false);
    replayBtn.setDisable(true);

    /**
     * Alignments
     */
    StackPane.setAlignment(startBtn, Pos.CENTER);
    StackPane.setAlignment(menuTitle, Pos.TOP_CENTER);
    StackPane.setMargin(instructBtn, new Insets(120, 0, 0, 0));
    StackPane.setMargin(menuTitle, new Insets(70, 0, 0, 0));
    StackPane.setMargin(exitBtn, new Insets(-250, -400, 0, 0));
    StackPane.setMargin(text, new Insets(-230, 0, 0, 0));
    StackPane.setMargin(text2, new Insets(-230, 0, 0, 0));
    StackPane.setMargin(setter, new Insets(-100, -20, 0, 0));
    StackPane.setMargin(experimenter, new Insets(-100, -15, 0, 0));
    StackPane.setMargin(nextBtn, new Insets(250, 400, 0, 0));
    StackPane.setMargin(absorptionsDisplay, new Insets(-10, 826, 0, 0));
    StackPane.setMargin(scoreDisplay, new Insets(-800, 0, 0, 0));
    StackPane.setMargin(replayBtn, new Insets(-725, 0, 0, 0));

    /**
     * Adding to root
     */
    root.getChildren().add(startBtn);
    root.getChildren().add(instructBtn);
    root.getChildren().add(menuTitle);
    // root.getChildren().add(mediaView);
    root.getChildren().add(absorptionsDisplay);
    root.getChildren().add(scoreDisplay);
    root.getChildren().add(replayBtn);

    primaryStage.setFullScreen(true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("BlackBox+");
    primaryStage.show();
  }

  private static void ingame(Stage primaryStage, StackPane root) {
    /*
     * Set up board
     */
    Board board = new Board();

    group = board.getBoardGroup();
    root.getChildren().add(getGroup());

    experimenter = new Experimenter();
    setter = new Setter();
  }

  public static Group getGroup() { return group; }
  public static Setter getSetter() { return setter; }
  public static Experimenter getExperimenter() { return experimenter; }

  /**
   * Resets all assets created by the game, useful for replay
   */
  private void clearAssets() {
    group = new Group();

    atoms.clear();
    torchs.clear();
    rays.clear();
    flags.clear();
    markers.clear();
  }
}
