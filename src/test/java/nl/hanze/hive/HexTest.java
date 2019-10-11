package nl.hanze.hive;
import org.junit.Test;
import static org.junit.Assert.*;

public class HexTest {

    @Test
    public void whenGivenCoordinateEqualsThenTrue(){
        Hex hex = new Hex(0, 0);
        int[] qr = {hex.getQ(),hex.getR()};

        assertArrayEquals(new int[]{0, 0}, qr);
    }

}
