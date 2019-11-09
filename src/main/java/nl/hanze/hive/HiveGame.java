package nl.hanze.hive;

import nl.hanze.hive.tiles.InsectTile;
import nl.hanze.hive.tiles.QueenBeeTile;
import nl.hanze.hive.tiles.iMovable;

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
        this(new Board());
    }

    public HiveGame(Board state){
        this.blackParticipant = new Participant(Hive.Player.BLACK);
        this.whiteParticipant = new Participant(Hive.Player.WHITE);
        this.whiteIsMoving = true;
        this.board = state;
    }


    public Participant getBlackParticipant() {
        return blackParticipant;
    }

    public Participant getWhiteParticipant() {
        return whiteParticipant;
    }


    /**
     * Play a new tile.
     *
     * @param tile InsectTile to play
     * @param q    Q coordinate of hexagon to play to
     * @param r    R coordinate of hexagon to play to
     * @throws IllegalMove If the tile could not be played
     */
    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        Participant p = (this.whiteIsMoving) ? whiteParticipant : blackParticipant;

        if(this.board.getTileCount(p.getColor()) == 3 && tile != Tile.QUEEN_BEE && p.getAvailableTiles().contains(new QueenBeeTile(p.getColor()) ) ){
            throw new IllegalMove();
        }

        if (p.canPlayTile(tile) && this.board.canPlayTile(p.getColor(), tile, q, r)) {
            this.board.play(tile, p.getColor(), q, r);
            p.removeTileFromAvailableTiles(tile);

            this.board.updateTileCount(p);

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
        Participant p = (this.whiteIsMoving) ? whiteParticipant : blackParticipant;
        checkLegalMove(fromQ, fromR, toQ, toR, p);

        iMovable t = (iMovable) this.board.getTile(fromQ, fromR);
        boolean isValid = t.isValidMove(this.board, fromQ, fromR, toQ, toR);

        if (isValid) {
            this.board.doMove(fromQ, fromR, toQ, toR);
            this.whiteIsMoving = !this.whiteIsMoving;
        } else {
            throw new Hive.IllegalMove("This move is invalid");
        }
    }

    private void checkLegalMove(int fromQ, int fromR, int toQ, int toR, Participant p) throws IllegalMove {

        if (this.board.getTileCount(p.getColor()) == 0) throw new IllegalMove("No tiles on board");

        if (!p.getTilesOnBoard().contains(new QueenBeeTile(p.getColor()))) throw new IllegalMove("Can't move without the QueenBee on board.");

        InsectTile tile = this.board.getTile(fromQ, fromR);
        if (tile == null) throw new IllegalMove("Can't move nothing!");

        if (tile.getPlayer() != p.getColor()) throw new IllegalMove("Moving someone else's tile");

        if ( !board.hasLocationNeighbouringTilesAfterMoving(toQ,toR,fromQ,fromR) ) throw new IllegalMove("Can't move to a location without having neighbours there");

        if ( !board.legalBoardAfterMoving(fromQ, fromR, toQ, toR) ) throw new IllegalMove("Board is not intact after this move");

    }

    /**
     * Pass the turn.
     *
     * @throws IllegalMove If the turn could not be passed
     */
    @Override
    public void pass() throws IllegalMove {
        Participant p = (whiteIsMoving) ? this.whiteParticipant : this.blackParticipant;
        if(!canPlayerPass(p)){
            throw new IllegalMove("Player cannot pass!");
        }
        this.whiteIsMoving = !this.whiteIsMoving;
    }

    private boolean canPlayerPass(Participant p){
        if(board.getHexTileMap().size() != 1) { //met een enkele steen op het bord kan je altijd spelen
            if (p.getAvailableTiles().isEmpty()) {
                return board.canPlayerMove(p);
            }

            return (!p.getAvailableTiles().isEmpty() && !board.canTileBePlaced(p) && !board.canPlayerMove(p));
        }
        return false;
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
        Player oppEnum = (player == Player.BLACK) ? Player.WHITE : Player.BLACK;

        return board.isQueenBeeSurrounded(oppEnum) ;
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
