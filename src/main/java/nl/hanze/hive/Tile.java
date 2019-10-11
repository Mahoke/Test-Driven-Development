package nl.hanze.hive;

import java.util.Objects;

public class Tile {

    Hive.Player player;
    Hive.Tile tile;

    public Tile(Hive.Player player){
        this(player, Hive.Tile.QUEEN_BEE);
    }

    public Tile(Hive.Tile tile){
        this(Hive.Player.BLACK, tile);
    }

    public Tile(Hive.Player player, Hive.Tile tile){
        this.tile = tile;
        this.player = player;
    }

    public Hive.Player getPlayer() {
        return player;
    }

    public void setPlayer(Hive.Player player) {
        this.player = player;
    }

    public Hive.Tile getTile() {
        return tile;
    }

    public void setTile(Hive.Tile tile) {
        this.tile = tile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return player == tile.player;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

}