import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class Ray {
  /**
   * Polyray drawing utilities
   */
  private ArrayList<Cell> path = new ArrayList<>();
  private ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
  private ArrayList<Line> lines = new ArrayList<>();

  private Direction finalDirection;

  private boolean absorbed = false;

  public Ray(double[] startPos, Cell cell) {
    coords.add(new Pair<Double, Double>(startPos[0], startPos[1]));

    Direction dir = slopeToDirection(startPos, cell.getCenter());

    checkCollisions(dir, cell);

    drawRays();
  }

  private void checkCollisions(Direction dir, Cell cell) {
    /**
     * Pre checks
     */
    if (cell == null) {
      return;
    } else if (cell.hasAtom()) {
      absorbed = true;
      return;
    }

    path.add(cell);

    /**
     * Check for collision (aka atoms in adjacent hexagons)
     */
    Direction nextDir = dir;
    finalDirection = nextDir;

    ArrayList<Cell> collisions = new ArrayList<>();

    for (Direction d : Direction.values()) {
      Cell nextCollision = cell.getAdjacentHexagon(d);

      if (!Objects.isNull(nextCollision)) {
        if (nextCollision.hasAtom()) {
          collisions.add(cell.getAdjacentHexagon(d));
        }
      }
    }

    if (collisions.size() == 1) {
      /**
       * Always 60 degree reflection or absorption
       */
      if (Objects.isNull(cell.getAdjacentHexagon(dir))
              ? false
              : cell.getAdjacentHexagon(dir).equals(collisions.get(0))) {
        /**
         * Absorption
         */
        absorbed = true;
        Main.getExperimenter().addAbsorption();
        path.add(collisions.get(0));
        return;
      } else {
        nextDir = sixtyReflection(dir, cell.getCenter(),
                                  collisions.get(0).getCenter());
        finalDirection = nextDir;
      }
    } else if (collisions.size() == 2) {
      /**
       * Always 120 degree reflection or full reflection
       */
      nextDir = onetwentyReflection(dir, cell.getCenter(),
                                    collisions.get(0).getCenter(),
                                    collisions.get(1).getCenter());
      finalDirection = nextDir;
    } else if (collisions.size() == 3) {
      /**
       * Always full reflection
       */
      nextDir = fullReflection(nextDir);
      finalDirection = nextDir;
    }

    Cell next = cell.getAdjacentHexagon(nextDir);

    checkCollisions(nextDir, next);
  }

  /**
   * Takes two points and returns the general direction of the slope
   * @param p1
   * @param p2
   * @return Direction (enum) type method resolved
   */
  private Direction slopeToDirection(double[] p1, double[] p2) {
    double error = 1e-3d;
    if (Math.abs(p1[1] - p2[1]) < error) {
      if (p1[0] < p2[0]) {
        return Direction.LEFT_RIGHT;
      } else {
        return Direction.RIGHT_LEFT;
      }
    } else if (p1[0] > p2[0]) {
      if (p1[1] > p2[1]) {
        return Direction.UP_LEFT;
      } else {
        return Direction.DOWN_LEFT;
      }
    } else {
      if (p1[1] > p2[1]) {
        return Direction.UP_RIGHT;
      } else {
        return Direction.DOWN_RIGHT;
      }
    }
  }

  private double distanceBetween(double p1[], double p2[]) {
    return Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2));
  }

  private Direction sixtyReflection(Direction dir, double[] p1, double p2[]) {
    switch (dir) {
    case LEFT_RIGHT:
      if (p1[1] > p2[1]) {
        return Direction.DOWN_RIGHT;
      } else {
        return Direction.UP_RIGHT;
      }
    case RIGHT_LEFT:
      if (p1[1] > p2[1]) {
        return Direction.DOWN_LEFT;
      } else {
        return Direction.UP_LEFT;
      }

    case UP_LEFT:
      if (p1[0] > p2[0]) {
        return Direction.UP_RIGHT;
      } else {
        return Direction.RIGHT_LEFT;
      }

    case DOWN_LEFT:
      if (p1[0] > p2[0]) {
        return Direction.DOWN_RIGHT;
      } else {
        return Direction.RIGHT_LEFT;
      }

    case UP_RIGHT:
      if (p1[0] < p2[0]) {
        return Direction.UP_LEFT;
      } else {
        return Direction.LEFT_RIGHT;
      }

    case DOWN_RIGHT:
      if (p1[0] < p2[0]) {
        return Direction.DOWN_LEFT;
      } else {
        return Direction.LEFT_RIGHT;
      }
    default:
      return null;
    }
  }

  private Direction onetwentyReflection(Direction dir, double[] p1, double[] p2,
                                        double[] p3) {

    switch (dir) {
    case LEFT_RIGHT:
      if (distanceBetween(p2, p3) > 70) {
        return fullReflection(dir);
      } else {
        if (Math.min(p2[1], p3[1]) >= p1[1]) {
          return Direction.UP_LEFT;
        } else {
          return Direction.DOWN_LEFT;
        }
      }

    case RIGHT_LEFT:
      if (distanceBetween(p2, p3) > 70) {
        return fullReflection(dir);
      } else {
        if (Math.min(p2[1], p3[1]) >= p1[1]) {
          return Direction.UP_RIGHT;
        } else {
          return Direction.DOWN_RIGHT;
        }
      }

    case UP_RIGHT:
      if (distanceBetween(p2, p3) > 70) {
        return fullReflection(dir);
      } else {
        if (p2[1] == p3[1]) {
          return Direction.DOWN_RIGHT;
        } else {
          return Direction.RIGHT_LEFT;
        }
      }

    case UP_LEFT:
      if (distanceBetween(p2, p3) > 70) {
        return fullReflection(dir);
      } else {
        if (p2[1] == p3[1]) {
          return Direction.DOWN_LEFT;
        } else {
          return Direction.LEFT_RIGHT;
        }
      }

    case DOWN_RIGHT:
      if (distanceBetween(p2, p3) > 70) {
        return fullReflection(dir);
      } else {
        if (p2[1] == p3[1]) {
          return Direction.UP_RIGHT;
        } else {
          return fullReflection(dir);
        }
      }

    case DOWN_LEFT:
      if (distanceBetween(p2, p3) > 70) {
        return fullReflection(dir);
      } else {
        if (p2[1] == p3[1]) {
          return Direction.UP_LEFT;
        } else {
          return fullReflection(dir);
        }
      }
    default:
      return null;
    }
  }

  private Direction fullReflection(Direction dir) {
    switch (dir) {
    case LEFT_RIGHT:
      return Direction.RIGHT_LEFT;

    case RIGHT_LEFT:
      return Direction.LEFT_RIGHT;

    case UP_RIGHT:
      return Direction.DOWN_LEFT;

    case UP_LEFT:
      return Direction.DOWN_RIGHT;

    case DOWN_RIGHT:
      return Direction.UP_LEFT;

    case DOWN_LEFT:
      return Direction.UP_RIGHT;

    default:
      return null;
    }
  }

  private Pair<Double, Double> addFinalPoint(Cell c) {
    for (Torch t : c.getTorch()) {
      if (slopeToDirection(c.getCenter(), t.getMainMidpoint()) ==
          finalDirection)
        return new Pair<>(t.getMainMidpoint()[0], t.getMainMidpoint()[1]);
    }

    return null;
  }

  private void drawRays() {
    for (Cell cell : path) {
      coords.add(
          new Pair<Double, Double>(cell.getCenterX(), cell.getCenterY()));
    }

    if (!absorbed)
      coords.add(addFinalPoint(path.get(path.size() - 1)));

    for (int i = 0; i < coords.size() - 1; i++) {
      Line line =
          new Line(coords.get(i).getKey(), coords.get(i).getValue(),
                   coords.get(i + 1).getKey(), coords.get(i + 1).getValue());
      /**
       * Display parameters
       */
      line.setFill(Color.YELLOW);
      line.setStroke(Color.YELLOW);
      line.setStrokeWidth(2);
      line.setMouseTransparent(true);
      lines.add(line);

      Main.getGroup().getChildren().add(line);
    }

    if (!absorbed) {
      Flag flag = new Flag(getFlagPos());
      Main.flags.add(flag);
      Main.getGroup().getChildren().add(flag.getInteractable());
    }

    // toggleOff();
  }

  public void toggleOn() {
    for (Line l : lines) {
      l.setStroke(Color.YELLOW);
    }
  }
  public void toggleOff() {
    for (Line l : lines) {
      l.setStroke(Color.TRANSPARENT);
    }
  }

  /**
   * @return null if the ray was absorbed, the exit position of the ray
   *     otherwise
   */
  public Pair<Double, Double> getFlagPos() {
    return (absorbed) ? null : coords.get(coords.size() - 1);
  }
}