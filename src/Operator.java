import java.util.*;

/**
 *
 */
public class Operator {

    private static int len;
    private static Queue<State> options;
    private static HashMap<String, State> states = new HashMap<>();

    public static Queue<State> generator(State current, String algoName) {

        len = current.getBoard().length;
        options = new LinkedList<>();
        states.put(current.getState(), current);

        for (Location location : current.getEmptyPlace()) {
            int i = location.getX();
            int j = location.getY();
            validation(current, i, j, algoName);
        }

        return options;
    }

    /**
     * This function checks if we can move in each
     * direction without going out of bounds.
     * Also, we check if the new location does not contain '_',
     * because if it contains '_' we don't get any benefit from this move.
     * The check on the opposite move will be performed in the moveMarble function.
     *
     * @param current   - the State
     * @param i         - current row
     * @param j         - current column
     * @param algoName  - algorithm name
     */
    private static void validation(State current, int i, int j, String algoName) {

        // UP
        if (i > 0 && current.getBoard()[i - 1][j] != ('_')) {
            Operator.moveMarble(i, j, i - 1, j, current, algoName);
        }
        // DOWN
        if (i < len - 1 && current.getBoard()[i + 1][j] != ('_')) {
            Operator.moveMarble(i, j, i + 1, j, current, algoName);
        }
        // LEFT
        if (j > 0 && current.getBoard()[i][j - 1] != ('_')) {
            Operator.moveMarble(i, j, i, j - 1, current, algoName);
        }
        // RIGHT
        if (j < len - 1 && current.getBoard()[i][j + 1] != ('_')) {
            Operator.moveMarble(i, j, i, j + 1, current, algoName);
        }
    }

    /**
     * Here we are moving the marble where the
     * empty place is, and checking on the opposite move.
     *
     * @param i         - old row
     * @param j         - old column
     * @param x         - new row
     * @param y         - new column
     * @param node      - the State
     * @param algoName  - algorithm name
     */
    private static void moveMarble(int i, int j, int x, int y, State node, String algoName) {

        List<Location> blocks = new ArrayList<>();

        for (Location empty : node.getEmptyPlace()) {
            if (empty.getX() == i && empty.getY() == j) {
                blocks.add(new Location(x, y));
            }
            else {
                blocks.add(new Location(empty.getX(), empty.getY()));
            }
        }

        char[][] board = copyState(node.getBoard());

        int c = computeCost(board[x][y]);

        String move = "(" + (x+1) + "," + (y+1) + "):" + board[x][y] + ":(" + (i+1) + "," + (j+1) + ")";

        Operator.swap(board, i, j, x, y);

        State n = new State(node, move, board, c, blocks);
        if (n.equals(n.getParent().getParent())) {  // check opposite step
            return;
        }

        if (algoName.equals("A*") || algoName.equals("IDA*") || algoName.equals("DFBnB")) {
            if (states.containsKey(n.getState())) {
                if (n.getCost() > states.get(n.getState()).getCost()) {
                    return;
                }
                else {
                    states.remove(n.getState());
                    states.put(n.getState(), n);
                }
            }
        }

        options.add(n);
    }

    private static int computeCost(char c) {

        if (c == 'R' || c == 'Y') {
            return 1;
        }
        else if (c == 'B') {
            return 2;
        }
        else { // c == 'G'
            return 10;
        }
    }

    private static void swap(char[][] board, int i, int j, int x, int y) {

        char temp = board[i][j];
        board[i][j] = board[x][y];
        board[x][y] = temp;
    }

    private static char[][] copyState(char[][] board) {

        char[][] newState = new char[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                newState[i][j] = board[i][j];
            }
        }
        return newState;
    }
}