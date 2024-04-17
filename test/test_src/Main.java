package test_src;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Entry point/launcher for the game. Includes all logic for the menu.
 */
public class Main extends Application {
  /*
   * Enums and Constant variables
   */
  public static final int MAX_ATOMS = 5;
  public enum GameStage { SETTER, RAYS, MARKERS, SCORE }
  public static GameStage gameStage;

  /*
   * Player classes
   */
  private static Setter setter;
  public static Experimenter experimenter;

  /*
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
  // public static Button replayBtn = new Button("Replay");
  //public Label gameStageInstruct = new Label();
  public static Text player = new Text("Setter");
  public static Label cellLabel;

  /**
   * Conventional main function
   */
  public static void main(String[] args) { launch(args); }

  /**
   * JavaFX start function
   */
  @Override
  public void start(Stage primaryStage) {
    /*
     * Menu display objects
     */
    StackPane root = new StackPane();
    Button startBtn = new Button("Play");
    Button instructBtn = new Button("Instructions");
    Button exitBtn = new Button("X");
    Button nextBtn = new Button("next");
    Label menuTitle = new Label("BlackBox+");
    Scene scene = new Scene(root);
    DropShadow dropShadow = new DropShadow();
    AtomicBoolean nextBtnPressed = new AtomicBoolean(false);

    /*
     * Style elements
     */
    root.setStyle("-fx-background-color: black;");
    dropShadow.setColor(Color.BLACK);
    dropShadow.setOffsetX(3);
    dropShadow.setOffsetY(3);
    menuTitle.setEffect(dropShadow);
    scene.getStylesheets().add(
            getClass().getResource("styles.css").toExternalForm());
    startBtn.getStyleClass().add("button");
    instructBtn.getStyleClass().add("button");
    menuTitle.setFont(Font.font("Verdana", 100));
    menuTitle.setStyle("-fx-text-fill: white");
    menuTitle.setOpacity(0);
    startBtn.setOpacity(0);
    instructBtn.setOpacity(0);
    absorptionsDisplay.setFont(Font.font("Arial", 20));
    absorptionsDisplay.setStyle("-fx-font-weight: bold");
    scoreDisplay.setFont(Font.font("Arial", 30));
    scoreDisplay.setStyle("-fx-font-weight: bold");
    player.setFont(Font.font("Arial", 30));
    player.setStyle("-fx-font-weight: bold");
    player.setFill(Color.RED);
    primaryStage.setFullScreenExitHint("");
    System.out.println("Stylesheet fetched successfully");

    /*
     * Image
     */
    Image image = new Image(getClass().getResourceAsStream("atom.jpg"));
    ImageView imageView = new ImageView(image);

    /*
     * Transitions
     */
    ScaleTransition scaleTransition =
            new ScaleTransition(Duration.seconds(5), imageView);
    scaleTransition.setToX(0);
    scaleTransition.setToY(0);
    scaleTransition.play();

    FadeTransition titleFadeIn =
            new FadeTransition(Duration.seconds(6), menuTitle);
    titleFadeIn.setFromValue(0.0);
    titleFadeIn.setToValue(1.0);

    FadeTransition buttonFadeIn =
            new FadeTransition(Duration.seconds(5), startBtn);
    buttonFadeIn.setFromValue(0.0);
    buttonFadeIn.setToValue(1.0);

    FadeTransition instructFadeIn =
            new FadeTransition(Duration.seconds(5), instructBtn);
    instructFadeIn.setFromValue(0.0);
    instructFadeIn.setToValue(1.0);

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

    Text setter =
            new Text("\n\nSet up five 'atoms' by clicking cells on the board.\n\n");
    setter.setFont(
            Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
    setter.setFill(Color.BLACK);

    Text experimenter = new Text(
            "\n\n\n\n\n\n\tDeduce position of atoms by sending in rays.\n\n\t"
                    + "Send a ray by pressing the torchs (triangles) on the\n\tedge of the "
                    + "board.\n\t"
                    + "\n\tWhen you believe you have located all the atoms, \n\tannounce "
                    + "the end of the round and place markers\n\twhere you believe the "
                    + "atoms are.\n\tScore is calculated as \n\t{CORRECT GUESSES} - "
                    + "{INCORRECT GUESSES}");
    experimenter.setFont(
            Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
    experimenter.setFill(Color.BLACK);
    experimenter.setMouseTransparent(true);

    Rectangle background = new Rectangle(500, 300);
    background.setFill(Color.WHITE);
    background.setOpacity(0.9);

    /**
     * Button actions
     */
    scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        root.getChildren().add(startBtn);
        root.getChildren().add(instructBtn);
        root.getChildren().add(menuTitle);
        root.getChildren().add(absorptionsDisplay);
        root.getChildren().add(scoreDisplay);

        titleFadeIn.play();
        buttonFadeIn.play();
        instructFadeIn.play();
      }
    });

    startBtn.setOnAction(event -> {
      for (int i = 0; i < 4; i++)
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
    StackPane.setMargin(scoreDisplay, new Insets(-700, 0, 0, 0));
    StackPane.setMargin(player, new Insets(-625, 0, 0, 0));
    //StackPane.setAlignment(gameStageInstruct, Pos.CENTER);

    /**
     * Adding to root
     */
    root.getChildren().add(imageView);

    primaryStage.setFullScreen(true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("BlackBox+");
    primaryStage.show();
  }

  private void ingame(Stage primaryStage, StackPane root) {
    /*
     * Set up board
     */
    Board board = new Board();
    group = board.getBoardGroup();
    StackPane.setMargin(group, new Insets(75, 0, 0, 0));
    root.getChildren().add(getGroup());

    /*
     * Set up game stage and initial player
     */
    Main.gameStage = GameStage.SETTER;
    root.getChildren().add(player);
    player.setText("SETTER");
    player.setVisible(true);

    /*
     * Write stage instructions
     */
    //root.getChildren().add(gameStageInstruct);
    root.getChildren().add(statusInstruct("\tSetter's Turn\n\tPlace 5 atoms"));

    experimenter = new Experimenter();
    setter = new Setter();

    /*
     * Create button to end player turn
     * and switch stages
     */
    Button endTurnBtn = new Button("End Turn");
    endTurnBtn.getStyleClass().add("button");
    StackPane.setMargin(endTurnBtn, new Insets(550, 0, 0, 700));
    root.getChildren().add(endTurnBtn);

    endTurnBtn.setOnMouseClicked(event -> {

      if(Main.gameStage == GameStage.RAYS){

        Main.gameStage = GameStage.MARKERS;
        Label label = statusInstruct("\tPlace markers to guess");
        root.getChildren().add(label);

        for(Torch t : Main.torchs){
          t.getInteractable().setOnMouseEntered(null);
          t.getInteractable().setOnMouseClicked(null);
        }
      }
      else if(Main.gameStage == GameStage.MARKERS){
        Main.gameStage = GameStage.SCORE;
        player.setVisible(false);

        experimenter.showScore();

        Button replayBtn = new Button("Replay");
        replayBtn.getStyleClass().add("button");
        replayBtn.setScaleZ(200);
        StackPane.setMargin(replayBtn, new Insets(-600, 0, 0, 0));

        replayBtn.setOnAction(newevent -> {
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
        root.getChildren().add(replayBtn);

        experimenter.hideAbsorptions();

        for (Atom a : Main.atoms) {
          a.toggleOn();
        }
        for (Ray r : Main.rays) {
          r.toggleOn();
        }
        for (Flag f : Main.flags) {
          f.toggleOff();
        }
      }
      else {
      }

    });
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

  /**
   * Writes game stage instructions to screen
   * with fade transition
   *
   * @param message instruction message for current stage
   */
  public static Label statusInstruct(String message) {
    Label gameStageInstruct = new Label(message);
    DropShadow dropShadow = new DropShadow();
    dropShadow.setColor(Color.BLACK);
    dropShadow.setOffsetX(3);
    dropShadow.setOffsetY(3);
    gameStageInstruct.setStyle("-fx-font-weight: bold");
    gameStageInstruct.setTextAlignment(TextAlignment.CENTER);
    gameStageInstruct.setFont(Font.font("Arial", 25));
    gameStageInstruct.setWrapText(true);
    gameStageInstruct.setPrefSize(400, 100);
    gameStageInstruct.setMouseTransparent(true);
    gameStageInstruct.setBackground(new Background(
            new BackgroundFill(Color.BLACK, new CornerRadii(10), Insets.EMPTY)));
    gameStageInstruct.setEffect(dropShadow);
    StackPane.setAlignment(gameStageInstruct, Pos.CENTER);

    gameStageInstruct.setTextAlignment(TextAlignment.CENTER);
    gameStageInstruct.setText(message);
    FadeTransition fade =
            new FadeTransition(Duration.millis(3500), gameStageInstruct);
    fade.setFromValue(1.0);
    fade.setToValue(0);
    fade.play();

    return gameStageInstruct;
  }

  public void addLabel(Label f){
    //root.getChildren().add(f);
  }

}