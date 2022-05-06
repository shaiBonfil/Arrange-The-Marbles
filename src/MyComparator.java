import java.util.Comparator;

public class MyComparator implements Comparator<State> {

    @Override
    public int compare(State o1, State o2) {

        if (o1.getHeuristicValue() + o1.getCost() > o2.getHeuristicValue() + o2.getCost()) {
            return 1;
        }
        else if (o1.getHeuristicValue() + o1.getCost() < o2.getHeuristicValue() + o2.getCost()) {
            return -1;
        }
        else {
            // if f(n) is equal, then compare it by the depth and take the state with the smallest depth
            return Integer.compare(o1.getDepth(), o2.getDepth());
        }
    }
}