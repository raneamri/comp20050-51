import javafx.scene.Node;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;



public class BoardTest {
    final int NUM_ROWS = 9;

    @Test
    void testCreateBoard(){
        Board newBoard = new Board();

        assertNotNull(newBoard.getCells());//Ensuring cells are created
        assertEquals(NUM_ROWS, newBoard.getCells().length);//Ensuring the no of rows is correct

        // Verify coordinates
        for(int row = 0; row < NUM_ROWS/2; row++){
            int expectedNumHexagons = row+5;
            for(int col = 0; col < expectedNumHexagons; col++){
                assertEquals(row, newBoard.getCells()[row][col].coords[0]);
                assertEquals(col, newBoard.getCells()[row][col].coords[1]);
            }
        }

    }

    @Test
    void testGetBoardGroup(){
        Board newBoard = new Board();

        newBoard.getBoardGroup();

        boolean containsPolygon = false;

        for(Node node : Main.getGroup().getChildren()){
            if(node instanceof Polygon){
                containsPolygon = true;
                break;
            }
        }
        //Ensure the hexagons are being added to main's group correctly
        assertTrue(containsPolygon);
    }

    @Test
    void testGetNumHexagonsInRow(){
        Board newBoard = new Board();

        int expectedHexagons = 5;
        assertEquals(expectedHexagons, newBoard.getCells()[0].length);

        expectedHexagons = 6;
        assertEquals(expectedHexagons, newBoard.getCells()[1].length);

        expectedHexagons = 9;
        assertEquals(expectedHexagons, newBoard.getCells()[4].length);
    }
}
