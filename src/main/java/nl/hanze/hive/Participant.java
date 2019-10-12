package nl.hanze.hive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Participant {
    List<Tile> availableTiles;
    private Hive.Player color;

    public Participant(Hive.Player color){
        this.color = color;
        this.assignStartTilesetToAvailableTiles();
    }

    public Participant(Hive.Player color, List<Tile> availableTiles){
        this.color = color;
        this.availableTiles = availableTiles;
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
