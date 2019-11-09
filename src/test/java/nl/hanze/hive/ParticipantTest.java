package nl.hanze.hive;
import nl.hanze.hive.tiles.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParticipantTest {

    //1c
    @Test
    public void creatingNewPlayerWhenTileSetEqualsStartTileSetThenTrue(){
        Participant p = new Participant(Hive.Player.BLACK);

        List<InsectTile> tiles = new ArrayList<>();
        for (Hive.Tile tile: HiveGame.startTileset) {
            InsectTile o = null;
            switch (tile) {
                case BEETLE:
                    o = new BeetleTile(Hive.Player.BLACK);
                    break;
                case GRASSHOPPER:
                    o = new GrasshopperTile(Hive.Player.BLACK);
                    break;
                case QUEEN_BEE:
                    o = new QueenBeeTile(Hive.Player.BLACK);
                    break;
                case SOLDIER_ANT:
                    o = new SoldierAntTile(Hive.Player.BLACK);
                    break;
                case SPIDER:
                    o = new SpiderTile(Hive.Player.BLACK);
                    break;
            }
            tiles.add(o);
        }

        assertTrue(tiles.equals(p.getAvailableTiles()));
    }


}
