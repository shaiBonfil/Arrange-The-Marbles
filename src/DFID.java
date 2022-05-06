import java.util.HashSet;
import java.util.Queue;

public class DFID extends Algorithm {

    private static HashSet<String> FRONTIER;

    private String algoName;
    private int num;
    private String result;

    public DFID(State startState, State goalState, boolean withOpen) {

        super(withOpen);

        this.algoName = "DFID";
        search(startState, goalState);
    }

    @Override
    public void search(State startState, State goalState) {

        num = 1;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            FRONTIER = new HashSet<>();
            result = limitedDFS(startState, goalState, i, FRONTIER);
            if (!result.equals("cutoff")) {
                return;
            }
        }
    }

    private String limitedDFS(State current, State goal, int limit, HashSet<String> openList) {

        print(current);

        if (isGoal(current, goal)) {
            createOutput(current, num);
            result = DFIDPath;
            return result;
        }
        else if (limit == 0) {
            return "cutoff";
        }
        else {
            openList.add(current.getState());
            boolean isCutoff = false;

            Queue<State> allowedOperators = Operator.generator(current, algoName);
            num += allowedOperators.size();

            while (!allowedOperators.isEmpty()) {

                State g = allowedOperators.poll();

                if (!openList.contains(g.getState())) {
                    result = limitedDFS(g, goal, limit - 1, openList);
                    if (result.equals("cutoff")) {
                        isCutoff = true;
                    }
                    else if (!result.equals("fail")) {
                        return result;
                    }
                }
            }

            openList.remove(current.getState());

            if (isCutoff) {
                return "cutoff";
            }
            else {
                return "fail";
            }
        }
    }
}
