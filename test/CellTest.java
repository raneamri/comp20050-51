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

    MouseEvent mouseEvent =
        new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
                       MouseEvent.MOUSE_CLICKED, 1, false, false, false, false,
                       false, false, false, false, false, false, null);

    newCell.getHexagon().fireEvent(mouseEvent);

    assertNotNull(hexagon);
    assertEquals(Color.TRANSPARENT, hexagon.getFill());
    assertEquals(Color.RED, hexagon.getStroke());
  }

  // Debugging fractures for mouse event in cell class
}