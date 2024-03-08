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
  private static final int HEIGHT = 650;
  private static final int WIDTH = 650;
  public static final int MAX_ATOMS = 6;

  /**
   * Player classes
   */
  // private static Setter setter = new Setter();
  // private static Experimenter experimenter = new Experimenter();

  /**
   * Display classes
   */
  private static Group group = new Group();
  public static ArrayList<Atom> atoms = new ArrayList<>();
  public static ArrayList<Torch> torchs = new ArrayList<>();

  /**
   * JavaFX start function
   */
  @Override
  public void start(Stage primaryStage) {
    /**
     * JavaFX display objects
     */
    StackPane root = new StackPane();
    root.setStyle("-fx-background-color: black;");

    Button startBtn = new Button("Play");
    startBtn.setOnAction(event -> {
      System.out.println("Play");
      ingame(primaryStage, root);
      for (int i = 0; i < 2; i++)
        root.getChildren().remove(0);
      System.out.println("Menu hidden");
    });
    startBtn.getStyleClass().add("button");
    startBtn.setScaleZ(200);
    root.getChildren().add(startBtn);
    StackPane.setAlignment(startBtn, Pos.CENTER);

    Label menuTitle = new Label("BlackBox+");
    menuTitle.setStyle("-fx-text-fill: white; -fx-font-size: 100;");
    root.getChildren().add(menuTitle);
    StackPane.setAlignment(menuTitle, Pos.TOP_CENTER);
    StackPane.setMargin(menuTitle, new Insets(70, 0, 0, 0));
    System.out.println("Title created");

    Scene scene = new Scene(root, HEIGHT, WIDTH);
    scene.getStylesheets().add(
        getClass().getResource("styles.css").toExternalForm());

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
  }

  public static Group getGroup() { return group; }

  /**
   * Conventional main function
   */
  public static void main(String[] args) { launch(args); }
}
