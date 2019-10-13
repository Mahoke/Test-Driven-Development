package nl.hanze.hive;
import nl.hanze.hive.Tile;
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

    private boolean whiteIsMoving;
    private Participant blackParticipant;
    private Participant whiteParticipant;
    private Board board;

    public HiveGame(){
        this.blackParticipant = new Participant(Hive.Player.BLACK);
        this.whiteParticipant = new Participant(Hive.Player.WHITE);
        this.whiteIsMoving = true;
        this.board = new Board();
    }


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
        Participant p = (this.whiteIsMoving) ? whiteParticipant : blackParticipant;

        if (this.board.isHexEmpty(q,r) && p.canPlayTile(tile)) {
            this.board.play(tile, p.getColor(), q, r);
            p.removeTileFromAvailableTiles(tile);
            this.whiteIsMoving = !this.whiteIsMoving;
        } else {
            throw new IllegalMove();
        }

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
        this.whiteIsMoving = !this.whiteIsMoving;
    }

    /**
     * Pass the turn.
     *
     * @throws IllegalMove If the turn could not be passed
     */
    @Override
    public void pass() throws IllegalMove {
        this.whiteIsMoving = !this.whiteIsMoving;
    }

    /**
     * Check whether the given player is the winner.
     *
     * @param player Player to check
     * @return Boolean
     */
    @Override
    public boolean isWinner(Player player) {
        if (this.isDraw()) return false;
        Player opp = (player == Player.BLACK) ? Player.WHITE : Player.BLACK;
        return board.isQueenBeeSurrounded(opp);
    }

    /**
     * Check whether the game is a draw.
     *
     * @return Boolean
     */
    @Override
    public boolean isDraw() {
        return board.isQueenBeeSurrounded(Player.BLACK) && board.isQueenBeeSurrounded(Player.WHITE);
    }

    public Participant getParticipantToMove() {
        return this.whiteIsMoving ? this.whiteParticipant : this.blackParticipant;
    }
}
