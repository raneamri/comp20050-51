package blackbox;

import javafx.scene.paint.Color;

/**
 * The Experimenter class keeps track of its own advancements, as well as the
 * simple logic behind displaying wins, losses and score.
 */
public class Experimenter {
  private int score = 0;
  private int absorptions = 0;

  public Experimenter() {}

  public void addScore(int s) { this.score += s; }

  public void subScore(int s) { score = Math.max(0, score - s); }

  public int getScore() { return this.score; }

  public void setAbsorptions(int a) { this.absorptions = a; }

  private String absorptionsToString() {
    return "Absorptions: " + Integer.toString(absorptions);
  }

  public String getToString() { return absorptionsToString(); }

  private String scoreToString() {
    return "Score: " + Integer.toString(score) +
        ((score == 10) ? " Perfect!" : "");
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
