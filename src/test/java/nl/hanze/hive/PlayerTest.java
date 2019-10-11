package nl.hanze.hive;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    //1c
    @Test
    public void creatingNewPlayerWhenTileSetEqualsStartTileSetThenTrue(){
        Player player = new Player(Hive.Player.BLACK);

        List<Tile> tiles = new ArrayList<>();
        for (Hive.Tile tile: HiveGame.startTileset) {
            tiles.add(new Tile(Hive.Player.BLACK, tile));
        }

        assertEquals(tiles, player.getAvailableTiles());
    }


}
