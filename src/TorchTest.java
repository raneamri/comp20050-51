import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TorchTest {

    @Test
    void testTorch()
    {
        Cell cell = new Cell();

        Polygon hexagon = cell.createHexagon(40);
        Torch torch = new Torch(cell,0);

        assertNotNull(torch);
        assertNotNull(torch.interactable);
        assertEquals(Color.RED, torch.interactable.getFill());
    }

    @Test
    void testRay()
    {
        Ray ray = new Ray(5,6,10,11);
        ray.drawRays();
        Line line = ray.getCreatedLine();

        assertNotNull(ray);
        assertEquals(Color.YELLOW, line.getFill());
        assertEquals(Color.YELLOW, line.getStroke());
    }

    @Test
    void testMidpoint()
    {
        Cell cell= new Cell();
        double[] result = cell.midPoint(2,10,4,12);
        assertEquals(3,result[0]);
        assertEquals(11,result[1]);
        
        double[] result2 = cell.midPoint(-6,7,6,10);
        assertEquals(0,result2[0]);
        assertEquals(8.5,result2[1]);
        assertNotEquals(8,result2[1]);
    }
}
