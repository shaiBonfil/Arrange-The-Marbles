import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements a state of the game.
 * Each state is a node on the searching graph,
 * that is built during the algorithm running.
 */
public class State {

    private State parent;               // to know what state is the parent of this state
    private String move;                // to restore the path
    private String state;               // to restore the state as a string:
                                        // In Operator class as a key for the HashMap
                                        // In Algorithm class for the isGoal method
    private int cost;                   // used to restore the cost to this state
    private char[][] board;             // the board of this state
    private List<Location> emptyPlace;  // empty spots on the board
    private int depth;                  // to restore the depth to this state
    private int heuristicValue;         // to restore the heuristic value of this state
    private String mark;                // for IDA* and DFBnB algorithms

    // an instance of Heuristic class for the setHeuristicValue function
    private static Heuristic heuristic = new Heuristic();

    public State(State parent, String move, char[][] node, int cost, List<Location> location) {

        this.parent = parent;
        this.move = move;
        setState(node);
        this.cost += cost;
        setEmptyPlace(location);
        setDepth();
        this.mark = "";
    }

    public State getParent() {
        return parent;
    }

    public String getMove() {
        return move;
    }

    public String getState() {
        return state;
    }

    private void setState(char[][] node) {

        StringBuilder str = new StringBuilder();
        this.board = new char[node.length][node[0].length];
        for (int i = 0; i < node.length; i++) {
            for (int j = 0; j < node[0].length; j++) {
                this.board[i][j] = node[i][j];
                str.append(node[i][j]);
                str.append(" ");
            }
            str.append("\n");
        }
        this.state = str.toString();
//        System.out.println(this.state);
    }

    public int getCost() {
        return cost;
    }

    public List<Location> getEmptyPlace() {
        return emptyPlace;
    }

    private void setEmptyPlace(List<Location> list) {

        this.emptyPlace = new ArrayList<>();
        for (Location l : list) {
            this.emptyPlace.add(l);
        }
//        System.out.println("location");
//        System.out.println(this.emptyPlace);
    }

    public char[][] getBoard() {
        return board;
    }

    public int getDepth() {
        return depth;
    }

    private void setDepth() {
        if (parent == null) {
            depth = 0;
        }
        else {
            depth = parent.depth + 1;
        }
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(State goal) {
        this.heuristicValue = heuristic.compute(this, goal);
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        return this.state.equals(state.state);
    }
}