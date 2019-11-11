package nl.hanze.hive.tiles;

import nl.hanze.hive.Board;
import nl.hanze.hive.Hex;
import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.List;

public class BeetleTile extends InsectTile implements iMovable {

    public BeetleTile(Hive.Player player) {
        super(player);
        super.setTile(Hive.Tile.BEETLE);
    }

    @Override
    public boolean isValidMove(Board board, int fromQ, int fromR, int toQ, int toR) {
        Hex from = new Hex(fromQ, fromR);

        List<Hex> locations = board.getNeighbouringHexLocations(from);

        for (Hex to : locations){
            if(to.equals(new Hex(toQ, toR))){
                if(board.canSlideFromAToB(from, to) && board.legalBoardAfterMoving(from, to)){
                    return true;
                }
            }
        }

        return false;
    }


}
