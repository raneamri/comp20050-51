package test_src;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The atom is an extension of the JavaFX <a
 * href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Circle.html">Circle
 * class</a>. The only signicant part of the Atom class are its center point.
 * The rest is purely visual.
 */
public class Atom extends Circle {

  private static final int RADIUS = 13;

  public COI coi;

  public Atom(double centerX, double centerY) {
    super(centerX, centerY, RADIUS);
    setMouseTransparent(true);
    setFill(Color.RED);
  }

  public void toggleOn() {
    setFill(Color.RED);
    coi.toggleOn();
  }

  public void toggleOff() {
    setFill(Color.TRANSPARENT);
    coi.toggleOff();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null || getClass() != obj.getClass())
      return false;

    Atom other = (Atom)obj;
    return this.getCenterX() == other.getCenterX() &&
        this.getCenterY() == other.getCenterY();
  }
}