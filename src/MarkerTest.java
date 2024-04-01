import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;
import javafx.util.Pair;
import static org.junit.jupiter.api.Assertions.*;
public class MarkerTest {
    @Test
    void testMarker(){
        Pair<Double,Double> coords= new Pair<>(5.0,10.0);
        Marker marker = new Marker(coords);
        Circle interactable = marker.getInteractable();

        assertNotNull(marker);
        assertEquals(Color.BLUE, interactable.getFill());
        assertEquals(Color.BLUEVIOLET, interactable.getStroke());
        assertEquals(2, interactable.getStrokeWidth());
    }

}
