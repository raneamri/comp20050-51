import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
  /**
   * Constant variables
   */
  private static final int HEIGHT = 1024;
  private static final int WIDTH = 1024;
  public static final int MAX_ATOMS = 6;
  public static final int MAX_RAYS = 3;

  /**
   * Player classes
   */
  private static Setter setter = new Setter();
  private static Experimenter experimenter = new Experimenter();

  /**
   * Game assets
   */
  private static Group group = new Group();
  public static ArrayList<Atom> atoms = new ArrayList<>();
  public static ArrayList<Torch> torchs = new ArrayList<>();
  public static ArrayList<Ray> rays = new ArrayList<>();
  public static ArrayList<Flag> flags = new ArrayList<>();

  /**
   * JavaFX start function
   */
  @Override
  public void start(Stage primaryStage) {
    /**
     * JavaFX display objects
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
    menuTitle.setStyle("-fx-text-fill: white; -fx-font-size: 100;");
    System.out.println("Stylesheet fetched");

    /**
     * Button actions
     */
    startBtn.setOnAction(event -> {
      System.out.println("Play");
      ingame(primaryStage, root);
      for (int i = 0; i < 2; i++)
        root.getChildren().remove(0);
      System.out.println("Menu hidden");
    });
    startBtn.setScaleZ(200);

    /**
     * Alignments
     */
    StackPane.setAlignment(startBtn, Pos.CENTER);
    StackPane.setAlignment(menuTitle, Pos.TOP_CENTER);
    StackPane.setMargin(menuTitle, new Insets(70, 0, 0, 0));

    /**
     * Adding to root
     */
    root.getChildren().add(startBtn);
    root.getChildren().add(menuTitle);

    primaryStage.setScene(scene);
    primaryStage.setTitle("BlackBox+ (51)");
    primaryStage.show();
  }

  private static void ingame(Stage primaryStage, StackPane root) {
    /*
     * Set up board
     */
    Board board = new Board();
    board.getBoardGroup();
    root.getChildren().add(getGroup());
    setter.placeAtoms();
  }

  public static Group getGroup() { return group; }
  public static Setter getSetter() { return setter; }
  public static Experimenter getExperimenter() { return experimenter; }

  /**
   * Conventional main function
   */
  public static void main(String[] args) { launch(args); }
}
