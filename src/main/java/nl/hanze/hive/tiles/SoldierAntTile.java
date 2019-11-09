package nl.hanze.hive.tiles;

import nl.hanze.hive.Board;
import nl.hanze.hive.Hex;
import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SoldierAntTile extends InsectTile implements iMovable {

    public SoldierAntTile(Hive.Player player) {
        super(player);
        super.setTile(Hive.Tile.SOLDIER_ANT);
    }

    @Override
    public boolean isValidMove(Board board, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if(fromQ == toQ && fromR == toR){
            throw new Hive.IllegalMove("Can't move soldierant to the same position");
        }

        List<Hex> validMoves = getPathForMove(board, fromQ,fromR,toQ,toR);

        if (validMoves == null){
            throw new Hive.IllegalMove("Can't move SoldierAnt from {" + fromQ + ", " + fromR +"} to {" + toQ + ", " + toR +"}.");
        }

        return true;
    }

    private List<Hex> getPathForMove(Board board, int fromQ, int fromR, int toQ, int toR) {
        List<Hex> neighbours = board.getNeighbouringHexLocations(fromQ, fromR);
        List<Hex> path = null;

        for (Hex neighbour: neighbours){
            path = recursiveSearch(board.clone(), fromQ, fromR, neighbour.getQ(), neighbour.getR(), toQ, toR, Integer.MAX_VALUE, new ArrayList<>());
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
            return path;
        }

        if(path.size() >= maxDepth){
            return null;
        }

        List<Hex> neighbours = board.getNeighbouringHexLocations(toQ,toR);
        for (Hex neighbour: neighbours){
            if(!path.contains(new Hex(neighbour.getQ(), neighbour.getR()))){
                if (recursiveSearch(board, toQ, toR, neighbour.getQ(), neighbour.getR(), goalQ, goalR, maxDepth, path) != null) return path;
                    if (path.contains(new Hex(neighbour.getQ(), neighbour.getR()))){
                        path.remove(new Hex(neighbour.getQ(), neighbour.getR()));
                        board.undoMove(toQ, toR, neighbour.getQ(), neighbour.getR());
                    }
            }
        }

        return null;
    }
}
