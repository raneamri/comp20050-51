package test;

import static org.junit.jupiter.api.Assertions.*;

import blackbox.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

public class GameLogicTest {
  @Test
  void testAtomEquals() {
    Atom atom1 = new Atom(10, 10);
    Atom atom2 = new Atom(10, 10);

    Atom atom3 = new Atom(30, 30);
    Atom atom4 = new Atom(40, 40);

    COI coi1 = null;
    COI coi2 = new COI(40, 40);

    assertTrue(atom1.equals(atom1));
    assertTrue(atom1.equals(atom2));
    assertFalse(atom3.equals(coi1));
    assertFalse(atom4.equals(coi2));
    assertEquals(atom1.equals(atom2), atom2.equals(atom1));
  }

  @Test
  void testGetBoardGroup() {
    Board newBoard = new Board();

    Group group = newBoard.getBoardGroup();

    boolean containsPolygon = false;

    for (Node node : group.getChildren()) {
      if (node instanceof Polygon) {
        containsPolygon = true;
        break;
      }
    }
    // Ensure the hexagons are being added to main's group correctly
    assertTrue(containsPolygon);
  }

  @Test
  void testGetNumHexagonsInRow() {
    Board newBoard = new Board();

    int expectedHexagons = 5;
    assertEquals(expectedHexagons, newBoard.getCells()[0].length);

    expectedHexagons = 6;
    assertEquals(expectedHexagons, newBoard.getCells()[1].length);

    expectedHexagons = 9;
    assertEquals(expectedHexagons, newBoard.getCells()[4].length);
  }

  @Test
  void testCorrectGuess() {
    Cell cell = new Cell(1, 1);
    Polygon hexagon = cell.createHexagon(40);
    cell.addAtom();
    cell.addMarker();

    assertTrue(cell.hasCorrectGuess());

    cell = new Cell(1, 1);
    cell.addMarker();
    assertFalse(cell.hasCorrectGuess());
  }

  @Test
  void testGetAdjacentHexagon() {
    Board board = new Board();
    Cell cell1 = board.getCells()[1][1];

    Cell cell2 = board.getCells()[1][2];
    assertEquals(cell1.getAdjacentHexagon(Direction.LEFT_RIGHT), cell2);
    Cell cell3 = board.getCells()[2][2];
    assertEquals(cell1.getAdjacentHexagon(Direction.DOWN_RIGHT), cell3);
    Cell cell4 = board.getCells()[2][1];
    assertEquals(cell1.getAdjacentHexagon(Direction.DOWN_LEFT), cell4);
    Cell cell5 = board.getCells()[1][0];
    assertEquals(cell1.getAdjacentHexagon(Direction.RIGHT_LEFT), cell5);
    Cell cell6 = board.getCells()[0][0];
    assertEquals(cell1.getAdjacentHexagon(Direction.UP_LEFT), cell6);
    Cell cell7 = board.getCells()[0][1];
    assertEquals(cell1.getAdjacentHexagon(Direction.UP_RIGHT), cell7);

    assertEquals(cell1.getAdjacentHexagon(Direction.UP_LEFT).getIndex()[1], 0);
    assertEquals(cell1.getAdjacentHexagon(Direction.UP_LEFT).getIndex()[1], 0);
  }

  @Test
  void testAtomRelations() {
    Board board = new Board();

    Cell cell = board.getCells()[1][1];
    Polygon hexagon = cell.createHexagon(40);
    assertFalse(cell.hasAtom());

    cell.addAtom();
    assertTrue(cell.hasAtom());

    Atom atom = new Atom(0, 0);
    assertEquals(cell.getAtom().getClass(), atom.getClass());
  }

  @Test
  void testMidpoint() {
    Cell cell = new Cell(4, 5);
    double[] result = cell.midpoint(2, 10, 4, 12);
    assertEquals(3, result[0]);
    assertEquals(11, result[1]);

    double[] result2 = cell.midpoint(-6, 7, 6, 10);
    assertEquals(0, result2[0]);
    assertEquals(8.5, result2[1]);
    assertNotEquals(8, result2[1]);
  }

  @Test
  void testCellEquals() {
    Cell cell1 = new Cell(10, 10);
    Cell cell2 = new Cell(10, 10);

    Cell cell3 = new Cell(30, 30);
    Cell cell4 = new Cell(40, 40);

    COI coi1 = null;
    COI coi2 = new COI(40, 40);

    assertTrue(cell1.equals(cell1));
    assertTrue(cell2.equals(cell2));
    assertFalse(cell3.equals(coi1));
    assertFalse(cell4.equals(coi2));
    assertEquals(cell1.equals(cell2), cell2.equals(cell1));
  }

  @Test
  void testMarkerScoreProperty() {
    Cell cell = new Cell(1, 1);
    cell.addAtom();
    cell.addMarker();

    assertTrue(cell.hasCorrectGuess());
  }

  @Test
  void testSlopeToDirection() {

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};

    double[] p3 = {2, 0};
    double[] p4 = {3, 0};

    double[] p5 = {0, 1};
    double[] p6 = {1, 0};

    Ray ray = new Ray();

    assertEquals(Direction.LEFT_RIGHT, ray.slopeToDirection(p3, p4));
    assertEquals(Direction.RIGHT_LEFT, ray.slopeToDirection(p4, p3));

    assertEquals(Direction.UP_LEFT, ray.slopeToDirection(p2, p1));
    assertEquals(Direction.DOWN_LEFT, ray.slopeToDirection(p4, p2));

    assertEquals(Direction.UP_RIGHT, ray.slopeToDirection(p5, p6));
    assertEquals(Direction.DOWN_RIGHT, ray.slopeToDirection(p1, p2));
  }

  @Test
  public void testDistanceBetween() {

    double[] p1 = {0, 0};
    double[] p2 = {3, 4};

    double[] p3 = {-2, 20};
    double[] p4 = {-5.7, 2};

    Ray ray = new Ray();

    assertEquals(5.0, ray.distanceBetween(p1, p2));
    assertEquals(18.376343488300385, ray.distanceBetween(p3, p4));
  }

  @Test
  public void testoneAtomReflection() {

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};

    double[] p3 = {2, 0};
    double[] p4 = {3, 0};

    Ray ray = new Ray();

    Direction directionLR = Direction.LEFT_RIGHT;
    Direction directionRL = Direction.RIGHT_LEFT;
    Direction directionUL = Direction.UP_LEFT;
    Direction directionDL = Direction.DOWN_LEFT;
    Direction directionUR = Direction.UP_RIGHT;
    Direction directionDR = Direction.DOWN_RIGHT;

    assertEquals(Direction.UP_RIGHT,
                 ray.oneAtomReflection(directionLR, p1, p2));
    assertEquals(Direction.DOWN_RIGHT,
                 ray.oneAtomReflection(directionLR, p2, p1));

    assertEquals(Direction.DOWN_LEFT,
                 ray.oneAtomReflection(directionRL, p2, p1));
    assertEquals(Direction.UP_LEFT, ray.oneAtomReflection(directionRL, p1, p2));

    assertEquals(Direction.UP_RIGHT,
                 ray.oneAtomReflection(directionUL, p4, p3));
    assertEquals(Direction.RIGHT_LEFT,
                 ray.oneAtomReflection(directionUL, p1, p2));

    assertEquals(Direction.DOWN_RIGHT,
                 ray.oneAtomReflection(directionDL, p4, p3));
    assertEquals(Direction.RIGHT_LEFT,
                 ray.oneAtomReflection(directionDL, p1, p2));

    assertEquals(Direction.UP_LEFT, ray.oneAtomReflection(directionUR, p3, p4));
    assertEquals(Direction.LEFT_RIGHT,
                 ray.oneAtomReflection(directionUR, p4, p3));

    assertEquals(Direction.DOWN_LEFT,
                 ray.oneAtomReflection(directionDR, p3, p4));
    assertEquals(Direction.LEFT_RIGHT,
                 ray.oneAtomReflection(directionDR, p4, p3));
  }

  @Test
  public void testtwoAtomReflection() {

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};
    double[] p3 = {2, 0};
    double[] p4 = {10, 10};
    double[] p5 = {80, 10};
    double[] p6 = {0, 0};

    Ray ray = new Ray();

    Direction directionLR = Direction.LEFT_RIGHT;
    Direction directionRL = Direction.RIGHT_LEFT;
    Direction directionUL = Direction.UP_LEFT;
    Direction directionDL = Direction.DOWN_LEFT;
    Direction directionUR = Direction.UP_RIGHT;
    Direction directionDR = Direction.DOWN_RIGHT;

    assertEquals(Direction.UP_LEFT,
                 ray.twoAtomReflection(directionLR, p1, p2, p3));
    assertEquals(Direction.DOWN_LEFT,
                 ray.twoAtomReflection(directionLR, p2, p1, p3));

    assertEquals(Direction.UP_RIGHT,
                 ray.twoAtomReflection(directionRL, p1, p2, p3));
    assertEquals(Direction.DOWN_RIGHT,
                 ray.twoAtomReflection(directionRL, p2, p1, p3));

    assertEquals(Direction.DOWN_RIGHT,
                 ray.twoAtomReflection(directionUR, p2, p1, p3));
    assertEquals(Direction.RIGHT_LEFT,
                 ray.twoAtomReflection(directionUR, p1, p2, p3));

    assertEquals(Direction.DOWN_LEFT,
                 ray.twoAtomReflection(directionUL, p2, p1, p3));
    assertEquals(Direction.LEFT_RIGHT,
                 ray.twoAtomReflection(directionUL, p1, p2, p3));

    assertEquals(Direction.UP_LEFT,
                 ray.twoAtomReflection(directionDL, p2, p1, p3));
    assertEquals(Direction.UP_RIGHT,
                 ray.twoAtomReflection(directionDL, p1, p2, p3));

    assertEquals(Direction.UP_RIGHT,
                 ray.twoAtomReflection(directionDR, p2, p1, p3));
    assertEquals(Direction.UP_LEFT,
                 ray.twoAtomReflection(directionDR, p1, p2, p3));

    // Full reflection method contains only switch cases
    // test 120 reflection method calls on full reflection, so we are
    // concurrently testing fullReflection
    assertEquals(Direction.RIGHT_LEFT,
                 ray.twoAtomReflection(directionLR, p4, p5, p6));
    assertEquals(Direction.LEFT_RIGHT,
                 ray.twoAtomReflection(directionRL, p4, p5, p6));
    assertEquals(Direction.DOWN_LEFT,
                 ray.twoAtomReflection(directionUR, p4, p5, p6));
    assertEquals(Direction.DOWN_RIGHT,
                 ray.twoAtomReflection(directionUL, p4, p5, p6));
    assertEquals(Direction.UP_LEFT,
                 ray.twoAtomReflection(directionDR, p4, p5, p6));
    assertEquals(Direction.UP_RIGHT,
                 ray.twoAtomReflection(directionDL, p4, p5, p6));
  }

  @Test
  public void testFullReflection() {

    Ray ray = new Ray();

    Direction directionLR = Direction.LEFT_RIGHT;
    Direction directionRL = Direction.RIGHT_LEFT;
    Direction directionUL = Direction.UP_LEFT;
    Direction directionDL = Direction.DOWN_LEFT;
    Direction directionUR = Direction.UP_RIGHT;
    Direction directionDR = Direction.DOWN_RIGHT;

    assertEquals(Direction.RIGHT_LEFT, ray.fullReflection(directionLR));
    assertEquals(Direction.LEFT_RIGHT, ray.fullReflection(directionRL));
    assertEquals(Direction.DOWN_LEFT, ray.fullReflection(directionUR));
    assertEquals(Direction.DOWN_RIGHT, ray.fullReflection(directionUL));
    assertEquals(Direction.UP_LEFT, ray.fullReflection(directionDR));
    assertEquals(Direction.UP_RIGHT, ray.fullReflection(directionDL));
  }

  @Test
  void testDrawRays() {
    Board board = new Board();
    board.getBoardGroup();

    Cell newCell = new Cell(4, 5);

    newCell.getAdjacentHexagon(Direction.LEFT_RIGHT);
    Polygon hexagon = newCell.createHexagon(40);

    newCell.addAtom();
    double[] start = {2, 1};

    Ray ray = new Ray(start, newCell, 4);

    ray.drawRays();
    assertEquals(ray.getLines().size(), ray.getCoords().size() - 1);
  }
}
