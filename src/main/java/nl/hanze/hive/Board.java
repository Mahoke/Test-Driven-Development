package nl.hanze.hive;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Board {
    private Map<Hex, Stack<Tile>> hexTileMap;

    final int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};

    private List<Tile> boardTiles;

    public Board(){
        this.boardTiles = new ArrayList<>();
        this.hexTileMap = new HashMap<>();
    }

    public List<Tile> getBoardTiles(){
        return boardTiles;
    }

    public List<Hex> getNeighbours(Hex hex){

        int q = hex.getQ();
        int r = hex.getR();
        List<Hex> neighbours = new ArrayList<>();

        for (int[] qr : directions){
            neighbours.add(new Hex(qr[0], qr[1]));
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
        return hexTileMap.get(hex).peek();
    }

    public void play(Hive.Tile tile, Hive.Player player, int q , int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> tStack = hexTileMap.get(hex);
        if (tStack == null){
            tStack = new Stack<>();
            hexTileMap.put(hex, tStack);
        }
        tStack.push(new Tile(player, tile));
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
}
