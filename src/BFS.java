import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends Algorithm {

    private static HashSet<String> FRONTIER;
    private static HashSet<String> EXPLORED;

    Queue<State> L;
    private String algoName;
    private int num;
    private boolean flag;

    public BFS(State startState, State goalState, boolean withOpen) {

        super(withOpen);

        this.algoName = "BFS";
        FRONTIER = new HashSet<>();
        EXPLORED = new HashSet<>();
        L = new LinkedList<>();
        search(startState, goalState);
    }

    @Override
    public void search(State startState, State goalState) {

        num = 0;

        L.add(startState);
        FRONTIER.add(startState.getState());

        while (!L.isEmpty() && !flag) {

            State current = L.poll();
            FRONTIER.remove(current.getState());
            EXPLORED.add(current.getState());

            print(current);

            Queue<State> allowedOperators = Operator.generator(current, algoName);
            num += allowedOperators.size();

            while (!allowedOperators.isEmpty()) {

                State g = allowedOperators.poll();

                if (!(EXPLORED.contains(g.getState())) && !(FRONTIER.contains(g.getState()))) {
                    if (isGoal(g, goalState)) {
                        createOutput(g, num);
                        flag = true;
                        break;
                    }

                    L.add(g);
                    FRONTIER.add(g.getState());
                }
            }
        }

        if (!flag) {
            noPath(num);
        }
    }
}