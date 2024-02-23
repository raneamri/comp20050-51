import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AtomandCOITest{

    @Test
    void testCreateAtom(){
        Atom newAtom = new Atom(40, 40);
        assertNotNull(newAtom);
        assertEquals(Color.RED, newAtom.getFill());

        newAtom.coi = new COI(40, 40);
        assertNotNull(newAtom.coi);
        assertEquals(Color.TRANSPARENT, newAtom.coi.getFill());
        assertEquals(Color.WHITE, newAtom.coi.getStroke());

        Atom newAtom2 = new Atom(35, 37);
        assertNotNull(newAtom2);
        assertEquals(Color.RED, newAtom2.getFill());


    }

    @Test
    void testEquals(){
        Atom atom1 = new Atom(10, 10);
        Atom atom2 = new Atom(10, 10);

        Atom atom3 = new Atom(30, 30);
        Atom atom4 = new Atom(40, 40);

        COI coi1 = null;
        COI coi2 = new COI(40, 40);

        assertTrue(atom1.equals(atom1));
        assertTrue(atom1.equals(atom2));
        assertFalse(atom3.equals(coi1));
        assertFalse(atom4.equals(coi2));
        assertEquals(atom1.equals(atom2), atom2.equals(atom1));
    }

}