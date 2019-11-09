package nl.hanze.hive.tiles;

import nl.hanze.hive.Board;
import nl.hanze.hive.Hex;
import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class GrasshopperTile extends InsectTile implements iMovable {

    public enum Direction { LEFT, LEFT_DOWN, LEFT_UP, RIGHT, RIGHT_DOWN, RIGHT_UP }

    public GrasshopperTile(Hive.Player player) {
        super(player);
        super.setTile(Hive.Tile.GRASSHOPPER);
    }

    @Override
    public boolean isValidMove(Board board, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if(fromQ == toQ && fromR == toR){
            throw new Hive.IllegalMove("b.Een sprinkhaan mag zich niet verplaatsen naar het veld waar hij al staat.");
        }

        Stack<InsectTile> st = board.getTilesAtHexLocation(toQ, toR);
        if(!st.empty()){
            throw new Hive.IllegalMove("11 d.Een sprinkhaanmag niet naar een bezet veld springen.");
        }

        List<Hex> validMoves = getValidMoves(board, fromQ, fromR, toQ, toR);
        if ( validMoves == null ){
            throw new Hive.IllegalMove("Can't move grasshopper from {" + fromQ + ", " + fromR +"} to {" + toQ + ", " + toR +"}.");
        }

        return true;
    }

    private List<Hex> getValidMoves(Board board, int fromQ, int fromR, int toQ, int toR){
        List<Hex> neighbours = board.getNeighbouringHexLocations(fromQ, fromR);
        List<Hex> path = null;

        for(Hex neighbour: neighbours){
            path = recursiveSearch(board, fromQ, fromR, neighbour.getQ(), neighbour.getR(), fromQ, fromR, toQ, toR, Integer.MAX_VALUE, new ArrayList<>());
            if(path != null){
                return path;
            }
        }

        return path;
    }

    private List<Hex> recursiveSearch(Board board, int currentQ, int currentR, int toQ, int toR, int startQ, int startR, int goalQ, int goalR, int maxDepth, List<Hex> path) {

        if(!isPathFilled(board, path)){
            return null;
        }

        path.add(new Hex(toQ, toR));

        if(!pathFollowsSameAxes(startQ, startR, path)){
            return null;
        }

        if(toQ == goalQ && toR == goalR){
            if(path.size() > 1){ //afstand moet groter dan een zijn.
                return path;
            } else {
                return null;
            }
        }

        List<Hex> neighbours = board.getNeighbouringHexLocations(toQ, toR);

        for( Hex neighbour: neighbours){
            if(!path.contains(neighbour)){
                if(recursiveSearch(board, toQ, toR, neighbour.getQ(), neighbour.getR(), startQ, startR, goalQ, goalR, maxDepth, path) != null){
                    return path;
                }
                path.remove(neighbour);
            }
        }
        return null;
    }

    private boolean pathFollowsSameAxes(int originQ, int originR, List<Hex> path){
        List<Hex> pathFromOrigin = new ArrayList<>();
        pathFromOrigin.add(new Hex(originQ, originR));
        pathFromOrigin.addAll(path);

        Direction previous = null;

        for (int i = 0; i < pathFromOrigin.size() - 1; i++) {
            Direction current = getDirection(pathFromOrigin.get(i), pathFromOrigin.get(i+1));
            if(current == null){
                return false;
            }

            if(previous == null) {
                previous = current;
            }

            if(previous != current){
                return false;
            }
        }
        return true;
    }

    private boolean isPathFilled(Board board, List<Hex> path){
        for(Hex position: path){
            if(board.getTilesAtHexLocation(position).empty()){
                return false;
            }
        }
        return true;
    }

    private Direction getDirection(Hex from, Hex to){
        int fromQ = from.getQ();
        int fromR = from.getR();
        int toQ = to.getQ();
        int toR = to.getR();

        if(fromR == toR && fromQ != toQ){ //X axis
            //LEFT or RIGHT
            if(fromQ < toQ){
                return Direction.RIGHT;
            }

            if(fromQ > toQ){
                return Direction.LEFT;
            }
        }

        //YAxis
        if (toQ == fromQ - 1 && toR == fromR + 1){
            return Direction.LEFT_DOWN;
        }

        if (toQ == fromQ + 1 && toR == fromR - 1) {
            return Direction.RIGHT_UP;
        }

        if(fromQ == toQ && fromR != toR){ //Z axis
            //LEFT UP or RIGHT DOWN
            if (fromR > toR){
                return Direction.LEFT_UP;
            }
            if (fromR < toR){
                return Direction.RIGHT_DOWN;
            }
        }
        return null;
    }
}
