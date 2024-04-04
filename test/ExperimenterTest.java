import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
public class ExperimenterTest {
  @Test
  void testToString() {
    Experimenter experimenter = new Experimenter();
    experimenter.setAbsorptions(5);

    assertEquals("Absorptions: 5", experimenter.getToString());
  }

  /*@Test
  void testShowAbsorptions(){
      Experimenter experimenter = new Experimenter();

      experimenter.showAbsorptions();
      assertEquals(Color.YELLOW, Main.absorptionsDisplay.getFill());
  }*/
}
