package nl.hanze.hive;

import java.util.*;

public class Board {
    private Map<Hex, Stack<Tile>> hexTileMap;

    private final int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};

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

    public boolean isQueenBeeParticipantSurrounded(Participant p){

    }
}
