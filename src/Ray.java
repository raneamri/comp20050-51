import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

/**
 * The Ray class is a non trivial class which handles the calculating and
 * drawing rays on the board. The Ray class does not directly extended the
 * JavaFX <a
 * href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Line.html">Line
 * class</a> and instead uses an ArrayList of coordinates to construct an
 * ArrayList of Lines.
 */
public class Ray {
  /*
   * Polyray drawing utilities
   */
  private ArrayList<Cell> path = new ArrayList<>();
  private ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
  private ArrayList<Line> lines = new ArrayList<>();
  private int torchCreator;

  /*
   * Tracks last direction passed in recursive call of
   * <code>checkCollisions()</code>
   */
  private Direction finalDirection;

  private boolean absorbed = false;

  /**
   *
   * @param startPos Midpoint of torch that spawned the Ray
   * @param cell Cell object within which Ray is spawned
   * @param torchCreator the number of the torch that created the ray
   */
  public Ray(double[] startPos, Cell cell, int torchCreator) {
    this.torchCreator = torchCreator;
    coords.add(new Pair<Double, Double>(startPos[0], startPos[1]));

    Direction dir = slopeToDirection(startPos, cell.getCenter());

    checkCollisions(dir, cell);

    drawRays();
  }

  public Ray (){}

  /**
   * Recursive method that computes path of the ray. This method of finding
   * the path is based on the assumption that any ray cast into the board passes
   * through the midpoint of any Cell it crosses. We use a "virtual pointer"
   * which tells us where the Ray's path finding is currently at and a direction
   * to recursively reach either the end of the board or an absorption.
   *
   * @param dir current direction of the ray
   * @param cell current cell virtual pointer is at
   */
  private void checkCollisions(Direction dir, Cell cell) {
    /**
     * Pre checks (& base case)
     */
    if (cell == null) {
      return;
    } else if (cell.hasAtom()) {
      absorbed = true;
      return;
    }

    path.add(cell);

    /*
     * Check for collision (aka atoms in adjacent hexagons)
     */
    Direction nextDir = dir;
    finalDirection = nextDir;

    /*
     * Compute all possible collisions for the current cell
     */
    ArrayList<Cell> collisions = new ArrayList<>();
    for (Direction d : Direction.values()) {
      Cell nextCollision = cell.getAdjacentHexagon(d);

      if (!Objects.isNull(nextCollision)) {
        if (nextCollision.hasAtom()) {
          collisions.add(cell.getAdjacentHexagon(d));
        }
      }
    }

    /*
     * The maximum collisions that can occur at a cell is 3.
     * There are four scenarios for collisions:
     *  - Absorption
     *  - 60° reflection
     *  - 120° reflection
     *  - 360° reflection
     *
     */
    if (collisions.size() == 1) {
      /*
       * Always 60 degree reflection or absorption
       */
      if (Objects.isNull(cell.getAdjacentHexagon(dir))
              ? false
              : cell.getAdjacentHexagon(dir).equals(collisions.get(0))) {
        /*
         * Absorption
         */
        absorbed = true;
        Main.getExperimenter().addAbsorption();
        path.add(collisions.get(0));
        return;
      } else {
        /*
         * 60 degree reflection
         */
        nextDir = sixtyReflection(dir, cell.getCenter(),
                                  collisions.get(0).getCenter());
        finalDirection = nextDir;
      }
    } else if (collisions.size() == 2) {
      /*
       * Always 120 degree reflection or full reflection
       * It's unclear but full reflection from 2 atom collisions are handled
       * within onetwentyReflection
       */
      nextDir = onetwentyReflection(dir, cell.getCenter(),
                                    collisions.get(0).getCenter(),
                                    collisions.get(1).getCenter());
      finalDirection = nextDir;
    } else if (collisions.size() == 3) {
      /*
       * Full reflection
       */
      nextDir = fullReflection(nextDir);
      finalDirection = nextDir;
    }

    /*
     * Find next cell for recursion
     */
    Cell next = cell.getAdjacentHexagon(nextDir);

    /*
     * Recursive call
     */
    checkCollisions(nextDir, next);
  }

  /**
   * Takes two points and returns the general direction of the slope
   *
   * @param p1
   * @param p2
   * @return Direction (enum) type method resolved
   */
  protected Direction slopeToDirection(double[] p1, double[] p2) {
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

  /**
   * Finds the distance between two points
   *
   * @param p1 point 1
   * @param p2 point 2
   * @return d between p1 -> p2, as a double
   */
  protected double distanceBetween(double p1[], double p2[]) {
    return Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2));
  }

  /**
   * Handles 60° reflections by manipulating the Direction enum type
   *
   * @param dir direction before reflection
   * @param p1 center point of cell the recursion is currently at
   * @param p2 center point of atom
   * @return new computed direction
   */
  protected Direction sixtyReflection(Direction dir, double[] p1, double p2[]) {
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

  /**
   * This method handles 120° reflection AND total internal reflection for two
   * atom collisions
   *
   * @param dir direction before reflection
   * @param p1 center point of cell the recursion is currently at
   * @param p2 center point of atom 1
   * @param p3 center point of atom 2
   * @return
   */
  protected Direction onetwentyReflection(Direction dir, double[] p1, double[] p2,
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
      if (distanceBetween(p2, p3) > 60) {
        return fullReflection(dir);
      } else {
        if (p2[1] == p3[1]) {
          return Direction.DOWN_RIGHT;
        } else {
          return Direction.RIGHT_LEFT;
        }
      }

    case UP_LEFT:
      if (distanceBetween(p2, p3) > 71) {
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

  /**
   * Finds 360° reflection of ray by inversing current Direction
   *
   * @param dir direction to inverse
   * @return inversed direction
   */
  protected Direction fullReflection(Direction dir) {
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

  /**
   * Computes the final point the Ray most cross in order to meet the edge of
   * the board. This is done by taking the final Cell the Ray crossed and
   * finding the codirectional Torch assigned to that Cell's midpoint.
   *
   * @param c final Cell the Ray crossed
   * @return point required to meet edge of the board, or null if the Ray was
   *     absorbed
   */
  private Pair<Double, Double> addFinalPoint(Cell c) {
    for (Torch t : c.getTorch()) {
      if (slopeToDirection(c.getCenter(), t.getMainMidpoint()) ==
          finalDirection)
        return new Pair<>(t.getMainMidpoint()[0], t.getMainMidpoint()[1]);
    }

    return null;
  }

  /**
   * Iterates over the points gathered by recursion and essentially draws a
   * JavaFX Polyray
   */
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
      /*
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
      Flag flag = new Flag(getFlagPos(), torchCreator);
      Main.flags.add(flag);
      Main.getGroup().getChildren().add(flag.getInteractable());
    }

    toggleOff();
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