package blackbox;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

/**
 * Cells are the building blocks of the board. Each cell is a hexagon, but the
 * Cell class doesn't directly extend the JavaFX <a
 * href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Polygon.html">Polygon
 * class</a> since cells include torches and assigned atoms.
 */
public class Cell {
  /**
   * Final constants
   */
  private static final double ROTATION_ANGLE = Math.PI / 6.0;

  /**
   * Cell children
   */
  private Polygon hexagon;
  private Atom atom;
  private ArrayList<Torch> torches = new ArrayList<>();

  public int[] coords;

  /**
   * Flags
   */
  private boolean hasAtom = false;
  private boolean hasMarker = false;

  public Cell(int row, int col) { this.coords = new int[] {row, col}; }

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
    hexagon.setStrokeWidth(2);

    /**
     * Event handler
     */
    hexagon.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if (Main.gameStage == Main.GameStage.SETTER ||
            Main.gameStage == Main.GameStage.MARKERS) {
          hexagon.setFill(Color.color(0.1, 0.1, 0.1));
          hexagon.setOnMouseExited(
              event -> { hexagon.setFill(Color.TRANSPARENT); });
        }
      }
    });

    hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (Main.atoms.size() < Main.MAX_ATOMS) {
          /**
           * Allow setter to place atoms until all atoms are placed
           */
          addAtom();

          if (Main.atoms.size() == Main.MAX_ATOMS) {
            try {
              Thread.sleep(100);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

            for (Atom a : Main.atoms) {
              a.toggleOff();
            }

            /*
             * Switching game stage
             */
            Main.gameStage = Main.GameStage.RAYS;
            Main.player.setText("EXPERIMENTER");
            Main.statusInstruct("Experimenter's Turn\n"
                                + "Shoot rays to figure out atom locations");
          }

          return;

        } else if (Main.gameStage != Main.GameStage.MARKERS || hasMarker) {
          /**
           * Allow no placement if not in correct game stage
           * or already has a marker
           */
          return;
        }
        hasMarker = true;

        if (hasCorrectGuess()) {
          Main.getExperimenter().addScore(2);
        }

        Marker marker =
            new Marker(new Pair<Double, Double>(getCenterX(), getCenterY()));
        Main.markers.add(marker);
        Main.getGroup().getChildren().add(marker.getInteractable());
      }
    });
    return hexagon;
  }

  /**
   * Finds the midpoint of two points
   * Its applied purpose is finding the centre of the side of a cell
   *
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @return double[] wrapped coordinates of the midpoint
   */
  public double[] midpoint(double x1, double y1, double x2, double y2) {
    double midpoint[] = {(x1 + x2) / 2, (y1 + y2) / 2};
    return midpoint;
  }

  /**
   * Method uses a switch statement and external methods to find neighbor of a
   * cell in a given direction
   * When applied, this method will sometimes return null
   * This is intended and helps map the
   * board's edges
   *
   * @param direction enum. type used to represent slope
   * @return reference to cell in that direction if found, null otherwise
   */
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

    if (!Board.isInBoard(adjacentHexagon[0], adjacentHexagon[1])) {
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

  /**
   * Attempts to allocate an atom to a cell
   *
   * @return true if the atom was successfully added, false if the cell already
   *     had an atom
   */
  public boolean addAtom() {
    if (hasAtom) {
      return false;
    } else {
      double centerX = hexagon.getBoundsInParent().getCenterX();
      double centerY = hexagon.getBoundsInParent().getCenterY();
      Atom a = new Atom(centerX, centerY);
      a.coi = new COI(centerX, centerY);

      Main.getGroup().getChildren().add(a);
      Main.getGroup().getChildren().add(a.coi);
      Main.atoms.add(a);

      this.hasAtom = true;
      this.atom = a;

      return true;
    }
  }

  public boolean hasAtom() { return this.hasAtom; }

  public boolean hasMarker() { return this.hasMarker; }

  public Atom getAtom() { return this.atom; }

  public void addTorch(Torch t) { torches.add(t); }

  public ArrayList<Torch> getTorch() { return torches; }

  public Polygon getHexagon() { return hexagon; }

  public void addMarker() { hasMarker = true; }

  /**
   * Simplified check for if user has pinpointed the right atom
   *
   * @return true if guess is correct, false otherwise
   */
  public boolean hasCorrectGuess() { return hasAtom && hasMarker; }

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
