import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

public class TorchTest {

  @Test
  void testTorch() {
    Cell cell = new Cell(4, 5);

    Polygon hexagon = cell.createHexagon(40);
    Torch torch = new Torch(cell, 0,2);

    assertNotNull(torch);
    assertNotNull(torch.getInteractable());
    assertEquals(Color.RED, torch.getInteractable().getFill());
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
}