package nl.hanze.hive;

public class HiveGame implements Hive {

    final static Hive.Tile[] startTileset = {
            Hive.Tile.QUEEN_BEE,
            Hive.Tile.SPIDER,
            Hive.Tile.SPIDER,
            Hive.Tile.BEETLE,
            Hive.Tile.BEETLE,
            Hive.Tile.GRASSHOPPER,
            Hive.Tile.GRASSHOPPER,
            Hive.Tile.GRASSHOPPER,
            Hive.Tile.SOLDIER_ANT,
            Hive.Tile.SOLDIER_ANT,
            Hive.Tile.SOLDIER_ANT,
    };
    /**
     * Play a new tile.
     *
     * @param tile Tile to play
     * @param q    Q coordinate of hexagon to play to
     * @param r    R coordinate of hexagon to play to
     * @throws IllegalMove If the tile could not be played
     */
    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {

    }

    /**
     * Move an existing tile.
     *
     * @param fromQ Q coordinate of the tile to move
     * @param fromR R coordinate of the tile to move
     * @param toQ   Q coordinate of the hexagon to move to
     * @param toR   R coordinare of the hexagon to move to
     * @throws IllegalMove If the tile could not be moved
     */
    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {

    }

    /**
     * Pass the turn.
     *
     * @throws IllegalMove If the turn could not be passed
     */
    @Override
    public void pass() throws IllegalMove {

    }

    /**
     * Check whether the given player is the winner.
     *
     * @param player Player to check
     * @return Boolean
     */
    @Override
    public boolean isWinner(Player player) {
        return false;
    }

    /**
     * Check whether the game is a draw.
     *
     * @return Boolean
     */
    @Override
    public boolean isDraw() {
        return false;
    }
}