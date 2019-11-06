package nl.hanze.hive;
import org.junit.Test;
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
    public void whenWhiteQueenBeeSurroundedBlackWinsThenTrue(){

        Board board = new Board();
        int QBWq = 0;
        int QBWr = 0;
        //playing the queen bee
        board.play(Hive.Tile.QUEEN_BEE, Hive.Player.WHITE, 0, 0);

        int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};

        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.BLACK,QBWq + directions[0][0], QBWr + directions[0][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.WHITE,QBWq + directions[1][0], QBWr + directions[1][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.BLACK,QBWq + directions[2][0], QBWr + directions[2][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.WHITE,QBWq + directions[3][0], QBWr + directions[3][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.BLACK,QBWq + directions[4][0], QBWr + directions[4][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.WHITE,QBWq + directions[5][0], QBWq + directions[5][1]);
        assertTrue(board.isQueenBeeSurrounded(Hive.Player.WHITE));
}

    @Test
    public void whenBothQueenBeesSurroundedGameDrawThenTrue(){
        Board board = new Board();

        //playing the WHITE and BLACK queen bee
        int QBWq = 0;
        int QBWr = 0;
        int QBBq = 0;
        int QBBr = -3;

        board.play(Hive.Tile.QUEEN_BEE,Hive.Player.WHITE, QBWq, QBWr);
        board.play(Hive.Tile.QUEEN_BEE,Hive.Player.BLACK, QBBq, QBBr);

        int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};

        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.BLACK, QBWq + directions[0][0], QBWr + directions[0][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.WHITE, QBWq + directions[1][0], QBWr + directions[1][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.BLACK, QBWq + directions[2][0], QBWr + directions[2][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.WHITE, QBBq + directions[0][0], QBBr + directions[0][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.BLACK, QBBq + directions[1][0], QBBr + directions[1][1]);
        board.play(Hive.Tile.GRASSHOPPER, Hive.Player.WHITE, QBBq + directions[2][0], QBBr + directions[2][1]);
        board.play(Hive.Tile.SOLDIER_ANT, Hive.Player.BLACK, QBWq + directions[3][0], QBWr + directions[3][1]);
        board.play(Hive.Tile.SOLDIER_ANT, Hive.Player.WHITE, QBWq + directions[4][0], QBWr + directions[4][1]);
        board.play(Hive.Tile.SOLDIER_ANT, Hive.Player.BLACK, QBWq + directions[5][0], QBWr + directions[5][1]);
        board.play(Hive.Tile.SOLDIER_ANT, Hive.Player.WHITE, QBBq + directions[3][0], QBBr + directions[3][1]);
        board.play(Hive.Tile.SOLDIER_ANT, Hive.Player.BLACK, QBBq + directions[4][0], QBBr + directions[4][1]);
        board.play(Hive.Tile.SOLDIER_ANT, Hive.Player.WHITE, QBBq + directions[5][0], QBBr + directions[5][1]);

        assertTrue(board.isQueenBeeSurrounded(Hive.Player.BLACK) && board.isQueenBeeSurrounded(Hive.Player.WHITE));
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

        Tile t1 = new Tile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE);
        Tile t2 = new Tile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE);

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

        Tile t1 = new Tile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE);
        Tile t2 = new Tile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE);

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
}
