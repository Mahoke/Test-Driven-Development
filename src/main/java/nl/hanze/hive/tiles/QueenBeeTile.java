package nl.hanze.hive.tiles;

import nl.hanze.hive.Board;
import nl.hanze.hive.Hex;
import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class QueenBeeTile extends InsectTile implements iMovable {

    public QueenBeeTile(Hive.Player player) {
        super(player);
        super.setTile(Hive.Tile.QUEEN_BEE);
    }

    @Override
    public boolean isValidMove(Board board, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        Stack<InsectTile> st = board.getTilesAtHexLocation(toQ, toR);
        if(!st.isEmpty()){
            throw new Hive.IllegalMove("Can't move QueenBee from {" + fromQ + ", " + fromR +"} to {" + fromQ + ", " + fromR +"}. Location is not empty.");
        }

        List<Hex> validMoves = getValidMoves(board, fromQ, fromR);
        if (validMoves.size() == 0){
            throw new Hive.IllegalMove("Can't move QueenBee from {" + fromQ + ", " + fromR +"} to {" + fromQ + ", " + fromR +"}. QueenBee does not have any valid moves.");
        }

        if (validMoves.contains(new Hex(toQ,toR))){
            return true;
        } else {
            throw new Hive.IllegalMove("Can't move QueenBee from {" + fromQ + ", " + fromR +"} to {" + fromQ + ", " + fromR +"}. Location is not part of QueenBee's valid moves.");
        }
    }

    public List<Hex> getValidMoves(Board board, int fromQ, int fromR){
        List<Hex> neighbours = board.getNeighbouringHexLocations(fromQ, fromR);
        List<Hex> validMoves = new ArrayList<>();

        for(Hex neighbour: neighbours){
            if(     board.getTilesAtHexLocation(neighbour).isEmpty()
                    && board.canSlideFromAToB(fromQ, fromR, neighbour.getQ(),neighbour.getR())
                    && board.legalBoardAfterMoving(fromQ, fromR, neighbour.getQ(), neighbour.getR())
            ){
                validMoves.add(new Hex(neighbour.getQ(),neighbour.getR()));
            }
        }
        return validMoves;
    }

}
