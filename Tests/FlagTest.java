import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;
import javafx.util.Pair;
import static org.junit.jupiter.api.Assertions.*;
public class FlagTest {
    @Test
    void testFlag(){
        Pair<Double,Double> coords= new Pair<>(5.0,10.0);
        Flag flag = new Flag(coords);
        Polygon interactable = flag.getInteractable();

        assertNotNull(flag);
        assertEquals(Color.BLUE, interactable.getFill());
        assertEquals(Color.BLUEVIOLET, interactable.getStroke());
        assertEquals(2, interactable.getStrokeWidth());
    }

    @Test
    void testToggleOff(){
        Pair<Double,Double> coords= new Pair<>(5.0,10.0);
        Flag flag = new Flag(coords);
        flag.toggleOff();
        Polygon interactable = flag.getInteractable();

        assertNotNull(flag);
        assertEquals(Color.TRANSPARENT, interactable.getFill());
        assertEquals(Color.TRANSPARENT, interactable.getStroke());

    }
}
