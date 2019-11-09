package nl.hanze.hive;


import java.util.*;

public class Board {
    private Map<Hex, Stack<Tile>> hexTileMap;

    final int[][] directions = {{0,-1},{0,1},{1,0},{1,-1},{-1,0},{-1,1}};
    private int tileCount = 0;
    private List<Tile> boardTiles;
    private int tileCountWhite;
    private int tileCountBlack;

    public Board(){
        this.boardTiles = new ArrayList<>();
        this.hexTileMap = new HashMap<>();
    }

    public List<Tile> getBoardTiles(){
        return boardTiles;
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
            Stack<Tile> tileStack = this.hexTileMap.get(neighbour);
            if (tileStack != null){
                if(tileStack.size() > 0)
                    neighboursWithTiles.add(neighbour);
            }
        }
        return neighboursWithTiles;
    }

    public boolean isHexEmpty(int q, int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> tileStack = this.hexTileMap.get(hex);
        return  (tileStack == null) || tileStack.size() == 0;
    }

    public void placeTile(Tile tile, int q, int r) {
        Hex hex = new Hex(q, r);
        Stack<Tile> tileStack = this.hexTileMap.get(hex);

        if (tileStack == null) {
            tileStack = new Stack<>();
        }

        tileStack.push(tile);
        this.hexTileMap.put(hex,tileStack);
    }

    public void removeTile(Tile tile, int q, int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> tileStack = this.hexTileMap.get(hex);
        if(tileStack == null){
            System.out.println("Tilel not at this location");
            return;
        }
        Tile removed = tileStack.pop();
        if (!removed.equals(tile)){
            System.out.println("Tile not on top!!!");
            placeTile(removed, q, r);
            return;
        }

        if(tileStack.size() == 0){
            this.hexTileMap.remove(hex);
        }
        return;
    }

    public Stack<Tile> getTilesAtLocation(int q, int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> st = this.hexTileMap.get(hex);

        if (st == null) return new Stack<>();

        return st;
    }


    public Tile getTile(int q, int r) {
        Hex hex = new Hex(q,r);
        Stack<Tile> st = this.hexTileMap.get(hex);
        if (st == null) return null;

        return st.peek();
    }

    public void play(Hive.Tile tile, Hive.Player player, int q , int r){
        Hex hex = new Hex(q,r);
        Stack<Tile> tStack = hexTileMap.get(hex);
        if (tStack == null){
            tStack = new Stack<>();
            hexTileMap.put(hex, tStack);
        }
        tStack.push(new Tile(player, tile));
        this.tileCount++;
    }

    public void doMove(int fromQ, int fromR, int toQ, int toR) {
        Tile toMove = getTile(fromQ, fromR);
        removeTile(toMove, fromQ, fromR);
        placeTile(toMove, toQ, toR);
    }

    public void undoMove(int fromQ, int fromR, int toQ, int toR){
        doMove(toQ, toR, fromQ, fromR);
    }

    public boolean isQueenBeeSurrounded(Hive.Player p){
        Tile tempQueenBee = new Tile(p, Hive.Tile.QUEEN_BEE);

        int foundQ = Integer.MIN_VALUE;
        int foundR = Integer.MIN_VALUE;

        for (Map.Entry<Hex, Stack<Tile>> pair : hexTileMap.entrySet()){
            int found = pair.getValue().search(tempQueenBee);
            if (found != -1){
                foundQ = pair.getKey().getQ();
                foundR = pair.getKey().getR();
                break;
            }
        }

        int neighbourcount = 0;
        for (int[] qr : directions){
            Stack<Tile> st = hexTileMap.get(new Hex(foundQ + qr[0], foundR + qr[1]));
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
            //Tile has to be played next to it's own color tile
            List<Hex> hexList = this.getNeighbouringHexLocations(new Hex(q, r));

            for (Hex h : hexList) {
                Stack<Tile> st = hexTileMap.get(h);
                if (st != null) {
                    if (st.size() > 0) {
                        Tile t = st.peek();
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
        Tile origin_tile = hexTileMap.get(origin_hex).pop();

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
            Stack<Tile> st = hexTileMap.get(h);
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

    public boolean legalBoardAfterMoving(int fromQ, int fromR, int toQ, int toR){
        Hex from = new Hex(fromQ, fromR);
        Hex to = new Hex(toQ, toR);
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
}
