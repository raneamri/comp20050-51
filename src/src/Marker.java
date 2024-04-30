package blackbox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

/**
 * The Marker is a simple visual indicator for Experimenter guesses.
 */
public class Marker {
  private final int RADIUS = 9;
  private Circle interactable;

  public Marker(Pair<Double, Double> coords) {
    interactable = new Circle(coords.getKey(), coords.getValue(), RADIUS);

    interactable.setFill(Color.BLUE);
    interactable.setStroke(Color.BLUEVIOLET);
    interactable.setStrokeWidth(3);
  }

  public Circle getInteractable() { return this.interactable; }
}
