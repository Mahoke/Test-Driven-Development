package nl.hanze.hive;
import nl.hanze.hive.tiles.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.Assert.*;

public class HiveGameTest {

    @Test
    public void whenNewGameParticipantToMoveEqualsWhiteThenTrue(){
        HiveGame g = new HiveGame();
        Participant p = new Participant(Hive.Player.WHITE);
        assertEquals(p, g.getParticipantToMove());
    }

    @Test
    public void afterWhiteParticipantPlaysTileParticipantToMoveEqualsBlackThenTrue(){
        HiveGame g = new HiveGame();

        try {
            g.play(Hive.Tile.BEETLE, 0, 0);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        assertEquals(new Participant(Hive.Player.BLACK), g.getParticipantToMove());
    }

    @Test
    public void afterWhiteParticipantMovesTileParticipantToMoveEqualsBlackThenTrue(){
        HiveGame g = new HiveGame();

        try {
            g.play(Hive.Tile.QUEEN_BEE,0,0);
            g.play(Hive.Tile.QUEEN_BEE,-1,0);
            g.move(0,0,0,-1);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        assertEquals(new Participant(Hive.Player.BLACK), g.getParticipantToMove());
    }

    @Test
    public void afterWhiteParticipantPassesParticipantToMoveEqualsBlackThenTrue(){
        HiveGame g = new HiveGame();

        try {
            g.pass();
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        assertEquals(new Participant(Hive.Player.BLACK), g.getParticipantToMove());
    }

    @Test
    public void whenWhiteQueenBeeSurroundedBlackWinsThenTrue(){
        Board board = new Board();
        board.placeTile(new QueenBeeTile(Hive.Player.WHITE), 0,0);

        int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};
        for(int[] dir : directions){
            board.placeTile(new GrasshopperTile(Hive.Player.BLACK), dir[0], dir[1]);
        }


        HiveGame game = new HiveGame(board);

        assertTrue(game.isWinner(Hive.Player.BLACK));
    }

    @Test
    public void whenBothQueenBeesSurroundedGameDrawThenTrue(){
        Board board = new Board();
        HiveGame game = new HiveGame(board);
        //playing the WHITE and BLACK queen bee
        int QBWq = 0; //QueenBeeWhiteQ etc.
        int QBWr = 0;
        int QBBq = 0;
        int QBBr = -3;
        board.placeTile(new QueenBeeTile(Hive.Player.WHITE), QBWq,QBWr);
        board.placeTile(new QueenBeeTile(Hive.Player.BLACK), QBBq,QBBr);

        int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};
        for (int[] dir : directions){
            board.placeTile(new GrasshopperTile(Hive.Player.BLACK), QBWq + dir[0], QBWr + dir[1]);
            board.placeTile(new GrasshopperTile(Hive.Player.BLACK), QBBq + dir[0], QBBr + dir[1]);
        }

        assertTrue(game.isDraw());
    }

    @Test
    public void whenPlayerPlaysUnavailableStoneThenFalse(){
        HiveGame game = new HiveGame();
        boolean playableState = true;
        for (int i = 0; i < 5; i++) {
            //each player has 4 spiders, so in 5th iteration, a illegalmove will be thrown.
            try {
                game.play(Hive.Tile.SPIDER, 0, 0);
            } catch (Hive.IllegalMove illegalMove) {
                playableState = false;
            }
        }
        assertFalse(playableState);
    }

    //4C
    @Test
    public void whenStonesOnBoardPlayStoneAtHexWithNoNeighboursThenFalse(){
        HiveGame game = new HiveGame();

        InsectTile t1 = new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE);
        InsectTile t2 = new InsectTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE);

        boolean playableState = true;

        try {
            game.play(Hive.Tile.QUEEN_BEE, -1, 0);
        } catch (Hive.IllegalMove illegalMove) {
            playableState = false;
        }

        try {
            game.play(Hive.Tile.QUEEN_BEE, 1, 0);
        } catch (Hive.IllegalMove illegalMove) {
            playableState = false;
        }

        assertFalse(playableState);
    }

    //4D
    @Test
    public void whenStonesOfBothOnBoardPlayStoneNextToOpponentThenFalse(){
        HiveGame game = new HiveGame();

        InsectTile t1 = new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE);
        InsectTile t2 = new InsectTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE);

        boolean playableState = true;

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 0);
            game.play(Hive.Tile.QUEEN_BEE, 1, 0);
        } catch (Hive.IllegalMove illegalMove) {
            playableState = false;
        }

        try {
            game.play(Hive.Tile.SOLDIER_ANT, 0, 1);
        } catch (Hive.IllegalMove illegalMove) {
            playableState = false;
        }

        assertFalse(playableState);
    }


    //4E
    @Test
    public void whenPlayerPlayedThreeTilesHasToPlayQueenThenTrue() {
        HiveGame game = new HiveGame();


        boolean playableState = true;

        try {
            game.play(Hive.Tile.SOLDIER_ANT, 0, 0);
            game.play(Hive.Tile.SOLDIER_ANT, 1, 0);
            game.play(Hive.Tile.SOLDIER_ANT, -1, 0);
            game.play(Hive.Tile.SOLDIER_ANT, 2, 0);
            game.play(Hive.Tile.SOLDIER_ANT, -2, 0);
            game.play(Hive.Tile.SOLDIER_ANT, 3, 0);
        } catch (Hive.IllegalMove illegalMove) {
            playableState = false;
        }

        try {
            game.play(Hive.Tile.QUEEN_BEE, -3, 0);
        } catch (Hive.IllegalMove illegalMove) {
            playableState = false;
        }

        assertTrue(playableState);
    }

    //5A
    @Test
    public void playerCanOnlyMoveTheirOwnTilesOnBoard(){
        HiveGame game = new HiveGame();
        boolean illegalState = false;

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0,0);//white
            game.play(Hive.Tile.SOLDIER_ANT, 1,0);//black
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        try {
            game.move(1, 0, 0, 1);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println((illegalMove.getMessage()));
            illegalState = true;
        }

        assertTrue(illegalState);
    }

    //5B
    @Test
    public void playerCanOnlyMoveWhenTheirQueenBeeIsOnBoard(){
        HiveGame game = new HiveGame();
        boolean illegalState = false;

        try {
            game.play(Hive.Tile.SOLDIER_ANT, 0, 0);
            game.play(Hive.Tile.GRASSHOPPER, -1, 0);
            game.move(0,0,0,-1);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println((illegalMove.getMessage()));
            illegalState = true;
        }

        assertTrue(illegalState);
    }

    //5C
    @Test(expected =  Hive.IllegalMove.class)  // < dit was nieuw voor mij, dus even testen
    public void afterMovingStoneInContactWithAtleastOneStone() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        boolean thrown = false;

        game.play(Hive.Tile.QUEEN_BEE, 0, 0); //white
        game.play(Hive.Tile.QUEEN_BEE, -1, 0);//black
        game.play(Hive.Tile.SOLDIER_ANT, 1, 0);//white
        game.play(Hive.Tile.SOLDIER_ANT, -2, 0);//black


        game.move(1,0,2,0);
    }

    //5D
    @Test(expected = Hive.IllegalMove.class)
    public void noSeperateGroupsOnBoardAfterMoving() throws  Hive.IllegalMove {
        HiveGame game = new HiveGame();
        boolean thrown = false;

        game.play(Hive.Tile.QUEEN_BEE, 0, 0); //white
        game.play(Hive.Tile.QUEEN_BEE, -1, 0);//black
        game.play(Hive.Tile.SOLDIER_ANT, 1, 0);//white
        game.play(Hive.Tile.SOLDIER_ANT, -2, 0);//black


        game.move(0,0,+ 1,-1);
    }

    //12 Passen
    @Test
    public void tryPassButNotValidThenFalse(){
        HiveGame game = new HiveGame();

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 0);
            game.play(Hive.Tile.QUEEN_BEE, 0, 1);
            game.play(Hive.Tile.SOLDIER_ANT, 0, -1);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        boolean canPass = true;
        try {
            game.pass();
        } catch (Hive.IllegalMove illegalMove) {
            canPass = false;
        }
        assertFalse(canPass);
    }

    @Test //12a
    public void playerTriesToPassWhenNothingCanBePlayedButMoveIsPossibleThenFalse(){
        Board board = new Board();
        HiveGame game = new HiveGame(board);

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 0);
            game.play(Hive.Tile.BEETLE, 1,-1);
            game.play(Hive.Tile.BEETLE, -1, 0);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        board.placeTile(new GrasshopperTile(Hive.Player.WHITE),0, -1);
        board.placeTile(new BeetleTile(Hive.Player.WHITE),2, -2);
        board.placeTile(new GrasshopperTile(Hive.Player.WHITE),1,0);
        board.placeTile(new GrasshopperTile(Hive.Player.WHITE),2,-1);

        Participant p = game.getBlackParticipant();
        p.removeTileFromAvailableTiles(Hive.Tile.QUEEN_BEE);

        boolean canPass = true;
        try {
            game.pass();
        } catch (Hive.IllegalMove illegalMove) {
            canPass = false;
        }

        assertFalse(canPass);
    }

    @Test //12
    public void playerPassNoMovesThenTrue(){
        Board board = new Board();
        HiveGame game = new HiveGame(board);

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 0);
            game.play(Hive.Tile.QUEEN_BEE, 0,-1);
            game.play(Hive.Tile.BEETLE, -1,1);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }
        board.doMove(-1,1, -1,0);
        board.placeTile(new BeetleTile(Hive.Player.WHITE), 1, -1);
        board.placeTile(new SoldierAntTile(Hive.Player.WHITE), -1, -1);
        board.placeTile(new SoldierAntTile(Hive.Player.WHITE), 1, -2);

        boolean canPass = true;

        try {
            game.pass(); //black player past
        } catch (Hive.IllegalMove illegalMove) {
            canPass = false;
        }

        assertTrue(canPass);
    }
}
