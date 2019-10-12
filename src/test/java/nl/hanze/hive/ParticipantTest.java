package nl.hanze.hive;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParticipantTest {

    //1c
    @Test
    public void creatingNewPlayerWhenTileSetEqualsStartTileSetThenTrue(){
        Participant p = new Participant(Hive.Player.BLACK);

        List<Tile> tiles = new ArrayList<>();
        for (Hive.Tile tile: HiveGame.startTileset) {
            tiles.add(new Tile(Hive.Player.BLACK, tile));
        }

        assertEquals(tiles, p.getAvailableTiles());
    }


}
