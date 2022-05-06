import java.util.*;

public class DFBnB extends Algorithm {

    private static HashMap<String, State> FRONTIER;
    private static ArrayList<State> result;

    private Stack<State> L;
    private String algoName;
    private int num;
    private boolean flag;

    public DFBnB(State startState, State goalState, boolean withOpen) {

        super(withOpen);

        this.algoName = "DFBnB";
        FRONTIER = new HashMap<>();
        L = new Stack<>();
        result = new ArrayList<>();
        search(startState, goalState);
    }

    @Override
    public void search(State startState, State goalState) {

        num = 1;

        L.add(startState);
        FRONTIER.put(startState.getState(), startState);

        int t = Integer.MAX_VALUE;

        while (!L.empty() && !flag) {

            State n = L.pop();

            print(n);

            if (n.getMark().equals("out")) {
                FRONTIER.remove(n.getState());
            }
            else {
                n.setMark("out");

                Queue<State> allowedOperators = Operator.generator(n, algoName);
                num += allowedOperators.size();

                // sort the nodes in "allowedOperators" according to their f values
                ArrayList<State> allowedOperatorsAsList = new ArrayList<>(allowedOperators);
                for (State state : allowedOperatorsAsList) {
                    state.setHeuristicValue(goalState);
                }
                allowedOperatorsAsList.sort(new MyComparator());
                ArrayList<State> temp = new ArrayList<>(allowedOperatorsAsList); // for ConcurrentModificationException

                for (State g : temp) {
                    int f = g.getCost() + g.getHeuristicValue();
                    if (f >= t) {
                        for (State s : temp) {
                            if ((s.getCost() + s.getHeuristicValue()) >= t) {
                                allowedOperatorsAsList.remove(s);
                            }
                        }
                    }
                    else if (FRONTIER.containsKey(g.getState()) && g.getMark().equals("out")) {
                        allowedOperatorsAsList.remove(g);
                    }
                    else if (FRONTIER.containsKey(g.getState()) && !g.getMark().equals("out")) {
                        int gTagVal = FRONTIER.get(g.getState()).getCost() +
                                FRONTIER.get(g.getState()).getHeuristicValue();
                        int gVal = g.getCost() + g.getHeuristicValue();

                        if (gTagVal <= gVal) {
                            allowedOperatorsAsList.remove(g);
                        }
                        else {
                            L.remove(g);
                            FRONTIER.remove(g.getState());
                        }
                    }
                    else if (isGoal(g, goalState)) {
                        t = g.getCost() +g.getHeuristicValue();
                        createOutput(g, num);
                        for (State path : L) {
                            if (path.getMark().equals("out")) {
                                result.add(path);
                            }
                        }
                        allowedOperatorsAsList.removeIf(state -> allowedOperatorsAsList.indexOf(g) < allowedOperatorsAsList.indexOf(g));
                        allowedOperatorsAsList.remove(g);
                        flag = true;
                    }

                    Collections.reverse(allowedOperatorsAsList);
                    L.addAll(allowedOperatorsAsList);
                    for (State s : allowedOperatorsAsList) {
                        FRONTIER.put(s.getState(), s);
                    }
                }
            }
        }

        if (!flag) {
            noPath(num);
        }
    }
}