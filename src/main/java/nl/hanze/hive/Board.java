package nl.hanze.hive;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};

    private List<Tile> boardTiles;

    public Board(){
        this.boardTiles = new ArrayList<>();
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
}
