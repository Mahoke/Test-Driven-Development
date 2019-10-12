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

    }
}
