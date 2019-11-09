package nl.hanze.hive;


import nl.hanze.hive.tiles.*;

import java.util.*;

public class Board {
    private Map<Hex, Stack<InsectTile>> hexTileMap;

    final int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};
    private int tileCount = 0;
    private List<InsectTile> boardTiles;
    private int tileCountWhite;
    private int tileCountBlack;

    public Board(){
        this.boardTiles = new ArrayList<>();
        this.hexTileMap = new HashMap<>();
    }

    @Override
    public Board clone(){
        Board copy = new Board();
        Map<Hex, Stack<InsectTile>> newHexTileMap = new HashMap<>();

        for (Map.Entry<Hex, Stack<InsectTile>> pair : hexTileMap.entrySet()){
            Hex location = pair.getKey();
            Stack<InsectTile> old = pair.getValue();
            Stack<InsectTile> nw = (Stack<InsectTile>) old.clone();
            newHexTileMap.put(location, nw);
        }

        copy.setHexTileMap(newHexTileMap);
        return copy;
    }

    public Map<Hex, Stack<InsectTile>> getHexTileMap(){
        return hexTileMap;
    }

    public void setHexTileMap(Map<Hex, Stack<InsectTile>> hexTileMap){
        this.hexTileMap = hexTileMap;
    }

    public List<Hex> getNeighbouringHexLocations(int q, int r) {
        return getNeighbouringHexLocations(new Hex(q,r));
    }

    public List<Hex> getNeighbouringHexLocations(Hex hex){
        List<Hex> neighbours = new ArrayList<>();

        for (int[] qr : directions){
            int q = hex.getQ() + qr[0];
            int r = hex.getR() + qr[1];
            neighbours.add(new Hex(q,r));
        }
        return neighbours;
    }

    private boolean hasTilesInNeighbouringHex(int q, int r) {
        Hex hex = new Hex(q, r);
        List<Hex> neighbourList = getNeighbouringHexLocations(hex);

        for(Hex neighbour : neighbourList){
            if (hasTileAt(neighbour)){
                return true;
            }
        }
        return false;
    }

    private boolean hasTileAt(int q, int r){
        return hasTileAt(new Hex(q,r));
    }

    private boolean hasTileAt(Hex hex){
        if(!hexTileMap.containsKey(hex)) return false;

        if(!this.hexTileMap.get(hex).isEmpty()) return true;

        return false;
    }
    public List<Hex> getNeighboursWithTiles(Hex hex){
        List<Hex> neighbours = getNeighbouringHexLocations(hex);
        List<Hex> neighboursWithTiles = new ArrayList<>();
        for (Hex neighbour : neighbours){
            Stack<InsectTile> tileStack = this.hexTileMap.get(neighbour);
            if (tileStack != null){
                if(tileStack.size() > 0)
                    neighboursWithTiles.add(neighbour);
            }
        }
        return neighboursWithTiles;
    }

    public List<Hex> getNeighboursWithoutTiles(Hex hex){
        List<Hex> neighbours = getNeighbouringHexLocations(hex);
        List<Hex> neighboursWithTiles = new ArrayList<>();
        for (Hex neighbour : neighbours){
            Stack<InsectTile> tileStack = this.hexTileMap.get(neighbour);
            if(tileStack == null){
                neighboursWithTiles.add(neighbour);

            }else {
                if(tileStack.size() == 0)
                    neighboursWithTiles.add(neighbour);
            }
        }
        return neighboursWithTiles;
    }


    public boolean isHexEmpty(int q, int r){
        Hex hex = new Hex(q,r);
        Stack<InsectTile> tileStack = this.hexTileMap.get(hex);
        return  (tileStack == null) || tileStack.size() == 0;
    }

    public void placeTile(InsectTile tile, int q, int r) {
        Hex hex = new Hex(q, r);
        Stack<InsectTile> tileStack = this.hexTileMap.get(hex);

        if (tileStack == null) {
            tileStack = new Stack<>();
        }

        tileStack.push(tile);
        this.hexTileMap.put(hex,tileStack);
    }

    public void removeTile(InsectTile tile, int q, int r){
        Hex hex = new Hex(q,r);
        Stack<InsectTile> tileStack = this.hexTileMap.get(hex);
        if(tileStack == null){
            System.out.println("Tilel not at this location");
            return;
        }
        InsectTile removed = tileStack.pop();
        if (!removed.equals(tile)){
            System.out.println("InsectTile not on top!!!");
            placeTile(removed, q, r);
            return;
        }

        if(tileStack.size() == 0){
            this.hexTileMap.remove(hex);
        }
        return;
    }

    public Stack<InsectTile> getTilesAtHexLocation(int q, int r) {
        return getTilesAtHexLocation(new Hex(q,r));

    }

    public Stack<InsectTile> getTilesAtHexLocation(Hex hex) {
        Stack<InsectTile> st = this.hexTileMap.get(hex);

        if (st == null) return new Stack<>();

        return st;
    }


    public InsectTile getTile(int q, int r) {
        Hex hex = new Hex(q,r);
        Stack<InsectTile> st = this.hexTileMap.get(hex);
        if (st == null) return null;

        return st.peek();
    }

    public void play(Hive.Tile tile, Hive.Player player, int q , int r){
        Hex hex = new Hex(q,r);
        Stack<InsectTile> tStack = hexTileMap.get(hex);
        if (tStack == null){
            tStack = new Stack<>();
            hexTileMap.put(hex, tStack);
        }
        tStack.push(createTileObjectFromEnum(tile, player));
        this.tileCount++;
    }

    private InsectTile createTileObjectFromEnum(Hive.Tile t, Hive.Player color){
        InsectTile o = null;
        switch (t) {
            case BEETLE:
                o = new BeetleTile(color);
                break;
            case GRASSHOPPER:
                o = new GrasshopperTile(color);
                break;
            case QUEEN_BEE:
                o = new QueenBeeTile(color);
                break;
            case SOLDIER_ANT:
                o = new SoldierAntTile(color);
                break;
            case SPIDER:
                o = new SpiderTile(color);
                break;
        }
        return o;
    }

    public void doMove(int fromQ, int fromR, int toQ, int toR) {
        InsectTile toMove = getTile(fromQ, fromR);
        removeTile(toMove, fromQ, fromR);
        placeTile(toMove, toQ, toR);
    }

    public void undoMove(int fromQ, int fromR, int toQ, int toR){
        doMove(toQ, toR, fromQ, fromR);
    }

    public boolean isQueenBeeSurrounded(Hive.Player p){
        InsectTile tempQueenBee = new QueenBeeTile(p);

        int foundQ = Integer.MIN_VALUE;
        int foundR = Integer.MIN_VALUE;

        for (Map.Entry<Hex, Stack<InsectTile>> pair : hexTileMap.entrySet()){
            int found = pair.getValue().search(tempQueenBee);
            if (found != -1){
                foundQ = pair.getKey().getQ();
                foundR = pair.getKey().getR();
                break;
            }
        }

        int neighbourcount = 0;
        for (int[] qr : directions){
            Stack<InsectTile> st = hexTileMap.get(new Hex(foundQ + qr[0], foundR + qr[1]));
            if( st != null){
                if (st.size() > 0) {
                    neighbourcount += 1;
                }
            }
        }
        return neighbourcount == 6;
    }

    public boolean canPlayTile(Hive.Player color, Hive.Tile tile, int q, int r) {
        if (!this.isHexEmpty(q, r)) {
            return false;
        }

        if (this.tileCount == 0) {
            return true;
        }

        boolean hasNeighbours = this.hasLocationNeighbouringTiles(q, r);

        if (!hasNeighbours) {
            return false;
        }

        if (this.tileCountWhite > 0 && this.tileCountBlack > 0) {
            //There are tiles of both players on the board
            //InsectTile has to be played next to it's own color tile
            List<Hex> hexList = this.getNeighbouringHexLocations(new Hex(q, r));

            for (Hex h : hexList) {
                Stack<InsectTile> st = hexTileMap.get(h);
                if (st != null) {
                    if (st.size() > 0) {
                        InsectTile t = st.peek();
                        Hive.Player neighbourColor = t.getPlayer();

                        if (neighbourColor != color) {
                            return false;
                        }
                    }
                }
            }

        }

        return true;
    }

    public boolean hasLocationNeighbouringTilesAfterMoving(int q_to, int r_to, int q_origin, int r_origin){
        Hex origin_hex = new Hex(q_origin, r_origin);

        //pop tile of the stack, it will get in the way when checking for neighbours
        InsectTile origin_tile = hexTileMap.get(origin_hex).pop();

        boolean ret_val = hasLocationNeighbouringTiles(q_to,r_to);

        //first tile back on the origin stack, this method doesnt moves tiles, it only checks
        hexTileMap.get(origin_hex).push(origin_tile);
        return ret_val;
    }

    private boolean hasLocationNeighbouringTiles(int q, int r) {
        Hex hex = new Hex(q, r);
        List<Hex> neighbourList = getNeighbouringHexLocations(hex);

        int neighbourcount = 0;

        for(Hex h : neighbourList){
            Stack<InsectTile> st = hexTileMap.get(h);
            if (st != null){
                if ( st.size() > 0 ) {
                    neighbourcount++;
                }
            }
        }

        return neighbourcount > 0;
    }

    public void updateTileCount(Participant p) {
        if (p.getColor() == Hive.Player.WHITE) {
            this.tileCountWhite += 1;
        } else {
            this.tileCountBlack += 1;
        }
    }

    public int getTileCount(Hive.Player c){
        if ( c == Hive.Player.WHITE ){
            return this.tileCountWhite;
        } else {
            return this.tileCountBlack;
        }
    }

    public boolean legalBoardAfterMoving(int fromQ, int fromR, int toQ, int toR) {
        Hex from = new Hex(fromQ, fromR);
        Hex to = new Hex(toQ, toR);
        return legalBoardAfterMoving(from, to);
    }

    public boolean legalBoardAfterMoving(Hex from, Hex to){
        List<Hex> nFrom = getNeighboursWithTiles(from);
        List<Hex> nTo = getNeighboursWithTiles(to);

        for(Hex neighbour: nFrom){
            if(to.getQ() != neighbour.getQ() || to.getR() != neighbour.getR()){
                if(!legalBoardSearch(from,to,neighbour, new ArrayList<>())) return false;
            }
        }
        return true;
    }

    private boolean legalBoardSearch(Hex from, Hex to, Hex current, List<Hex> visited){
        visited.add(current);

        if (current.equals(to)){
            return true;
        }

        if (!hexTileMap.containsKey(current)) return false;

        if(current.equals(from)) return false;

        for (Hex neighbour : getNeighbouringHexLocations(current)){
            if (!visited.contains(neighbour)){
                if (legalBoardSearch(from, to, neighbour, visited)) return true;
            }
        }
        return false;
    }

    //6
    public boolean canSlideFromAToB(Hex from, Hex to) {
        return this.canSlideFromAToB(from.getQ(), from.getR(), to.getQ(), to.getR());
    }

    public boolean canSlideFromAToB(int fromQ, int fromR, int toQ, int toR){
        Hex a = new Hex(fromQ, fromR);
        Hex b = new Hex(toQ, toR);

        List<Hex> nbA = getNeighbouringHexLocations(a);
        List<Hex> nbB = getNeighbouringHexLocations(b);

        if (!nbA.contains(b)) return false; //b not connected to a

        Hex n1 = null;
        Hex n2 = null;
        List<Hex> commonNeighbours = new ArrayList<>();
        int amountNeighboursWithTiles = 0;
        for (Hex h: nbA) {
            if (nbB.contains(h)) {

                if (n1 == null) {
                    n1 = h;
                }
                else {
                    n2 = h;
                }

                if (hasTileAt(h)) {
                    amountNeighboursWithTiles++;
                }
            }
        }

    //    if(amountNeighboursWithTiles == 0) {
     //       return false;// na slide verlaat de tile de hive, hierdoor geen buren.
      //  }

        if(amountNeighboursWithTiles == 1) {
            return true; //Eén buur na slide, hierdoor slide altijd mogelijk (hoeft niet door vernauwing)
        }


        //6B
        Stack<InsectTile> stackA = this.getTilesAtHexLocation(a);
        Stack<InsectTile> stackB = this.getTilesAtHexLocation(b);
        Stack<InsectTile> stackN1 = this.getTilesAtHexLocation(n1);
        Stack<InsectTile> stackN2 = this.getTilesAtHexLocation(n2);

        int min = Math.min(stackN1.size(), stackN2.size());
        int max = Math.max(stackA.size() - 1, stackB.size());

        return min <= max;
    }

    public boolean canPlayerMove(Participant p) {
        List<Hex> positions = new ArrayList<>(); //Alle locaties waar de huidige speler boven ligt.

        for (Map.Entry<Hex, Stack<InsectTile>> pair : hexTileMap.entrySet()) {
            if(pair.getValue().peek().getPlayer() == p.getColor()){
                positions.add(pair.getKey());
            }
        }

        if(positions.isEmpty()){
            return false;
        }

        for(Hex position: positions) {
            for (Map.Entry<Hex, Stack<InsectTile>> pair : hexTileMap.entrySet()) {
                List<Hex> neighbours = getNeighbouringHexLocations(pair.getKey());
                for(Hex neighbour: neighbours){
                    iMovable tileAtLocation = (iMovable) getTile(position.getQ(), position.getR());

                    boolean canMove = false;
                    try {
                        canMove = tileAtLocation.isValidMove(this, position.getQ(), position.getR(), neighbour.getQ(),neighbour.getR());
                    } catch (Hive.IllegalMove illegalMove) {
                        canMove = false;
                    }

                    if(canMove){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canTileBePlaced(Participant p) {
        InsectTile tile = p.getAvailableTiles().get(0);

        for (Map.Entry<Hex, Stack<InsectTile>> pair : hexTileMap.entrySet()){
            if(pair.getValue().peek().getPlayer() == p.getColor()){
                List<Hex> neighboursWoTiles = getNeighboursWithoutTiles(pair.getKey());
                for(Hex neighbour: neighboursWoTiles){
                    if(canPlayTile(p.getColor(), tile.getTile(), neighbour.getQ(),neighbour.getR())){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
