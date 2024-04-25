import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * The COI (circle of influence) class is a visual addition to the displayed
 * atom from the Atom class, which draws a dotted line around the latter. The
 * COI class also extends the <a
 * href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Circle.html">Circle
 * class</a>.
 */
public class COI extends Circle {
  private static final double RADIUS = Math.sqrt(3) * 39;

  public COI(double centerX, double centerY) {
    super(centerX, centerY, RADIUS);
    setFill(Color.TRANSPARENT);
    setStroke(Color.color(0.9, 0.9, 0.9));
    setStrokeWidth(1);
    setStrokeType(StrokeType.OUTSIDE);
    getStrokeDashArray().addAll(2d, 12d);
    setMouseTransparent(true);
  }

  public void toggleOn() { setStroke(Color.color(0.9, 0.9, 0.9)); }

  public void toggleOff() { setStroke(Color.TRANSPARENT); }
}