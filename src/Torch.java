import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Torch {
  private Polygon interactable;
  private Ray ray;

  private double[] mainMidpoint;

  // int i = array position of appropriate hexagon vertex
  public Torch(Cell cell, int i) {
    interactable = new Polygon();
    Polygon hex = cell.getHexagon();
    // initializing array to contain hexagon points
    ArrayList<Double> hexagonPoints = new ArrayList<>(hex.getPoints());
    double centerX = hex.getBoundsInParent().getCenterX();
    double centerY = hex.getBoundsInParent().getCenterY();

    // x and y offsets to place triangle vertices correctly on the board
    double xOffset = hex.getLayoutX();
    double yOffset = hex.getLayoutY();

    double x1 = hexagonPoints.get(i) + xOffset;
    double y1 = hexagonPoints.get(++i) + yOffset;
    if (i == 11) // go back to beginning of hexagonPoints array
      i = -1;
    double x2 = hexagonPoints.get(++i) + xOffset;
    double y2 = hexagonPoints.get(++i) + yOffset;

    // Midpoint of hexagon side
    this.mainMidpoint = cell.midPoint(x1, y1, x2, y2);

    // getting midpoints again to make triangle smaller by setting vertices on
    // these midpoints
    double[] x1midPoint =
        cell.midPoint(mainMidpoint[0], mainMidpoint[1], x1, y1);
    double[] x2midPoint =
        cell.midPoint(mainMidpoint[0], mainMidpoint[1], x2, y2);
    double[] centremidPoint =
        cell.midPoint(mainMidpoint[0], mainMidpoint[1], centerX, centerY);

    interactable.getPoints().addAll(x1midPoint[0], x1midPoint[1], x2midPoint[0],
                                    x2midPoint[1], centremidPoint[0],
                                    centremidPoint[1]);
    interactable.setFill(Color.RED);

    interactable.setOnMouseEntered(
        event -> { interactable.setFill(Color.YELLOWGREEN); });
    interactable.setOnMouseExited(
        event -> { interactable.setFill(Color.RED); });

    interactable.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        // passing in hex side midpoint and hex centre to shoot ray
        System.out.println("Torch clicked. Ray cast at: " + mainMidpoint[0] +
                           " " + mainMidpoint[1]);
        ray = new Ray(mainMidpoint[0], mainMidpoint[1], cell);
        interactable.setOnMouseClicked(null);
        interactable.setOnMouseEntered(null);
        interactable.setOnMouseExited(null);
        interactable.setFill(Color.YELLOW);

        Main.rays.add(ray);

        if (Main.rays.size() == 6) {
          /**
           * Guessing phase
           */
        }
      }
    });
  }

  public Polygon getInteractable() { return this.interactable; }

  public double[] getMainMidpoint() { return this.mainMidpoint; }

  public void toggleOn() { interactable.setFill(Color.RED); }
  public void toggleOff() { interactable.setFill(Color.TRANSPARENT); }
}