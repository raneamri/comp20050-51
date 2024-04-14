import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
  /**
   * Constant variables
   */
  private static final int HEIGHT = 256 * 4;
  private static final int WIDTH = 256 * 4;
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
    Label menuTitle = new Label("BlackBox+");
    Scene scene = new Scene(root, HEIGHT, WIDTH);

    /**
     * Style elements
     */
    root.setStyle("-fx-background-color: black;");
    scene.getStylesheets().add(
        getClass().getResource("styles.css").toExternalForm());
    startBtn.getStyleClass().add("button");
    replayBtn.getStyleClass().add("button");
    menuTitle.setFont(Font.font("Arial", 100));
    menuTitle.setStyle("-fx-text-fill: white");
    absorptionsDisplay.setFont(Font.font("Arial", 20));
    absorptionsDisplay.setStyle("-fx-font-weight: bold");
    scoreDisplay.setFont(Font.font("Arial", 30));
    scoreDisplay.setStyle("-fx-font-weight: bold");
    System.out.println("Stylesheet fetched successfully");

    /**
     * Button actions
     */
    startBtn.setOnAction(event -> {
      for (int i = 0; i < 2; i++)
        root.getChildren().remove(0);

      ingame(primaryStage, root);
    });
    startBtn.setScaleX(1.5);
    startBtn.setScaleY(1.5);

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
    StackPane.setMargin(menuTitle, new Insets(70, 0, 0, 0));
    StackPane.setMargin(absorptionsDisplay, new Insets(-10, 826, 0, 0));
    StackPane.setMargin(scoreDisplay, new Insets(-800, 0, 0, 0));
    StackPane.setMargin(replayBtn, new Insets(-725, 0, 0, 0));

    /**
     * Adding to root
     */
    root.getChildren().add(startBtn);
    root.getChildren().add(menuTitle);
    root.getChildren().add(absorptionsDisplay);
    root.getChildren().add(scoreDisplay);
    root.getChildren().add(replayBtn);

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
