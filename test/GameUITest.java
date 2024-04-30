package test;

import blackbox.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameUITest {
  @Test
  void testCreateAtom() {
    Atom newAtom = new Atom(40, 40);
    assertNotNull(newAtom);
    assertEquals(Color.RED, newAtom.getFill());

    newAtom.coi = new COI(40, 40);
    assertNotNull(newAtom.coi);
    assertEquals(Color.TRANSPARENT, newAtom.coi.getFill());
    assertEquals(Color.color(0.9, 0.9, 0.9), newAtom.coi.getStroke());

    Atom newAtom2 = new Atom(35, 37);
    assertNotNull(newAtom2);
    assertEquals(Color.RED, newAtom2.getFill());
  }

  final int NUM_ROWS = 9;

  @Test
  void testCreateBoard() {
    Board newBoard = new Board();

    assertNotNull(newBoard.getCells()); // Ensuring cells are created
    assertEquals(
        NUM_ROWS,
        newBoard.getCells().length); // Ensuring the no of rows is correct

    // Verify coordinates
    for (int row = 0; row < NUM_ROWS / 2; row++) {
      int expectedNumHexagons = row + 5;
      for (int col = 0; col < expectedNumHexagons; col++) {
        assertEquals(row, newBoard.getCells()[row][col].coords[0]);
        assertEquals(col, newBoard.getCells()[row][col].coords[1]);
      }
    }
  }

  @Test
  void testCreateHexagon() {
    Cell newCell = new Cell(4, 5);
    Polygon hexagon = newCell.createHexagon(40);

    assertEquals(Color.RED, hexagon.getStroke());
    assertEquals(Color.TRANSPARENT, hexagon.getFill());
    assertNotNull(hexagon);

    Cell newCell2 = new Cell(6, 2);
    Polygon hexagon2 = newCell2.createHexagon(40);
    assertEquals(Color.RED, hexagon2.getStroke());
    assertEquals(Color.TRANSPARENT, hexagon2.getFill());
  }

  @Test
  void testCellMouseEvents() {
    MouseEvent mouseEvent = new MouseEvent(
            MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false,
            false, false, false, false, false, false, false, false, false, null);


    /*
     * Test to see if atom id added correctly when hex is clicked and game stage
     * is Setter
     */
    Experimenter experimenter = new Experimenter();
    Cell newCell = new Cell(4, 5);
    newCell.createHexagon(40);

    newCell.getHexagon().fireEvent(mouseEvent);
    assertTrue(newCell.hasAtom());

    Cell newCell2 = new Cell(4, 5);
    newCell2.createHexagon(40);
    newCell2.getHexagon().fireEvent(mouseEvent);

    assertTrue(newCell2.hasAtom());

    /*
     * Test to see if marker is added correctly when hex is clicked and game stage
     * is Marker
     */
    Cell newCell3 = new Cell(2, 1);
    newCell3.createHexagon(40);
    newCell3.getHexagon().fireEvent(mouseEvent);

    assertTrue(newCell3.hasMarker());

    /*Test to see if hex lights up correctly when hovered on*/
    newCell3.getHexagon().fireEvent(mouseEvent);
    assertEquals(newCell3.getHexagon().getFill().toString(),
                 Color.color(0.1, 0.1, 0.1).toString());
  }

  @Test
  void testToString() {
    Experimenter experimenter = new Experimenter();
    experimenter.setAbsorptions(5);

    assertEquals("Absorptions: 5", experimenter.getToString());
  }

  @Test
  void testFlag() {
    Pair<Double, Double> coords = new Pair<>(5.0, 10.0);
    Flag flag = new Flag(coords, 5);

    assertNotNull(flag);
    assertEquals(Color.BLUE, flag.getInteractable().getFill());
    assertEquals(Color.BLUEVIOLET, flag.getInteractable().getStroke());
    assertEquals(1, flag.getInteractable().getStrokeWidth());
  }

  @Test
  void testToggleOff() {
    Pair<Double, Double> coords = new Pair<>(5.0, 10.0);
    Flag flag = new Flag(coords, 5);
    flag.toggleOff();

    assertNotNull(flag);
    assertEquals(Color.TRANSPARENT, flag.getInteractable().getFill());
    assertEquals(Color.TRANSPARENT, flag.getInteractable().getStroke());
  }

  @Test
  void testMarker() {
    Pair<Double, Double> coords = new Pair<>(5.0, 10.0);
    Marker marker = new Marker(coords);
    Circle interactable = marker.getInteractable();

    assertNotNull(marker);
    assertEquals(Color.BLUE, interactable.getFill());
    assertEquals(Color.BLUEVIOLET, interactable.getStroke());
    assertEquals(3, interactable.getStrokeWidth());
  }

  @Test
  void testTorch() {
    Cell cell = new Cell(4, 5);

    Polygon hexagon = cell.createHexagon(40);
    Torch torch = new Torch(cell, 0, 2);

    assertNotNull(torch);
    assertNotNull(torch.getInteractable());
    assertEquals(Color.RED, torch.getInteractable().getFill());
  }
}
