import javafx.scene.paint.Color;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

public class CellTest {

    @Test
    void testCreateHexagon(){
        Cell newCell = new Cell(4,5);
        Polygon hexagon = newCell.createHexagon(40);

        assertNotNull(hexagon);
        assertEquals(Color.TRANSPARENT, hexagon.getFill());
        assertEquals(Color.RED, hexagon.getStroke());
    }

    //Debugging fractures for mouse event in cell class

}