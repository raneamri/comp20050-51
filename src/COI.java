import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class COI extends Circle {
  private static final double RADIUS = 1.73f * 50;

  public COI(double centerX, double centerY) {
    super(centerX, centerY, RADIUS);
    setFill(Color.TRANSPARENT);
    setStroke(Color.WHITE);
    setMouseTransparent(true);
  }
}
