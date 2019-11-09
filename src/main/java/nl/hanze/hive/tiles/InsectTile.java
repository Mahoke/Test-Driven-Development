package nl.hanze.hive.tiles;

import nl.hanze.hive.Board;
import nl.hanze.hive.Hex;
import nl.hanze.hive.Hive;

import java.util.List;
import java.util.Objects;

public class InsectTile {

    Hive.Player player;
    Hive.Tile tile;

    public InsectTile(Hive.Player player){
        this(player, Hive.Tile.QUEEN_BEE);
    }

    public InsectTile(Hive.Tile tile){
        this(Hive.Player.BLACK, tile);
    }

    public InsectTile(Hive.Player player, Hive.Tile tile){
        this.tile = tile;
        this.player = player;
    }

    public InsectTile(Hive.Tile tile, Hive.Player player){
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
        InsectTile tile1 = (InsectTile) o;
        return player == tile1.player &&
                tile == tile1.tile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, tile);
    }

}