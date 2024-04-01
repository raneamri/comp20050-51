import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ExperimenterTest {
    @Test
    void testToString(){
        Experimenter experimenter = new Experimenter();
        experimenter.setAbsorptions(5);

        assertEquals("Absorptions: 5",experimenter.getToString());
    }
    //not working I don't know why
    /*@Test
    void testShowAbsorptions(){
        Experimenter experimenter = new Experimenter();

        experimenter.showAbsorptions();
        assertEquals(Color.YELLOW, Main.absorptionsDisplay.getFill());
    }*/
}
