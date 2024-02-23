import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    void testCreateHexagon(){
        Cell newCell = new Cell();
        Polygon hexagon = newCell.createHexagon(40);

        assertNotNull(hexagon);
        assertEquals(Color.TRANSPARENT, hexagon.getFill());
        assertEquals(Color.RED, hexagon.getStroke());
    }

    //Debugging fractures for mouse event in cell class

}