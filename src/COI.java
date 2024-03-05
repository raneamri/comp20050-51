import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class COI extends Circle {
  private static final double RADIUS = 1.73f * 50;

  public COI(double centerX, double centerY) {
    super(centerX, centerY, RADIUS);
    setFill(Color.TRANSPARENT);
    setStroke(Color.WHITE);
    setStrokeWidth(2);
    setStrokeType(StrokeType.OUTSIDE);
    getStrokeDashArray().addAll(9d, 12d);
    setMouseTransparent(true);
  }
}