package nl.hanze.hive;

import nl.hanze.hive.tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Participant {
    List<InsectTile> availableTiles;
    List<InsectTile> tilesOnBoard;
    private Hive.Player color;

    public Participant(Hive.Player color){
        this.color = color;
        this.assignStartTilesetToAvailableTiles();
        this.tilesOnBoard = new ArrayList<>();
    }

    private void assignStartTilesetToAvailableTiles() {
        this.availableTiles = new ArrayList<InsectTile>();
        for (Hive.Tile t : HiveGame.startTileset ){
            InsectTile toAdd = createTileObjectFromEnum(t);
            availableTiles.add(toAdd);
        }
    }

    private InsectTile createTileObjectFromEnum(Hive.Tile t){
        InsectTile o = null;
        switch (t) {
            case BEETLE:
                o = new BeetleTile(this.color);
                break;
            case GRASSHOPPER:
                o = new GrasshopperTile(this.color);
                break;
            case QUEEN_BEE:
                o = new QueenBeeTile(this.color);
                break;
            case SOLDIER_ANT:
                o = new SoldierAntTile(this.color);
                break;
            case SPIDER:
                o = new SpiderTile(this.color);
                break;
        }
        return o;
    }

    public boolean canPlayTile(Hive.Tile tile){
        InsectTile t = createTileObjectFromEnum(tile);
        return availableTiles.contains(t);
    }

    public void removeTileFromAvailableTiles(Hive.Tile tile){
        InsectTile t = createTileObjectFromEnum(tile);
        this.availableTiles.remove(t);
        this.tilesOnBoard.add(t);
    }

    public List<InsectTile> getAvailableTiles(){
        return availableTiles;
    }


    public List<InsectTile> getTilesOnBoard() {
        return tilesOnBoard;
    }

    public Hive.Player getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
