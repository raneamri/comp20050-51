import javafx.scene.paint.Color;

public class Experimenter {
  private int score = 0;
  private int absorptions = 0;

  Experimenter() {}

  public void setScore(int s) { this.score = s; }
  public int getScore() { return this.score; }
  public void setAbsorptions(int a) { this.absorptions = a; }

  private String absorptionsToString() {
    return "Absorptions: " + Integer.toString(absorptions);
  }

  public String getToString(){
    return absorptionsToString();
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
}
