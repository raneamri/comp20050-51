import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

public class CellTest {

  @Test
  void testCreateHexagon() {
    Cell newCell = new Cell(4, 5);
    Polygon hexagon = newCell.createHexagon(40);

    MouseEvent mouseEvent = new MouseEvent(
        MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false,
        false, false, false, false, false, false, false, false, false, null);

    newCell.getHexagon().fireEvent();

    assertNotNull(hexagon);
    assertEquals(Color.TRANSPARENT, hexagon.getFill());
    assertEquals(Color.RED, hexagon.getStroke());
  }

  @Test
  void testCorrectGuess() {
    Cell cell = new Cell(1, 1);
    cell.addAtom();
    cell.addMarker();

    assertTrue(cell.hasCorrectGuess());

    cell = new Cell(1, 1);
    cell.addMarker();

    assertFalse(cell.hasCorrectGuess());
  }

  @Test
  void testGetAdjacentHexagon() {
    /*
     * Write assert trues for getAdjacentHexagon()
     *
     * Here's an example one I did
     *
     * The ones for other directions will be a bit more complicated but just
     * look at the board to help yourself
     */

    Board board = new Board();
    Cell cell1 = board.getCells()[1][1];
    Cell cell2 = board.getCells()[1][2];

    assertEquals(cell1.getAdjacentHexagon(Direction.LEFT_RIGHT).getIndex,
                 cell2.getIndex());
  }

  @Test
  void testAtomRelations() {
    Cell cell = new Cell(1, 1);

    assertFalse(cell.hasAtom());
    cell.addAtom();
    assertTrue(cell.hasAtom());
    Atom atom = new Atom(0, 0);
    assertEquals(cell.getAtom().getClass(), atom.getClass());
  }
}