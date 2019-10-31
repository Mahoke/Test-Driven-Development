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
    public void afterWhiteParticipantMovesTileParticipantToMoveEqualsBlackThenTrue(){
        HiveGame g = new HiveGame();

        try {
            g.move(0,0,1,1);
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
        HiveGame game = new HiveGame();
        int QBWq = 0;
        int QBWr = 0;
        //playing the queen bee
        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 0);

            int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[0][0], QBWr + directions[0][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[1][0], QBWr + directions[1][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[2][0], QBWr + directions[2][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[3][0], QBWr + directions[3][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[4][0], QBWr + directions[4][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[5][0], QBWq + directions[5][1]);

        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }
        assertTrue(game.isWinner(Hive.Player.BLACK));
    }

    @Test
    public void whenBothQueenBeesSurroundedGameDrawThenTrue(){
        HiveGame game = new HiveGame();
        //playing the WHITE and BLACK queen bee
        int QBWq = 0;
        int QBWr = 0;
        int QBBq = 0;
        int QBBr = -3;

        try {
            game.play(Hive.Tile.QUEEN_BEE, QBWq, QBWr);
            game.play(Hive.Tile.QUEEN_BEE, QBBq, QBBr);

            int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};

            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[0][0], QBWr + directions[0][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[1][0], QBWr + directions[1][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBWq + directions[2][0], QBWr + directions[2][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBBq + directions[0][0], QBBr + directions[0][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBBq + directions[1][0], QBBr + directions[1][1]);
            game.play(Hive.Tile.GRASSHOPPER, QBBq + directions[2][0], QBBr + directions[2][1]);
            game.play(Hive.Tile.SOLDIER_ANT, QBWq + directions[3][0], QBWr + directions[3][1]);
            game.play(Hive.Tile.SOLDIER_ANT, QBWq + directions[4][0], QBWr + directions[4][1]);
            game.play(Hive.Tile.SOLDIER_ANT, QBWq + directions[5][0], QBWr + directions[5][1]);
            game.play(Hive.Tile.SOLDIER_ANT, QBBq + directions[3][0], QBBr + directions[3][1]);
            game.play(Hive.Tile.SOLDIER_ANT, QBBq + directions[4][0], QBBr + directions[4][1]);
            game.play(Hive.Tile.SOLDIER_ANT, QBBq + directions[5][0], QBBr + directions[5][1]);


        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
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

    @Test
    public void playerCanOnlyMoveTilesOnBoard(){
        HiveGame game = new HiveGame();
        boolean illegalState = false;

        try {
            game.move(0, 0, -1, 0);
        } catch (Hive.IllegalMove illegalMove) {
            illegalState = true;
        }

        assertTrue(illegalState);
    }

    @Test
    public void playerCanOnlyMoveTheirOwnTilesOnBoard(){
        HiveGame game = new HiveGame();
        boolean illegalState = false;

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0,0);
            game.play(Hive.Tile.QUEEN_BEE, -1, 0);
            game.move(0, 0, 0, -1);
        } catch (Hive.IllegalMove illegalMove) {
            System.out.println(illegalMove);
            illegalState = true;
        }

        assertFalse(illegalState);
    }

    @Test
    public void playerCanOnlyMoveWhenTheirQueenBeeIsOnBoard(){
        HiveGame game = new HiveGame();
        boolean illegalState = false;

        try {
            game.play(Hive.Tile.GRASSHOPPER, 0, 0);
            game.play(Hive.Tile.GRASSHOPPER, -1, 0);
            game.move(0,0,0,-1);
        } catch (Hive.IllegalMove illegalMove) {
            illegalState = true;
        }

        assertTrue(illegalState);
    }
}
