import java.io.FileWriter;
import java.io.IOException;

/**
 * This class writes the "output.txt" file in the following format:
 * 1. In the first line of the file, write the series of actions found by the algorithm.
 * 2. In the second line, write "Num: " and then the number of nodes generated.
 * 3. In the third line, write "Cost: " and then the cost of the solution found.
 * 4. In the fourth line, write the time it took for the algorithm to find the solution (in seconds).
 */
public class Output {

    private String path;            // a variable that holds the path to the solution
    private String num;             // a variable that holds the number of nodes generated
    private String cost;            // a variable that holds the cost of the solution
    private long startTime;         // a variable that holds the time that the algorithm started running
    private long endTime;           // a variable that holds the time that the algorithm stopped running
    private boolean withOpen;       // boolean variable to know if all output will be written only to the
                                    // output.txt file or also to print on the screen

    public Output(boolean withOpen) {
        this.withOpen = withOpen;
        this.startTime = System.currentTimeMillis();
    }

    public void writeToFile(String path, int num, String cost) {

        try {
            FileWriter file = new FileWriter("output.txt");

            setPath(path);
            file.write(this.path);

            setNum(num);
            file.write(this.num);

            setCost(cost);
            file.write(this.cost);

            file.write(setTime() + " seconds");

            file.close();
//            System.out.println("The output.txt file was written successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPath(String path) {
        this.path = path + "\n";
    }

    public void setNum(int num) {
        this.num = "Num: " + num + "\n";
    }

    public void setCost(String cost) {
        this.cost = "Cost: " + cost + "\n";
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String setTime() {
        String t = (this.endTime - this.startTime) / 1000.0 + "";
        return t;
    }
}
