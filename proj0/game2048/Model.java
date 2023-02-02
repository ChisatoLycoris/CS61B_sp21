package game2048;

import java.util.Formatter;
import java.util.Iterator;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author Ming
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        board.startViewingFrom(side);
        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        int size = size();
        Tile[] tiles = new Tile[size];
        for(int i = 0; i < size; i ++) {
            /* Create local variable reference to the tile each row. */
            for(int j = 0; j < size; j ++) {
                tiles[j] = board.tile(i, j);
            }
            /* Record the last index of tiles[] that has merged to prevent merging again.
             * Initialized -1 as nothing merged yet. */
            int merged = -1;
            for(int j = size - 2; j > -1; j--) {
                /* Escape checking if there is no tile at current position. */
                if (tiles[j] == null) {
                    continue;
                }
                /* Record the index that we are going to check with current tile. */
                int target = j + 1;
                /* Adjust the target if it is null and still have space on the board. */
                while (tiles[target] == null && target < size - 1) {
                    target = target + 1;
                }
                /* If target still remains empty, it means that the edge of the board is empty.
                 * We can move current tile to the target place since it was empty. */
                if (tiles[target] == null) {
                    tiles[target] = tiles[j];
                    board.move(i, target, tiles[j]);
                    tiles[j] = null;
                    changed = true;
                    continue;
                }
                /* If target's value equals current tile's value, then we need to further check.
                 * If there is empty space between target and current tile, we can move current tile to that space.
                 * Otherwise, current tile just remains at same place. */
                if (tiles[target].value() != tiles[j].value()) {
                    if (target - 1 != j) {
                        tiles[target - 1] = tiles[j];
                        board.move(i, target - 1, tiles[j]);
                        tiles[j] = null;
                        changed = true;
                    }
                    continue;
                }
                /* Program running to here implies some information:
                 * 1. Current place is not empty.
                 * 2. Target place is not empty.
                 * 3. There is no tile between target tile and current tile.
                 * 4. The value of current tile and the value of target tile are the same.
                 * However, we still have to check if target tile is already merged.
                 * If target tile already merged, then we cannot merge current tile to target tile. */
                if (target == merged) {
                    /* If there is empty space between target and current tile,
                     * then we move current tile to that space. */
                    if (target - 1 != j) {
                        tiles[target - 1] = tiles[j];
                        board.move(i, target - 1, tiles[j]);
                        tiles[j] = null;
                        changed = true;
                    }
                    continue;
                }
                /* Finally, after several checks we can merge current tile to target tile. */
                score += tiles[j].value() * 2;
                board.move(i, target, tiles[j]);
                tiles[j] = null;
                tiles[target] = board.tile(i, target);
                merged = target;
                changed = true;

            }
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }
        board.startViewingFrom(Side.NORTH);
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        /* using Iterator
        Iterator<Tile> tileIterator = b.iterator();
        while (tileIterator.hasNext()) {
            if (tileIterator.next() == null) {
                return true;
            }
        }
        return false;
        */
        /* using traditional for loop */
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        /* using Iterator
        Iterator<Tile> tileIterator = b.iterator();
        while (tileIterator.hasNext()) {
            Tile test = tileIterator.next();
            if (test != null && test.value() == MAX_PIECE) {
                return true;
            }
        }
        return false;
        */
        /* using traditional for loop */
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                Tile test = b.tile(i, j);
                if (test != null && test.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        /* if there is at least one empty space on the board then return*/
        if (emptySpaceExists(b)) {
            return true;
        }
        /* check tile value with the prev and next horizontal one */
        for (int i = 0; i < b.size(); i += 2) {
            for (int j = 0; j < b.size(); j++) {
                Tile test = b.tile(i, j);
                if (i - 1 > 0) {
                    Tile testLeft = b.tile(i-1, j);
                    if (test.value() == testLeft.value()) {
                        return true;
                    }
                }
                if (i + 1 < b.size()) {
                    Tile testRight = b.tile(i+1, j);
                    if (test.value() == testRight.value()) {
                        return true;
                    }
                }
            }
        }
        /* check tile value with the prev and next vertical one*/
        for (int i = 0; i < b.size(); i ++) {
            for (int j = 0; j < b.size(); j += 2) {
                Tile test = b.tile(i, j);
                if (j - 1 > 0) {
                    Tile testUp = b.tile(i, j - 1);
                    if (test.value() == testUp.value()) {
                        return true;
                    }
                }
                if (j + 1 < b.size()) {
                    Tile testDown = b.tile(i, j + 1);
                    if (test.value() == testDown.value()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
