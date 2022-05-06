import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

public class IDAStar extends Algorithm {

    private static HashMap<String, State> FRONTIER;
    private static Stack<State> L;

    private String algoName;
    private int num;
    private boolean flag;

    public IDAStar(State startState, State goalState, boolean withOpen) {

        super(withOpen);

        this.algoName = "IDA*";
        FRONTIER = new HashMap<>();
        L = new Stack<>();
        search(startState, goalState);
    }

    @Override
    public void search(State startState, State goalState) {

        num = 1;

        startState.setHeuristicValue(goalState);
        int t = startState.getHeuristicValue();

        while (t < Integer.MAX_VALUE) {

            int minF = Integer.MAX_VALUE;

            L.push(startState);
            FRONTIER.put(startState.getState(), startState);

            while (!L.empty() && !flag) {

                State current = L.pop();

                print(current);

                if (current.getMark().equals("out")) {
                    FRONTIER.remove(current.getState());
                }
                else {
                    current.setMark("out");
                    L.add(current);

                    Queue<State> allowedOperators = Operator.generator(current, algoName);
                    num += allowedOperators.size();

                    while (!allowedOperators.isEmpty()) {

                        State g = allowedOperators.poll();

                        g.setHeuristicValue(goalState);
                        int f = g.getCost() + g.getHeuristicValue();

                        if (f > t) {
                            minF = Math.min(minF, f);
                            continue;
                        }
                        if (FRONTIER.containsKey(g.getState()) && g.getMark().equals("out")) {
                            continue;
                        }
                        if (FRONTIER.containsKey(g.getState()) && !g.getMark().equals("out")) {

                            double gTagVal = FRONTIER.get(g.getState()).getCost() + FRONTIER.get(g.getState()).getHeuristicValue();
                            double gVal = g.getCost() + g.getHeuristicValue();

                            if (gTagVal > gVal) {
                                L.remove(g);
                                FRONTIER.remove(g.getState());
                            }
                            else {
                                continue;
                            }
                        }
                        if (isGoal(g, goalState)) {
                            createOutput(g, num);
                            t = Integer.MAX_VALUE;
                            flag = true;
                            break;
                        }

                        L.add(g);
                        FRONTIER.put(g.getState(), g);
                    }
                }
            }

            t = minF;
            startState.setMark("");
        }

        if (!flag) {
            noPath(num);
        }
    }
}