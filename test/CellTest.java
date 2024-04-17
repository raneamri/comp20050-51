import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;
import test_src.Main;

public class CellTest {

  @Test
  void testCreateHexagon() {
    test_src.Cell newCell = new test_src.Cell(4, 5);
    Polygon hexagon = newCell.createHexagon(40);

    assertEquals(Color.RED, hexagon.getStroke());
    assertEquals(Color.TRANSPARENT, hexagon.getFill());
    assertNotNull(hexagon);

    test_src.Cell newCell2 = new test_src.Cell(6, 2);
    Polygon hexagon2 = newCell2.createHexagon(40);
    assertEquals(Color.RED, hexagon2.getStroke());
    assertEquals(Color.TRANSPARENT, hexagon2.getFill());

  }

  @Test
  void testCellMouseEvents(){
    /*Test to see if atom id added correctly when hex is clicked and gamestage is Setter*/
    Main.experimenter = new test_src.Experimenter();
    test_src.Cell newCell = new test_src.Cell(4, 5);
    Polygon hexagon = newCell.createHexagon(40);

    MouseEvent mouseEvent = new MouseEvent(
            MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false,
            false, false, false, false, false, false, false, false, false, null);

    newCell.getHexagon().fireEvent(mouseEvent);
    assertTrue(newCell.hasAtom());

    /*Test to see if atom is not added when hex is clicked and gamestage is Rays*/
    Main.atoms.add(new test_src.Atom(3,6));
    Main.atoms.add(new test_src.Atom(3,2));
    Main.atoms.add(new test_src.Atom(3,4));
    Main.atoms.add(new test_src.Atom(3,1));

    test_src.Cell newCell2 = new test_src.Cell(4, 5);
    Polygon hexagon2 = newCell2.createHexagon(40);
    MouseEvent mouseEvent2 = new MouseEvent(
            MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false,
            false, false, false, false, false, false, false, false, false, null);

    newCell2.getHexagon().fireEvent(mouseEvent2);
    assertFalse(newCell2.hasAtom());


    /*Test to see if atom id added correctly when hex is clicked and gamestage is Marker*/
    Main.gameStage = Main.GameStage.MARKERS;

    test_src.Cell newCell3 = new test_src.Cell(2, 1);
    Polygon hexagon3 = newCell3.createHexagon(40);
    MouseEvent mouseEvent3 = new MouseEvent(
            MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false,
            false, false, false, false, false, false, false, false, false, null);


    newCell3.getHexagon().fireEvent(mouseEvent3);
    assertTrue(newCell3.hasMarker);

    /*Test to see if hex lights up correctly when hovered on*/
    MouseEvent mouseEvent4 = new MouseEvent(
            MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false,
            false, false, false, false, false, false, false, false, false, null);
    newCell3.getHexagon().fireEvent(mouseEvent4);
    assertEquals(hexagon3.getFill().toString(), Color.color(0.1, 0.1, 0.1).toString());

  }

  @Test
  void testCorrectGuess() {
    test_src.Cell cell = new test_src.Cell(1, 1);
    Polygon hexagon = cell.createHexagon(40);
    cell.addAtom();
    cell.addMarker();

    assertTrue(cell.hasCorrectGuess());

    cell = new test_src.Cell(1, 1);
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
    test_src.Board board = new test_src.Board();

    test_src.Cell cell = board.getCells()[1][1];
    Polygon hexagon = cell.createHexagon(40);
    assertFalse(cell.hasAtom());

    cell.addAtom();
    assertTrue(cell.hasAtom());

    test_src.Atom atom = new test_src.Atom(0, 0);
    assertEquals(cell.getAtom().getClass(), atom.getClass());
  }

  @Test
  void testMidPoint(){
    Cell newCell = new Cell(3, 4);
    double mid[] = newCell.midpoint(2, 3, 4, 5);
    assertEquals(mid[0], 3);
    assertEquals(mid[1], 4);

    mid = newCell.midpoint(3.5, 8.3, 2.3, 7);
    assertEquals(mid[0], 2.9);
    assertEquals(mid[1], 7.65);

  }

  @Test
  void testEquals() {
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
}