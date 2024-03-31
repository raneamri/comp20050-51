import javafx.scene.paint.Color;

public class Experimenter {
  private int score = 0;
  private int absorptions = 0;

  Experimenter() {}

  public void addScore(int s) { this.score++; }
  public int getScore() { return this.score; }

  private String absorptionsToString() {
    return "Absorptions: " + Integer.toString(absorptions);
  }

  private String scoreToString() {
    return "Score: " + Integer.toString(score) +
        ((score == 5) ? " Perfect!" : "");
  }

  public void addAbsorption() {
    if (absorptions == 0)
      showAbsorptions();
    absorptions++;
    Main.absorptionsDisplay.setText(absorptionsToString());
  }

  public void showAbsorptions() {
    Main.absorptionsDisplay.setFill(Color.YELLOW);
  }
  public void hideAbsorptions() {
    Main.absorptionsDisplay.setFill(Color.TRANSPARENT);
  }

  public void showScore() {
    Main.scoreDisplay.setText(scoreToString());
    Main.scoreDisplay.setFill(Color.BLUEVIOLET);
  }
  public void hideScore() { Main.scoreDisplay.setFill(Color.TRANSPARENT); }

  public void showReplay() {
    Main.replayBtn.setVisible(true);
    Main.replayBtn.setDisable(false);
  }
  public void hideReplay() {
    Main.replayBtn.setVisible(false);
    Main.replayBtn.setDisable(true);
  }
}
