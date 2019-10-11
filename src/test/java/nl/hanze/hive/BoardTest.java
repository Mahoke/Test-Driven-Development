package nl.hanze.hive;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    //2c
    @Test
    public void whenNewGameNoTilesOnBoardThenTrue(){
        Board b = new Board();
        int tileCount = b.getBoardTiles().size();
        assertEquals(0, tileCount);
    }

    //2b
    @Test
    public void whenGivenCoordinateNeighboursEqualsThenTrue(){
        Hex h = new Hex(0, 0);
        int hq = h.getQ();
        int hr = h.getR();

        Board board = new Board();
        List<Hex> returnedNeighbours = board.getNeighbours(h);
        ArrayList<Hex> shouldBeNeighbours = new ArrayList<>();
        shouldBeNeighbours.add(new Hex(0,- 1));
        shouldBeNeighbours.add(new Hex(0,+ 1));
        shouldBeNeighbours.add(new Hex(+ 1,0));
        shouldBeNeighbours.add(new Hex(+ 1,- 1));
        shouldBeNeighbours.add(new Hex(- 1,0));
        shouldBeNeighbours.add(new Hex(- 1,+ 1));

        assertArrayEquals(shouldBeNeighbours.toArray(), returnedNeighbours.toArray());
    }
}
