import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cell {
  private static final double ROTATION_ANGLE = Math.PI / 6.0;
  public Polygon hexagon;
  public int[] coords = new int[] {0, 0};
  private boolean hasAtom;
  private Atom atom;

  public Cell() {}

  public Cell(int row, int col) {
    coords = new int[] {row, col};
    hasAtom = false;
  }

  public Polygon createHexagon(double size) {
    hexagon = new Polygon();

    /**
     * Simple maths to construct a hexagon
     */
    for (int i = 0; i < 6; i++) {
      double angle = ROTATION_ANGLE + (2.0 * Math.PI / 6 * i);
      double x = size * Math.cos(angle);
      double y = size * Math.sin(angle);
      hexagon.getPoints().addAll(x, y);
    }

    /**
     * Visual parameters
     */
    hexagon.setFill(Color.TRANSPARENT);
    hexagon.setStroke(Color.RED);

    hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double centerX = hexagon.getBoundsInParent().getCenterX();
        double centerY = hexagon.getBoundsInParent().getCenterY();
        Atom a = new Atom(centerX, centerY);
        a.coi = new COI(centerX, centerY);
        System.out.println("Hexagon clicked coords " + coords[0] + " " +
                           coords[1]);

        if (Main.atoms.size() == Main.MAX_ATOMS) {
          return;
        }

        Main.getGroup().getChildren().add(a);
        Main.getGroup().getChildren().add(a.coi);
        Main.atoms.add(a);
        hasAtom = true;
        atom = a;

        hexagon.setOnMouseClicked(null);
      }
    });

    return hexagon;
  }

  public double[] midPoint(double x1, double y1, double x2, double y2) {
    double midpoint[] = {(x1 + x2) / 2, (y1 + y2) / 2};
    return midpoint;
  }

  public Cell getAdjacentHexagon(Direction direction) {
    int[] adjacentHexagon = new int[2];
    int row = getRow();
    int col = getCol();

    switch (direction) {
    case LEFT_RIGHT:
      adjacentHexagon[0] = row;
      adjacentHexagon[1] = col + 1;
      break;
    case RIGHT_LEFT:
      adjacentHexagon[0] = row;
      adjacentHexagon[1] = col - 1;
      break;
    case UP_RIGHT:
      if (row >= 5) {
        adjacentHexagon[0] = row - 1;
        adjacentHexagon[1] = col + 1;
      } else {
        adjacentHexagon[0] = row - 1;
        adjacentHexagon[1] = col;
      }
      break;
    case UP_LEFT:
      if (row >= 5) {
        adjacentHexagon[0] = row - 1;
        adjacentHexagon[1] = col;
      } else {
        adjacentHexagon[0] = row - 1;
        adjacentHexagon[1] = col - 1;
      }
      break;
    case DOWN_RIGHT:
      if (row >= 4) {
        adjacentHexagon[0] = row + 1;
        adjacentHexagon[1] = col;
      } else {
        adjacentHexagon[0] = row + 1;
        adjacentHexagon[1] = col + 1;
      }
      break;
    case DOWN_LEFT:
      if (row >= 4) {
        adjacentHexagon[0] = row + 1;
        adjacentHexagon[1] = col - 1;
      } else {
        adjacentHexagon[0] = row + 1;
        adjacentHexagon[1] = col;
      }
      break;
    }

    if (adjacentHexagon[0] < 0 || adjacentHexagon[1] < 0 ||
        adjacentHexagon[1] >= Board.getNumHexagonsInRow(adjacentHexagon[0]) ||
        adjacentHexagon[0] > 8) {
      return null;
    } else {
      return Board.getCells()[adjacentHexagon[0]][adjacentHexagon[1]];
    }
  }

  public double getCenterX() {
    return hexagon.getBoundsInParent().getCenterX();
  }

  public double getCenterY() {
    return hexagon.getBoundsInParent().getCenterY();
  }

  public double[] getCenter() {
    return new double[] {getCenterX(), getCenterY()};
  }

  public int getRow() { return coords[0]; }

  public int getCol() { return coords[1]; }

  public int[] getIndex() { return new int[] {getRow(), getCol()}; }

  public boolean hasAtom() { return hasAtom; }

  public Atom getAtom() { return atom; }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null || getClass() != obj.getClass())
      return false;

    Cell other = (Cell)obj;
    return this.getIndex() == other.getIndex();
  }
}
