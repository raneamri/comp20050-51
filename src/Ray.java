import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class Ray {
  public enum ORDER { ROW_LR, ROW_RL, COL_LR, COL_RL }

  private ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
  private ArrayList<Line> lines = new ArrayList<>();

  public Ray(double startX, double startY, double endX, double endY) {
    /**
     * Prepare to draw rays
     */
    coords.add(new Pair<Double, Double>(startX, startY));
    checkCollisions(startX, startY, endX, endY);

    drawRays();
  }

  /**
   * Calculates the slope of the line going through two points
   *
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @return slope
   */
  public double getSlope(double x1, double y1, double x2, double y2) {
    return (y2 - y1) / (x2 - x1);
  }

  /**
   * Calculates the y-intercept of a line equation
   * @param x1
   * @param y1
   * @param m
   * @return y-intercept
   */
  public double getYIntercept(double x1, double y1, double m) {
    return y1 - m * x1;
  }

  /**
   * Checks if three points are collinear. Done by comparing slopes, with a
   * level of error allowed
   *
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @param x3
   * @param y3
   * @return true if collinear, false otherwise
   */
  public boolean isCollinear(double x1, double y1, double x2, double y2,
                             double x3, double y3) {
    double m1 = (y2 - y1) / (x2 - x1);
    double m2 = (y3 - y2) / (x3 - x2);
    double error = 1e-5;

    return Math.abs(m1 - m2) < error;
  }

  /**
   * Calculates the point(s) of contact between a line segment defined by two
   * points and a circle defined by a center point and radius and decides which
   * is significant for its purpose.
   *
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @param centerX
   * @param centerY
   * @param radius
   * @return the coordinates of the meaningful point of contact with the circle,
   *     or null if there is none
   */
  public static double[] getPointOfContact(double x1, double y1, double x2,
                                           double y2, double centerX,
                                           double centerY, double radius) {

    /**
     * Vector components
     */
    double vecX = x2 - x1;
    double vecY = y2 - y1;

    /**
     * Vector components from x1 y1 to center of circle
     */
    double vecCX = centerX - x1;
    double vecCY = centerY - y1;

    /**
     * Length of segment squared
     */
    double a = vecX * vecX + vecY * vecY;

    /**
     * Dot product of vectors
     */
    double dotBy2 = vecX * vecCX + vecY * vecCY;
    double c = vecCX * vecCX + vecCY * vecCY - radius * radius;

    double pBy2 = dotBy2 / a;
    double q = c / a;

    double discriminant = pBy2 * pBy2 - q;

    /**
     * No intersection
     */
    if (discriminant < 0) {
      return null;
    }

    /**
     * Scaling factors along the line
     */
    double discSqrt = Math.sqrt(discriminant);
    double abScalingFactor1 = -pBy2 + discSqrt;
    double abScalingFactor2 = -pBy2 - discSqrt;

    /**
     * First intersection point (line intersects circle twice unless tangential)
     */
    double p1x = x1 - vecX * abScalingFactor1;
    double p1y = y1 - vecY * abScalingFactor1;

    /**
     * Tangential therefore no need to check which point of contact we need
     */
    if (discriminant == 0) {
      return new double[] {p1x, p1y};
    }

    /**
     * Second intersection point
     */
    double p2x = x1 - vecX * abScalingFactor2;
    double p2y = y1 - vecY * abScalingFactor2;

    /**
     * We only keep the intersection point closest to x1 y1
     */
    double d1 = Math.sqrt(Math.pow(p1x - x1, 2) + Math.pow(p1y - y1, 2));
    double d2 = Math.sqrt(Math.pow(p2x - x1, 2) + Math.pow(p2y - y1, 2));

    return d1 < d2 ? new double[] {p1x, p1y} : new double[] {p2x, p2y};
  }

  /**
   * For a line interpreted as two points, this method checks for the first
   * COI it'd intersect with
   *
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   */
  public void checkCollisions(double x1, double y1, double x2, double y2) {
    for (Atom atom : Main.atoms) {
      /**
       * If they are collinear
       */
      if (isCollinear(x1, y1, x2, y2, atom.getCenterX(), atom.getCenterY())) {
        /**
         * This will be the last collision so we return the coordinates of the
         * atom
         */
        System.out.println("Absorption at " + atom.getCenterX() + " " +
                           atom.getCenterY());
        System.out.println(atom.coi.getRadius());
        coords.add(
            new Pair<Double, Double>(atom.getCenterX(), atom.getCenterY()));
        return;
      }

      /**
       * If they collide
       */
      double[] pointOfContact =
          getPointOfContact(x1, y1, x2, y2, atom.getCenterX(),
                            atom.getCenterY(), atom.coi.getRadius());
      if (pointOfContact != null) {
        /**
         * Calculate point of collision of with COI
         */
        System.out.println("Collision at " + pointOfContact[0] + " " +
                           pointOfContact[1]);
        coords.add(
            new Pair<Double, Double>(pointOfContact[0], pointOfContact[1]));

        /**
         * Recursive call with next point
         */
        checkCollisions(atom.getCenterX(), atom.getCenterY(), pointOfContact[0],
                        pointOfContact[1]);
        return;
      } else {
        System.out.println("No collision");
        return;
      }
    }

    /**
     * No collisions left
     */
    return;
  }

  public void drawRays() {
    if (coords.size() == 1) {
      Line line =
          new Line(coords.get(0).getKey(), coords.get(0).getValue(),
                   coords.get(0).getKey() + 20, coords.get(0).getValue() + 20);
      line.setFill(Color.YELLOW);
      line.setStroke(Color.YELLOW);
      line.setStrokeWidth(4);
      lines.add(line);
      Main.getGroup().getChildren().add(line);
      return;
    }

    for (int i = 0; i < coords.size() - 1; i++) {
      Line line =
          new Line(coords.get(i).getKey(), coords.get(i).getValue(),
                   coords.get(i + 1).getKey(), coords.get(i + 1).getValue());
      /**
       * Display parameters
       */
      line.setFill(Color.YELLOW);
      line.setStroke(Color.YELLOW);
      line.setStrokeWidth(4);
      lines.add(line);
      Main.getGroup().getChildren().add(line);
    }
  }
}
