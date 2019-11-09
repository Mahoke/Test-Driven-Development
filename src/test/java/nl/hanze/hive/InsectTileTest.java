package nl.hanze.hive;

import nl.hanze.hive.tiles.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class InsectTileTest {
    //1a
    @Test
    public void givenColorWhenEqualsThenTrue() {
        InsectTile a = new InsectTile(Hive.Player.BLACK);
        assertEquals(Hive.Player.BLACK, a.getPlayer());
    }

    @Test
    public void givenDifferentColorWhenNotEqualThenTrue(){
        InsectTile a = new InsectTile(Hive.Player.BLACK);
        InsectTile b = new InsectTile(Hive.Player.WHITE);
        assertNotEquals(a.getPlayer(), b.getPlayer());
    }

    //1b
    @Test
    public void givenPictureQueenBeeWhenEqualsThenTrue(){
        InsectTile a = new InsectTile(Hive.Tile.QUEEN_BEE);
        assertEquals(Hive.Tile.QUEEN_BEE, a.getTile());
    }

    @Test
    public void givenPictureSpiderWhenEqualsThenTrue(){
        InsectTile a = new InsectTile(Hive.Tile.SPIDER);
        assertEquals(Hive.Tile.SPIDER, a.getTile());
    }

    @Test
    public void givenPictureBeetleWhenEqualsThenTrue(){
        InsectTile a = new InsectTile(Hive.Tile.BEETLE);
        assertEquals(Hive.Tile.BEETLE, a.getTile());
    }

    @Test
    public void givenPictureSoldierAntWhenEqualsThenTrue(){
        InsectTile a = new InsectTile(Hive.Tile.SOLDIER_ANT);
        assertEquals(Hive.Tile.SOLDIER_ANT, a.getTile());
    }

    @Test
    public void givenPictureGrasshopperWhenEqualsThenTrue(){
        InsectTile a = new InsectTile(Hive.Tile.GRASSHOPPER);
        assertEquals(Hive.Tile.GRASSHOPPER, a.getTile());
    }

    //7 Verplaatsen kever
    @Test
    public void validMovesBeetleEqualsActualAvailableMovesThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, 0);


        boolean allMovesValid = (
                            new BeetleTile(Hive.Player.BLACK).isValidMove(board, 0,0, 0, -1) &&
                            new BeetleTile(Hive.Player.BLACK).isValidMove(board, 0,0, 0, 1) &&
                            new BeetleTile(Hive.Player.BLACK).isValidMove(board, 0,0, 1, 0) &&
                            new BeetleTile(Hive.Player.BLACK).isValidMove(board, 0,0, 1, -1) &&
                            new BeetleTile(Hive.Player.BLACK).isValidMove(board, 0,0, -1, 0) &&
                            new BeetleTile(Hive.Player.BLACK).isValidMove(board, 0,0, -1, 1)
                        );

        assertTrue(allMovesValid);
    }
    //7
    @Test
    public void validMovesBeetlePartlyBlockedEqualsActualAvailableMovesThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, 1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),1, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),-1, 0);

        boolean isValidMove = new BeetleTile(Hive.Player.BLACK).isValidMove(board, 0,0, -1, +1);
        assertFalse(isValidMove);
    }

    //8 verplaatsen bijenkoningin
    @Test
    public void validMovesQueenBeeEqualsActualAvailableMovesThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),1, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),-1, 0);

        List<Hex> returnedMoves = new QueenBeeTile(Hive.Player.BLACK).getValidMoves(board, 0,0);
        ArrayList<Hex> shouldBe = new ArrayList<>();
        shouldBe.add(new Hex(0,+ 1));
        shouldBe.add(new Hex(-1,+ 1));

        assertTrue(shouldBe.equals(returnedMoves));
    }

    //8a.De bijenkoningin verplaatst zichdoor precies één keer te verschuiven
    @Test
    public void QueenBeeMovesMoreThanOneSpotThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, -1);

        boolean isValidMove = false;
        try {
            isValidMove = new QueenBeeTile(Hive.Player.BLACK).isValidMove(board, 0,0, 1, -2);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }
        assertFalse(isValidMove);
    }


    //8 b.De bijenkoningin mag alleen verplaatst worden naar een leeg veld.
    @Test
    public void QueenBeeMovesToOccupiedLocatieThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),0, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),1, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.BEETLE),-1, 0);

        boolean isValidMove = false;
        try {
            isValidMove = new QueenBeeTile(Hive.Player.BLACK).isValidMove(board, 0,0, 0, -1);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }
        assertFalse(isValidMove);
    }

    //9
    @Test
    public void validSoldierAntMoveThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),2, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),3, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT),-1, 0);

        boolean validMove = false;
        try {
            validMove = new SoldierAntTile(Hive.Player.BLACK).isValidMove(board, -1,0, 4, 0);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }


        assertTrue(validMove);
    }

    //9
    @Test
    public void invalidSoldierAntMoveThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, 1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),-1, 1);
        board.placeTile(new SoldierAntTile(Hive.Player.BLACK),-1, 0);

        boolean validMove = false;
        try {
            validMove = new SoldierAntTile(Hive.Player.BLACK).isValidMove(board, -1,0, 0, 0);
        } catch (Hive.IllegalMove illegalMove) {

        }

        assertFalse(validMove);
    }

    //9B Een soldatenmier mag zich niet verplaatsen naar het veld waar hij al staat.
    @Test
    public void SoldierAntMoveToSameSpotThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0, 1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1, -1);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),-1, 1);
        board.placeTile(new SoldierAntTile(Hive.Player.BLACK),-1, 0);

        boolean validMove = false;
        try {
            validMove = new SoldierAntTile(Hive.Player.BLACK).isValidMove(board, -1,0, -1, 0);
        } catch (Hive.IllegalMove illegalMove) {

        }

        assertFalse(validMove);
    }

    //10a a.Een spin verplaatst zichdoor precies drie keer teverschuiven.
    //c.Een spinmag alleen verplaatstworden over en naar lege velden.
    // d.Eenspin mag tijdens zijn verplaatsing geen stap maken naar een veld waar hij tijdens de verplaatsing al is geweest.
    @Test
    public void validSpiderMoveThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1,0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),2, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),3,0);
        board.placeTile(new SpiderTile(Hive.Player.BLACK),0,0);
        boolean validMove = false;
        try {
            validMove = ( new SpiderTile(Hive.Player.BLACK).isValidMove(board,0,0,3,-1)
                        && new SpiderTile(Hive.Player.BLACK).isValidMove(board,0,0,2,1)
                        );
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        assertTrue(validMove);
    }

    //10
    @Test
    public void invalidSpiderMoveThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),2,0);
        board.placeTile(new SpiderTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new SpiderTile(Hive.Player.BLACK).isValidMove(board,-1,0,0,1);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }

    //10b Een spin magzich niet verplaatsennaar het veld waar hij al staat
    @Test
    public void spiderMoveToSameLocationThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1, 0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),2,0);
        board.placeTile(new SpiderTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new SpiderTile(Hive.Player.BLACK).isValidMove(board,-1,0,-1,0);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }

    //11a.Een sprinkhaan verplaatst zich door in een rechte lijn een sprong te maken naar een veld meteen achter een andere steen in de richting van de sprong
    @Test
    public void validGrasshopperMoveThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);

        board.placeTile(new GrasshopperTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new GrasshopperTile(Hive.Player.BLACK).isValidMove(board,-1,0,1,0);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertTrue(validMove);
    }

    //11a meteen achter een andere steen in de richting van de sprong
    @Test
    public void invalidGrasshopperMoveThenTrue(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);

        board.placeTile(new GrasshopperTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new GrasshopperTile(Hive.Player.BLACK).isValidMove(board,-1,0,2,0);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }

    //11a in een rechte lijn
    @Test
    public void invalidGrasshopperMoveNotTravelingStraightThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);

        board.placeTile(new GrasshopperTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new GrasshopperTile(Hive.Player.BLACK).isValidMove(board,-1,0,0,-2);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }

    //11bb.Een sprinkhaan mag zich niet verplaatsen naar het veld waar hij al staat.
    @Test
    public void invalidGrasshopperMoveTravelingSamePositionThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);

        board.placeTile(new GrasshopperTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new GrasshopperTile(Hive.Player.BLACK).isValidMove(board,-1,0,-1,0);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }

    //c.Een sprinkhaan moet over minimaal één steen springen.
    @Test
    public void invalidGrasshopperMoveTravelingLessThanMinimalOneSpotInBetweenPositionsThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);

        board.placeTile(new GrasshopperTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new GrasshopperTile(Hive.Player.BLACK).isValidMove(board,-1,0,0,-1);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }

    //d.Een sprinkhaanmag niet naar een bezet veld springen.
    @Test
    public void invalidGrasshopperMoveTravelingToOccupiedPositionThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),1,0);

        board.placeTile(new GrasshopperTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new GrasshopperTile(Hive.Player.BLACK).isValidMove(board,-1,0,1,0);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }

    //e.Een sprinkhaan magniet over lege velden springen. Dit betekent dat alle velden tussen de start-en eindpositie bezet moeten zijn.
    @Test
    public void invalidGrasshopperMoveToPositionWithEmptyLocationAlongTheWayThenFalse(){
        Board board = new Board();
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),0,0);
        board.placeTile(new InsectTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE),3,0);

        board.placeTile(new GrasshopperTile(Hive.Player.BLACK),-1,0);
        boolean validMove = false;
        try {
            validMove = new GrasshopperTile(Hive.Player.BLACK).isValidMove(board,-1,0,2,0);
        } catch (Hive.IllegalMove illegalMove) {
            //illegalMove.printStackTrace();
        }

        assertFalse(validMove);
    }
}
