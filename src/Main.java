import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
  /**
   * Constant variables
   */
  private static final int HEIGHT = 1024;
  private static final int WIDTH = 1024;
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

    Button startBtn = new Button("Start");
    startBtn.setOnAction(event -> {
      System.out.println("Start");
      ingame(primaryStage, root);
      root.getChildren().remove(0);
      root.getChildren().remove(1);
    });
    root.getChildren().add(startBtn);
    StackPane.setAlignment(startBtn, javafx.geometry.Pos.TOP_CENTER);

    Button quitBtn = new Button("Quit");
    quitBtn.setOnAction(event -> {
      System.out.println("Quit");
      System.exit(0);
    });
    root.getChildren().add(quitBtn);
    StackPane.setAlignment(quitBtn, javafx.geometry.Pos.BOTTOM_CENTER);

    Scene scene = new Scene(root, HEIGHT, WIDTH);

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
    // remove button & add board to stage
    root.getChildren().remove(0);
    root.getChildren().add(getGroup());
  }

  public static Group getGroup() { return group; }

  /**
   * Conventional main function
   */
  public static void main(String[] args) { launch(args); }
}
