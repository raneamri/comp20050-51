import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class Ray {
  private ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
  private ArrayList<Line> lines = new ArrayList<>();
  private ArrayList<Cell> path = new ArrayList<>();

  public Ray(double startX, double startY, Cell cell) {
    coords.add(new Pair<Double, Double>(startX, startY));

    path.add(cell);

    Direction dir =
        slopeToDirection(new double[] {startX, startY}, cell.getCenter());

    checkCollisions(dir, cell);

    drawRays();
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

  private Direction sixtyReflection(Direction dir, double[] p1, double p2[]) {
    switch (dir) {
    case LEFT_RIGHT:
      if (p1[1] > p2[1]) {
        return Direction.DOWN_LEFT;
      } else {
        return Direction.UP_LEFT;
      }
    case RIGHT_LEFT:
      if (p1[1] > p2[1]) {
        return Direction.DOWN_RIGHT;
      } else {
        return Direction.UP_RIGHT;
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
      if (Math.min(p2[1], p3[1]) >= p1[1]) {
        return Direction.UP_LEFT;
      } else {
        return Direction.DOWN_LEFT;
      }

    case RIGHT_LEFT:
      if (Math.min(p2[1], p3[1]) >= p1[1]) {
        return Direction.UP_RIGHT;
      } else {
        return Direction.DOWN_RIGHT;
      }

    case UP_RIGHT:
      if (p2[1] == p3[1]) {
        return Direction.DOWN_RIGHT;
      } else {
        return Direction.RIGHT_LEFT;
      }

    case UP_LEFT:
      if (p2[1] == p3[1]) {
        return Direction.DOWN_LEFT;
      } else {
        return Direction.LEFT_RIGHT;
      }

    case DOWN_RIGHT:
      if (p2[1] == p3[1]) {
        return Direction.UP_RIGHT;
      } else {
        return Direction.RIGHT_LEFT;
      }

    case DOWN_LEFT:
      if (p2[1] == p3[1]) {
        return Direction.UP_LEFT;
      } else {
        return Direction.LEFT_RIGHT;
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

  private void checkCollisions(Direction dir, Cell cell) {
    /**
     * Pre checks
     */
    if (cell.hasAtom()) {
      path.add(cell);
      return;
    }

    /**
     * Check for collision (aka atoms in adjacent hexagons)
     */
    Direction nextDir = dir;
    Direction[] directions = new Direction[] {
        Direction.LEFT_RIGHT, Direction.RIGHT_LEFT, Direction.UP_RIGHT,
        Direction.UP_LEFT,    Direction.DOWN_RIGHT, Direction.DOWN_LEFT};
    ArrayList<Cell> collisions = new ArrayList<>();
    for (Direction d : directions) {
      Cell nextCollision = cell.getAdjacentHexagon(d);
      if (nextCollision != null) {
        if (nextCollision.hasAtom()) {
          System.out.println("Collided with: " + nextCollision.getRow() + " " +
                             nextCollision.getCol());
          collisions.add(cell.getAdjacentHexagon(d));
        }
      }
    }

    if (collisions.size() == 1) {
      /**
       * Always 60 degree reflection or absorption
       */
      if (cell.getAdjacentHexagon(dir).equals(collisions.getFirst())) {
        /**
         * Absorption
         */
        System.out.println("Absorption");
        path.add(collisions.getFirst());
        return;
      } else {
        nextDir = sixtyReflection(dir, cell.getCenter(),
                                  collisions.getFirst().getCenter());
        Cell next = cell.getAdjacentHexagon(nextDir);
        System.out.println("Next dir: " + nextDir.toString());
        checkCollisions(nextDir, next);
        return;
      }

    } else if (collisions.size() == 2) {
      /**
       * Always 120 degree reflection
       */
      nextDir = onetwentyReflection(dir, cell.getCenter(),
                                    collisions.getFirst().getCenter(),
                                    collisions.getLast().getCenter());

    } else if (collisions.size() == 3) {
      /**
       * Always full reflection
       */
      nextDir = fullReflection(nextDir);
      checkCollisions(
          nextDir,
          cell.getAdjacentHexagon(nextDir).getAdjacentHexagon(nextDir));
    }

    /**
     * If no collisions and not at boards bounds, advance to next cell in
     * direction
     */
    Cell next = cell.getAdjacentHexagon(nextDir);
    /**
     * Check if reached boards bounds
     */
    if (next == null) {
      System.out.println("Out of bounds");
      return;
    }
    System.out.println("Curr cell: " + cell.getRow() + " " + cell.getCol());
    System.out.println("Next cell: " + next.getRow() + " " + next.getCol());
    path.add(next);
    checkCollisions(nextDir, next);
  }

  private Pair<Double, Double> addFinalPoint(Cell c1, Cell c2) {
    double n = 1;

    switch (slopeToDirection(c1.getCenter(), c2.getCenter())) {
    case LEFT_RIGHT:
      return new Pair<Double, Double>(c2.getCenterX() + n, c2.getCenterY());

    case RIGHT_LEFT:
      return new Pair<Double, Double>(c2.getCenterX() - n, c2.getCenterY());

    case UP_RIGHT:
      return new Pair<Double, Double>(c2.getCenterX() + n, c2.getCenterY() - n);

    case UP_LEFT:
      return new Pair<Double, Double>(c2.getCenterX() - n, c2.getCenterY() - n);

    case DOWN_RIGHT:
      return new Pair<Double, Double>(c2.getCenterX() - n, c2.getCenterY() + n);

    case DOWN_LEFT:
      return new Pair<Double, Double>(c2.getCenterX() + n, c2.getCenterY() + n);

    default:
      return null;
    }
  }

  private void drawRays() {
    for (Cell cell : path) {
      coords.add(
          new Pair<Double, Double>(cell.getCenterX(), cell.getCenterY()));
    }

    // coords.add(addFinalPoint(path.getLast(), path.get(path.size() - 2)));

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
      lines.add(line);

      Main.getGroup().getChildren().add(line);
    }
  }
}