package nl.hanze.hive;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Tile> availableTiles;
    private Hive.Player color;

    public Player(Hive.Player color){
        this.color = color;
        this.availableTiles = new ArrayList<Tile>();
        assignStartTilesetToAvailableTiles();
    }

    private void assignStartTilesetToAvailableTiles() {
        for (Hive.Tile t : HiveGame.startTileset ){
            availableTiles.add(new Tile(this.color, t));
        }
    }

    public List<Tile> getAvailableTiles(){
        return availableTiles;
    }


}
