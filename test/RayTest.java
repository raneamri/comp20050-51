import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

public class RayTest {
  @Test
  void testSlopeToDirection() {

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};

    double[] p3 = {2,0};
    double[] p4 = {3,0};

    double[] p5 = {0,1};
    double[] p6 = {1,0};

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

    double[] p3 = {-2,20};
    double[] p4 = {-5.7,2};

    Ray ray = new Ray();

    assertEquals(5.0, ray.distanceBetween(p1, p2));
    assertEquals(18.376343488300385, ray.distanceBetween(p3, p4));
  }

  @Test
  public void testSixtyReflection() {

    double[] p1 = {0, 0};
    double[] p2 = {1, 1};

    double[] p3 = {2,0};
    double[] p4 = {3,0};

    Ray ray = new Ray();

    Direction directionLR = Direction.LEFT_RIGHT;
    Direction directionRL = Direction.RIGHT_LEFT;
    Direction directionUL = Direction.UP_LEFT;
    Direction directionDL = Direction.DOWN_LEFT;
    Direction directionUR = Direction.UP_RIGHT;
    Direction directionDR = Direction.DOWN_RIGHT;

    assertEquals(Direction.UP_RIGHT, ray.sixtyReflection(directionLR, p1, p2));
    assertEquals(Direction.DOWN_RIGHT, ray.sixtyReflection(directionLR, p2, p1));

    assertEquals(Direction.DOWN_LEFT, ray.sixtyReflection(directionRL, p2, p1));
    assertEquals(Direction.UP_LEFT, ray.sixtyReflection(directionRL, p1, p2));

    assertEquals(Direction.UP_RIGHT, ray.sixtyReflection(directionUL, p4, p3));
    assertEquals(Direction.RIGHT_LEFT, ray.sixtyReflection(directionUL, p1, p2));

    assertEquals(Direction.DOWN_RIGHT, ray.sixtyReflection(directionDL, p4, p3));
    assertEquals(Direction.RIGHT_LEFT, ray.sixtyReflection(directionDL, p1, p2));

    assertEquals(Direction.UP_LEFT, ray.sixtyReflection(directionUR, p3, p4));
    assertEquals(Direction.LEFT_RIGHT, ray.sixtyReflection(directionUR, p4, p3));

    assertEquals(Direction.DOWN_LEFT, ray.sixtyReflection(directionDR, p3, p4));
    assertEquals(Direction.LEFT_RIGHT, ray.sixtyReflection(directionDR, p4, p3));
  }


  @Test
  public void testOnetwentyReflection() {

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

    assertEquals(Direction.UP_LEFT, ray.onetwentyReflection(directionLR, p1, p2, p3));
    assertEquals(Direction.DOWN_LEFT, ray.onetwentyReflection(directionLR, p2, p1, p3));

    assertEquals(Direction.UP_RIGHT, ray.onetwentyReflection(directionRL, p1, p2, p3));
    assertEquals(Direction.DOWN_RIGHT, ray.onetwentyReflection(directionRL, p2, p1, p3));

    assertEquals(Direction.DOWN_RIGHT, ray.onetwentyReflection(directionUR, p2, p1, p3));
    assertEquals(Direction.RIGHT_LEFT, ray.onetwentyReflection(directionUR, p1, p2, p3));

    assertEquals(Direction.DOWN_LEFT, ray.onetwentyReflection(directionUL, p2, p1, p3));
    assertEquals(Direction.LEFT_RIGHT, ray.onetwentyReflection(directionUL, p1, p2, p3));

    assertEquals(Direction.UP_LEFT, ray.onetwentyReflection(directionDL, p2, p1, p3));
    assertEquals(Direction.UP_RIGHT, ray.onetwentyReflection(directionDL, p1, p2, p3));

    assertEquals(Direction.UP_RIGHT, ray.onetwentyReflection(directionDR, p2, p1, p3));
    assertEquals(Direction.UP_LEFT, ray.onetwentyReflection(directionDR, p1, p2, p3));

    //Full reflection method contains only switch cases
    //test 120 reflection method calls on full reflection, so we are concurrently testing fullReflection
    assertEquals(Direction.RIGHT_LEFT, ray.onetwentyReflection(directionLR,p4,p5,p6));
    assertEquals(Direction.LEFT_RIGHT, ray.onetwentyReflection(directionRL,p4,p5,p6));
    assertEquals(Direction.DOWN_LEFT, ray.onetwentyReflection(directionUR,p4,p5,p6));
    assertEquals(Direction.DOWN_RIGHT, ray.onetwentyReflection(directionUL,p4,p5,p6));
    assertEquals(Direction.UP_LEFT, ray.onetwentyReflection(directionDR,p4,p5,p6));
    assertEquals(Direction.UP_RIGHT, ray.onetwentyReflection(directionDL,p4,p5,p6));
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

    assertEquals(Direction.RIGHT_LEFT,ray.fullReflection(directionLR));
    assertEquals(Direction.LEFT_RIGHT,ray.fullReflection(directionRL));
    assertEquals(Direction.DOWN_LEFT,ray.fullReflection(directionUR));
    assertEquals(Direction.DOWN_RIGHT,ray.fullReflection(directionUL));
    assertEquals(Direction.UP_LEFT,ray.fullReflection(directionDR));
    assertEquals(Direction.UP_RIGHT,ray.fullReflection(directionDL));

  }
  
  @Test
  void drawRays(){
    test_src.Board board = new test_src.Board();
    board.getBoardGroup();

    test_src.Cell newCell = new test_src.Cell(4, 5);

    newCell.getAdjacentHexagon(test_src.Direction.LEFT_RIGHT);
    Polygon hexagon = newCell.createHexagon(40);

    newCell.addAtom();
    double[] start = {2,1};

    test_src.Ray ray = new test_src.Ray(start,newCell,4);

    ray.drawRays();
    assertEquals(ray.lines.size(),ray.coords.size()-1);

  }

}
















