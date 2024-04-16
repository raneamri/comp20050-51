import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

public class FlagTest {
  @Test
  void testFlag() {
    Pair<Double, Double> coords = new Pair<>(5.0, 10.0);
    Flag flag = new Flag(coords,5);
    Polygon interactable = flag.getInteractable();

    assertNotNull(flag);
    assertEquals(Color.BLUE, interactable.getFill());
    assertEquals(Color.BLUEVIOLET, interactable.getStroke());
    assertEquals(2, interactable.getStrokeWidth());
  }

  @Test
  void testToggleOff() {
    Pair<Double, Double> coords = new Pair<>(5.0, 10.0);
    Flag flag = new Flag(coords,5);
    flag.toggleOff();
    Polygon interactable = flag.getInteractable();

    assertNotNull(flag);
    assertEquals(Color.TRANSPARENT, interactable.getFill());
    assertEquals(Color.TRANSPARENT, interactable.getStroke());
  }
}
