import java.util.*;

public class AStar extends Algorithm {

    private static HashMap<String, State> FRONTIER;
    private static HashSet<String> EXPLORED;

    private String algoName;
    private int num;
    private boolean flag;

    public AStar(State startState, State goalState, boolean withOpen) {

        super(withOpen);

        this.algoName = "A*";
        FRONTIER = new HashMap<>();
        EXPLORED = new HashSet<>();
        search(startState, goalState);
    }

    @Override
    public void search(State startState, State goalState) {

        PriorityQueue<State> queue = new PriorityQueue<>(new MyComparator());
        queue.add(startState);
        FRONTIER.put(startState.getState(), startState);
        num = 0;

        while (!queue.isEmpty()) {

            State current = queue.poll();

            if (isGoal(current, goalState)) {
                createOutput(current, num);
                flag = true;
                break;
            }

            EXPLORED.add(current.getState());

            print(current);

            Queue<State> allowedOperators = Operator.generator(current, algoName);
            num += allowedOperators.size();

            while (!allowedOperators.isEmpty()) {

                State g = allowedOperators.poll();

                if (!(EXPLORED.contains(g.getState())) && !(FRONTIER.containsKey(g.getState()))) {
                    g.setHeuristicValue(goalState);
                    queue.add(g);
                    FRONTIER.put(g.getState(), g);
                }
                else if (queue.contains(g) && FRONTIER.containsKey(g.getState())
                        && FRONTIER.get(g.getState()).getCost() > g.getCost()) {
                    queue.remove(g);
                    queue.add(g);
                    FRONTIER.put(g.getState(), g);
                }
            }
        }

        if (!flag) {
            noPath(num);
        }
    }
}
