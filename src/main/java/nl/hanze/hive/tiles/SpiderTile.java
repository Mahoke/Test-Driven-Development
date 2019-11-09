package nl.hanze.hive.tiles;

import nl.hanze.hive.Board;
import nl.hanze.hive.Hex;
import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SpiderTile extends InsectTile implements iMovable {

    public SpiderTile(Hive.Player player) {
        super(player);
        super.setTile(Hive.Tile.SPIDER);
    }

    @Override
    public boolean isValidMove(Board board, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if(fromQ == toQ && fromR == toR){
            throw new Hive.IllegalMove("Move to the same location is not allowed!");
        }
        List<Hex> validMoves = getValidMoves(board, fromQ,fromR,toQ,toR);

        if (validMoves == null){
            throw new Hive.IllegalMove("Can't move spider from {" + fromQ + ", " + fromR +"} to {" + toQ + ", " + toR +"}.");
        }

        return true;
    }

    public List<Hex> getValidMoves(Board board, int fromQ, int fromR, int toQ, int toR){
        List<Hex> neighbours = board.getNeighbouringHexLocations(fromQ, fromR);
        List<Hex> path = null;

        for (Hex neighbour: neighbours){
            path = recursiveSearch(board.clone(), fromQ, fromR, neighbour.getQ(), neighbour.getR(), toQ, toR, 3, new ArrayList<>());
            if(path != null){
                return path;
            }
        }

        return path;
    }

    private List<Hex> recursiveSearch(Board board, int currentQ, int currentR, int toQ, int toR, int goalQ, int goalR, int maxDepth, List<Hex> path){
        Stack<InsectTile> st = board.getTilesAtHexLocation(toQ,toR);
        if(st.size() > 0){
            return null; //Mag niet verplaatssen over bezette plaatsen
        }

        if( ! board.canSlideFromAToB(currentQ,currentR,toQ, toR) || !board.legalBoardAfterMoving(currentQ, currentR, toQ, toR)){
            return null;
        }

        path.add(new Hex(toQ, toR));
        board.doMove(currentQ, currentR, toQ, toR);

        if(goalQ == toQ && goalR == toR){
            if(path.size() != maxDepth){
                return null;
            }
            return path;
        }


        if(path.size() >= maxDepth) {
            return null;
        }

        List<Hex> neighbours = board.getNeighbouringHexLocations(toQ,toR);

        for (Hex neighbour: neighbours){
            if(!path.contains(neighbour)){
                if (recursiveSearch(board, toQ, toR, neighbour.getQ(), neighbour.getR(), goalQ, goalR, maxDepth, path) != null){
                    return path;
                }

                if (path.contains(neighbour)){
                    path.remove(neighbour);
                    board.undoMove(toQ, toR, neighbour.getQ(), neighbour.getR());
                }
            }
        }

        return null;
    }
}
