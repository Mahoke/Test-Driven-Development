package nl.hanze.hive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Participant {
    List<Tile> availableTiles;
    List<Tile> tilesOnBoard;
    private Hive.Player color;

    public Participant(Hive.Player color){
        this.color = color;
        this.assignStartTilesetToAvailableTiles();
        this.tilesOnBoard = new ArrayList<>();
    }

    public Participant(Hive.Player color, List<Tile> availableTiles, List<Tile> tilesOnBoard){
        this.color = color;
        this.availableTiles = availableTiles;
        this.tilesOnBoard = tilesOnBoard;
    }

    private void assignStartTilesetToAvailableTiles() {
        this.availableTiles = new ArrayList<Tile>();
        for (Hive.Tile t : HiveGame.startTileset ){
            availableTiles.add(new Tile(this.color, t));
        }
    }

    public List<Tile> getAvailableTiles(){
        return availableTiles;
    }


    public List<Tile> getTilesOnBoard() {
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
