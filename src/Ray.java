import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class Ray {
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
  private boolean isCollinear(double x1, double y1, double x2, double y2,
                              double x3, double y3) {
    double m1 = (y2 - y1) / (x2 - x1);
    double m2 = (y3 - y2) / (x3 - x2);
    double error = 1e-1;

    return Math.abs(m1 - m2) < error;
  }

  private boolean isReflectedBy(double[] point, double[] center, double r) {
    double distance = Math.sqrt(Math.pow(point[0] - center[0], 2) +
                                Math.pow(point[1] - center[1], 2));

    return distance <= r;
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
  private static double[] getPointOfContact(double x1, double y1, double x2,
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

  private double[] getReflectedRay(double[] line, double[] normal,
                                   double[] poc) {
    double n = 1;

    /**
     * Recast points
     */
    double x2 = line[2];
    double y2 = line[3];
    double nx1 = normal[0];
    double ny1 = normal[1];
    double nx2 = normal[2];
    double ny2 = normal[3];

    /**
     * Compute directional vector of incident ray
     */
    double[] incidentRay = {x2 - poc[0], y2 - poc[1]};

    /**
     * Compute normal vector
     */
    double normalX = ny1 - ny2;
    double normalY = nx2 - nx1;

    /**
     * Check if the normal vector points towards the incident ray
     */
    double dotProduct = incidentRay[0] * normalX + incidentRay[1] * normalY;
    if (dotProduct < 0) {
      /**
       * Sometimes reflected ray will be in wrong direction, this reverses it
       */
      normalX = -normalX;
      normalY = -normalY;
    }

    /**
     * Get magnitude of normal vector
     */
    double normalMagnitude = Math.sqrt(normalX * normalX + normalY * normalY);

    /**
     * Get unit of normal vector
     */
    double[] unitNormal = {normalX / normalMagnitude,
                           normalY / normalMagnitude};

    /**
     * Compute the dot product of the incident ray direction vector and the
     * unit normal vector
     */
    double dotProduct2 =
        incidentRay[0] * unitNormal[0] + incidentRay[1] * unitNormal[1];

    /**
     * Get reflected ray vector
     */
    double[] reflectedRay = {incidentRay[0] - 2 * dotProduct2 * unitNormal[0],
                             incidentRay[1] - 2 * dotProduct2 * unitNormal[1]};

    /**
     * Get its magnitude
     */
    double reflectedRayMagnitude = Math.sqrt(reflectedRay[0] * reflectedRay[0] +
                                             reflectedRay[1] * reflectedRay[1]);

    /**
     * Shorten vector to n
     */
    double scale = n / reflectedRayMagnitude;
    double[] scaledReflectedRay = {reflectedRay[0] * scale,
                                   reflectedRay[1] * scale};

    /**
     * Add the scaled reflected ray direction vector to the point of
     * contact to get the end point of the reflected ray
     */
    double[] reflectedRayEndPoint = {poc[0] + scaledReflectedRay[0],
                                     poc[1] + scaledReflectedRay[1]};

    return reflectedRayEndPoint;
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
  private void checkCollisions(double x1, double y1, double x2, double y2) {
    for (Atom atom : Main.atoms) {
      double centerX = atom.getCenterX();
      double centerY = atom.getCenterY();
      double radius = atom.coi.getRadius();

      // Check for collisions with atoms
      if (isReflectedBy(new double[] {x1, y1}, new double[] {centerX, centerY},
                        radius)) {
        continue;
      }

      if (isCollinear(x1, y1, x2, y2, centerX, centerY)) {
        // Absorption when collinear
        coords.add(new Pair<>(centerX, centerY));
        return;
      }

      double[] pointOfContact =
          getPointOfContact(x1, y1, x2, y2, centerX, centerY, radius);

      if (pointOfContact != null) {
        // Calculate reflection
        double[] nextPoint =
            getReflectedRay(new double[] {x1, y1, x2, y2},
                            new double[] {centerX, centerY, pointOfContact[0],
                                          pointOfContact[1]},
                            pointOfContact);

        coords.add(new Pair<>(pointOfContact[0], pointOfContact[1]));
        coords.add(new Pair<>(nextPoint[0], nextPoint[1]));

        checkCollisions(pointOfContact[0], pointOfContact[1], nextPoint[0],
                        nextPoint[1]);
        return;
      }
    }
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