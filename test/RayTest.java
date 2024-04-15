import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

public class RayTest {
  @Test
  void testSlopeToDirection() {
    /*
     * View slopeToDirection() in Ray
     * write a few tests that take a hardcoded slope and make sure they
     * consistently and succesfully translate it to the Direction type
     */
  }

  @Test
  void testSixtyReflection() {}


  @Test
  public void testOnetwentyReflection() {}

  @Test
  public void testFullReflection(){}

  @Test
  void testDrawRays() {
    /*
     * Instantiate coords ArrayList
     * Run drawRays, this will fill the lines ArrayList
     *
     * Assert coords.size == lines.size - 1 (i think)
     */
  }

  /*
   * If you feel up to it write some test for the recursive function
   * checkCollisions() It's a complex function which would tick off the
   * non-trivial testing in the marking rubric I understand it's hard so it's up
   * to you
   */
}







/*

  @Test
  public void testSlopeToDirection() {
    Board board = new Board();
    Cell[][] cells = Board.getCells();
    double[] start = {1,2};

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};

    Ray ray = new Ray(start, cells[1][0], 10);
    assertEquals(Direction.DOWN_RIGHT, ray.slopeToDirection(p1, p2));
  }




  @Test
  public void testDistanceBetween() {
    Board board = new Board();
    Group group = board.getBoardGroup();
    Cell[][] cells = Board.getCells();
    double[] start = {1,2};

    double[] p1 = {0, 0};
    double[] p2 = {3, 4};
    Ray ray = new Ray(start, cells[1][0], 11);
    assertEquals(5.0, ray.distanceBetween(p1, p2));
  }

  @Test
  public void testSixtyReflection() {
    Board board = new Board();
    Group group = board.getBoardGroup();
    Cell[][] cells = Board.getCells();
    double[] start = {1,2};

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};
    Ray ray = new Ray(start, cells[1][0], 11);
    Direction direction = Direction.LEFT_RIGHT;
    assertEquals(Direction.UP_RIGHT, ray.sixtyReflection(direction, p1, p2));
  }

  @Test
  public void testOnetwentyReflection() {
    Board board = new Board();
    Group group = board.getBoardGroup();
    Cell cells[][] = Board.getCells();
    double start[] = {1,2};

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};
    double[] p3 = {2, 0};

    Ray ray = new Ray(start, cells[1][0], 10);
    Direction direction = Direction.LEFT_RIGHT;
    assertEquals(Direction.UP_LEFT, ray.onetwentyReflection(direction, p1, p2, p3));
  }

  @Test
  public void testFullReflection() {
    Board board = new Board();
    Group group = board.getBoardGroup();
    Cell[][] cells = Board.getCells();
    double[] start = {1,2};

    Ray ray = new Ray(start, cells[1][0], 12);
    Direction direction = Direction.LEFT_RIGHT;
    assertEquals(Direction.RIGHT_LEFT, ray.fullReflection(direction));
  }*/
