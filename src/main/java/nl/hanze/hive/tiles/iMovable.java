package nl.hanze.hive.tiles;

import nl.hanze.hive.Board;
import nl.hanze.hive.Hex;
import nl.hanze.hive.Hive;

import java.util.List;

public interface iMovable {
    public boolean isValidMove(Board board, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove;
}
