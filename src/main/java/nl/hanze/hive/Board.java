package nl.hanze.hive;


import java.util.*;

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

    public void play(Tile tile, int q, int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> tStack = hexTileMap.get(hex);
        if (tStack == null){
            tStack = new Stack<>();
            hexTileMap.put(hex, tStack);
        }
        tStack.push(tile);
    }

    public boolean isQueenBeeSurrounded(Participant p){
        Tile tempQueenBee = new Tile(p.getColor(), Hive.Tile.QUEEN_BEE);

        int neighbourcount = 0;
        for (int[] qr : directions){
            Stack<Tile> st = hexTileMap.get(new Hex(qr[0], qr[1]));
            System.out.printf(st.toString());
            if( st != null){
                neighbourcount += 1;
            }
        }
        return neighbourcount == 6;
    }
}
