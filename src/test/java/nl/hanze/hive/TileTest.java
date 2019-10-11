package nl.hanze.hive;

import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;


public class TileTest {
    //1a
    @Test
    public void givenColorWhenEqualsThenTrue() {
        Tile a = new Tile(Hive.Player.BLACK);
        assertEquals(Hive.Player.BLACK, a.getPlayer());
        Tile b = new Tile(Hive.Player.WHITE);
        assertEquals(Hive.Player.WHITE, b.getPlayer());
    }

    @Test
    public void givenDifferentColorWhenNotEqualThenTrue(){
        Tile a = new Tile(Hive.Player.BLACK);
        Tile b = new Tile(Hive.Player.WHITE);
        assertNotEquals(a.getPlayer(), b.getPlayer());
    }

    //1b
    @Test
    public void givenPictureQueenBeeWhenEqualsThenTrue(){
        Tile a = new Tile(Hive.Tile.QUEEN_BEE);
        assertEquals(Hive.Tile.QUEEN_BEE, a.getTile());
    }
}
