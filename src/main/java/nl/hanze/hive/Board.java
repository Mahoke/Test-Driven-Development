package nl.hanze.hive;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Board {
    private Map<Hex, Stack<Tile>> hexTileMap;

    final int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};
    private int tileCount = 0;
    private List<Tile> boardTiles;
    private int tileCountWhite;
    private int tileCountBlack;

    public Board(){
        this.boardTiles = new ArrayList<>();
        this.hexTileMap = new HashMap<>();
    }

    public List<Tile> getBoardTiles(){
        return boardTiles;
    }

    public List<Hex> getNeighbours(Hex hex){
            List<Hex> neighbours = new ArrayList<>();

        for (int[] qr : directions){
            int q = hex.getQ() + qr[0];
            int r = hex.getR() + qr[1];
            neighbours.add(new Hex(q,r));
        }
        return neighbours;
    }

    public boolean isHexEmpty(int q, int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> tileStack = this.hexTileMap.get(hex);
        if (tileStack == null){
            return true;
        } else {
            return tileStack.size() == 0;
        }
    }

    public void placeTile(Tile tile, int q, int r) {
        Hex hex = new Hex(q, r);
        Stack<Tile> tileStack = this.hexTileMap.get(hex);

        if (tileStack == null) {
            tileStack = new Stack<>();
        }

        tileStack.push(tile);

        this.hexTileMap.put(hex,tileStack);
    }

    public Tile getTile(int q, int r) {
        Hex hex = new Hex(q,r);
        Stack<Tile> st = this.hexTileMap.get(hex);
        if (st == null) return null;

        return st.peek();
    }

    public void play(Hive.Tile tile, Hive.Player player, int q , int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> tStack = hexTileMap.get(hex);
        if (tStack == null){
            tStack = new Stack<>();
            hexTileMap.put(hex, tStack);
        }
        tStack.push(new Tile(player, tile));
        this.tileCount++;
    }

    public void move(Participant p, int fromQ, int fromR, int toQ, int toR) {

    }

    public boolean isQueenBeeSurrounded(Hive.Player p){
        Tile tempQueenBee = new Tile(p, Hive.Tile.QUEEN_BEE);
        int foundQ =-99;
        int foundR =-99;

        for (Map.Entry<Hex, Stack<Tile>> pair : hexTileMap.entrySet()){
            int found = pair.getValue().search(tempQueenBee);
            if (found != -1){
                foundQ = pair.getKey().getQ();
                foundR = pair.getKey().getR();
                break;
            }
        }

        int neighbourcount = 0;
        for (int[] qr : directions){
            Stack<Tile> st = hexTileMap.get(new Hex(foundQ + qr[0], foundR + qr[1]));
            if( st != null){
                if (st.size() > 0) {
                    neighbourcount += 1;
                }
            }
        }
        return neighbourcount == 6;
    }

    public boolean canPlayTile(Hive.Player color, Hive.Tile tile, int q, int r) {
        if (!this.isHexEmpty(q, r)) {
            return false;
        }

        if (this.tileCount == 0) {
            return true;
        }

        boolean hasNeighbours = this.hasTilesInNeighbouringHex(q, r);

        if (!hasNeighbours) {
            return false;
        }

        if (this.tileCountWhite > 0 && this.tileCountBlack > 0) {
            //There are tiles of both players on the board
            //Tile has to be played next to it's own color tile
            List<Hex> hexList = this.getNeighbours(new Hex(q, r));

            for (Hex h : hexList) {
                Stack<Tile> st = hexTileMap.get(h);
                if (st != null) {
                    if (st.size() > 0) {
                        Tile t = st.peek();
                        Hive.Player neighbourColor = t.getPlayer();

                        if (neighbourColor != color) {
                            return false;
                        }
                    }
                }
            }

        }

        return true;
    }


    private boolean hasTilesInNeighbouringHex(int q, int r) {
        Hex hex = new Hex(q, r);
        List<Hex> neighboutList = getNeighbours(hex);

        int neighbourcount = 0;

        for(Hex h : neighboutList){
            Stack<Tile> st = hexTileMap.get(h);
            if (st != null){
                if ( st.size() > 0 ) {
                    neighbourcount++;
                }
            }
        }

        return neighbourcount > 0;
    }

    public void updateTileCount(Participant p) {
        if (p.getColor() == Hive.Player.WHITE) {
            this.tileCountWhite += 1;
        } else {
            this.tileCountBlack += 1;
        }
    }

    public int getTileCount(Hive.Player c){
        if ( c == Hive.Player.WHITE ){
            return this.tileCountWhite;
        } else {
            return this.tileCountBlack;
        }
    }

}
