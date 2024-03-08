import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class COI extends Circle {
  private static final double RADIUS = Math.sqrt(3) * 39;

  public COI(double centerX, double centerY) {
    super(centerX, centerY, RADIUS);
    setFill(Color.TRANSPARENT);
    setStroke(Color.WHITE);
    setStrokeWidth(1);
    setStrokeType(StrokeType.OUTSIDE);
    getStrokeDashArray().addAll(9d, 12d);
    setMouseTransparent(true);
  }
}