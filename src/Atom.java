import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Atom extends Circle {
  private static final int RADIUS = 13;
  public COI coi;

  public Atom(double centerX, double centerY) {
    super(centerX, centerY, RADIUS);
    setFill(Color.RED);
  }

  // public int correctAngle(double theta) { return (int)theta; }

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