import java.util.Stack;

/**
 * This is an abstract class for any class that implements an algorithm.
 * In the constructor, we initialize an instance of the Output class, and the clock starts running.
 * In addition, we have the "print", "isGoal", "createOutput" and "noPath" functions
 * whose implementation is the same as all the algorithms.
 * There is also the abstract function "search" that each class that implements an algorithm,
 * will implement according to its own algorithm.
 */
public abstract class Algorithm {

    private Output output;
    private boolean withOpen;

    protected String DFIDPath;    // used for recursive DFID

    public Algorithm(boolean withOpen) {

        this.withOpen = withOpen;
        this.output = new Output(this.withOpen);
    }

    abstract public void search(State startState, State goalState);

    public void print(State current) {
        if (this.withOpen) {
            System.out.println(current.getState());
        }
    }

    public boolean isGoal(State current, State goal) {
        return current.getState().equals(goal.getState());
    }

    public void createOutput(State goal, int num) {

        StringBuilder path = new StringBuilder();   // will be the path answer
        int cost = 0;                               // will be the cost answer

        Stack<String> stringStack = new Stack<>();  // stack that holds all the moves from the startState to the goalState
        while (goal.getParent() != null) {
            stringStack.push(goal.getMove());

            // cost
            cost += goal.getCost();

            goal = goal.getParent();
        }

        // path
        while (!stringStack.empty()) {
            path.append(stringStack.pop());
            path.append("--");
        }
        path.delete(path.length() - 2, path.length()); // delete last -- from the path string

        this.DFIDPath = path + "";

        // write to output.txt file
        output.setEndTime(System.currentTimeMillis());
        output.writeToFile(path.toString(), num, cost + "");
    }

    public void noPath(int num) {
        output.setEndTime(System.currentTimeMillis());
        output.writeToFile("no path", num, "inf");
    }
}