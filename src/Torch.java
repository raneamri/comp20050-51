import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * The Torch class is an interactable that allows the Experimenter to shine rays
 * on the board. The Torch is created by and bound to a Cell class. Cells and
 * Torchs do not have a bijective relation.
 */
public class Torch {
  private Polygon interactable;
  private Ray ray;

  private double[] midpoint;

  /**
   *
   * @param cell Cell object torch is being assigned to
   * @param i array position of appropriate hexagon vertex
   */
  public Torch(Cell cell, int i) {
    interactable = new Polygon();
    Polygon hex = cell.getHexagon();

    /*
     * Init. array that contains hexagon points
     */
    ArrayList<Double> hexagonPoints = new ArrayList<>(hex.getPoints());
    double centerX = hex.getBoundsInParent().getCenterX();
    double centerY = hex.getBoundsInParent().getCenterY();

    /*
     * x & y offsets to place triangle vertices correctly on the board
     */
    double xOffset = hex.getLayoutX();
    double yOffset = hex.getLayoutY();

    double x1 = hexagonPoints.get(i) + xOffset;
    double y1 = hexagonPoints.get(++i) + yOffset;
    if (i == 11)
      i = -1;
    double x2 = hexagonPoints.get(++i) + xOffset;
    double y2 = hexagonPoints.get(++i) + yOffset;

    /*
     * Midpoint of hexagon side
     */
    midpoint = cell.midpoint(x1, y1, x2, y2);

    /*
     * Getting midpoints again to make triangle smaller by setting vertices on
     * these midpoints
     */
    double[] x1midPoint = cell.midpoint(midpoint[0], midpoint[1], x1, y1);
    double[] x2midPoint = cell.midpoint(midpoint[0], midpoint[1], x2, y2);
    double[] centremidPoint =
        cell.midpoint(midpoint[0], midpoint[1], centerX, centerY);

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
        /*
         * Block action if not all atoms have been placed yet
         */
        if (Main.atoms.size() < Main.MAX_ATOMS) {
          return;
        }

        /*
         * If all rays have been shone, toggle off torches
         */
        if (Main.rays.size() >= Main.MAX_RAYS - 1) {
          ray = new Ray(midpoint, cell);
          Main.rays.add(ray);

          for (Torch t : Main.torchs) {
            t.toggleOff();
            t.interactable.setOnMouseClicked(null);
          }

          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          return;
        }

        /*
         * Passing hexagon's side's midpoint and hexagon's centre to begin
         * shooting the ray
         */
        ray = new Ray(midpoint, cell);
        /*
         * Void torch properties
         */
        interactable.setOnMouseClicked(null);
        interactable.setOnMouseEntered(null);
        interactable.setOnMouseExited(null);
        interactable.setFill(Color.YELLOW);

        Main.rays.add(ray);
      }
    });
  }

  public Polygon getInteractable() { return this.interactable; }

  public double[] getMainMidpoint() { return this.midpoint; }

  public void toggleOn() { interactable.setVisible(true); }
  public void toggleOff() { interactable.setVisible(false); }
}