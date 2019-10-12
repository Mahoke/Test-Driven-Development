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

    @Test
    public void givenPictureSpiderWhenEqualsThenTrue(){
        Tile a = new Tile(Hive.Tile.SPIDER);
        assertEquals(Hive.Tile.SPIDER, a.getTile());
    }

    @Test
    public void givenPictureBeetleWhenEqualsThenTrue(){
        Tile a = new Tile(Hive.Tile.BEETLE);
        assertEquals(Hive.Tile.BEETLE, a.getTile());
    }

    @Test
    public void givenPictureSoldierAntWhenEqualsThenTrue(){
        Tile a = new Tile(Hive.Tile.SOLDIER_ANT);
        assertEquals(Hive.Tile.SOLDIER_ANT, a.getTile());
    }

    @Test
    public void givenPictureGrasshopperWhenEqualsThenTrue(){
        Tile a = new Tile(Hive.Tile.GRASSHOPPER);
        assertEquals(Hive.Tile.GRASSHOPPER, a.getTile());
    }
}
