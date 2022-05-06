import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads the "input.txt" file,
 * sets all the variables,
 * and calls the right constructor by the name of the algorithm.
 */
public class Input {

    private String algo;                    // the first line of the file will determine which algorithm to use
    private boolean withOpen;               // the second line will determine whether to print the list open
                                            // to the screen at each stage of the search algorithm run or not
    private int boardSize;                  // the third row will determine in which variation these are: big board 5X5 or small board 3X3
    private State gameState;                // the initial arrangement of the board by rows will then appear
    private State goalState;                // then a line will appear that says "Goal state:" and after that will appear
                                            // the final arrangement of the board that needs to be reached
    private List<Location> emptyPlace;      // a list that holds empty places marked with '_'
    private List<String> inputs;            // we read the data from the file into this list. Each cell represents a row

    private static int COUNTER = 0;         // the counter is used to match each variable to the correct values from the inputs list

    public Input(String input) {

        inputs = new ArrayList<>();
        readFile(input);
        setVariables();
        switch (getAlgo()) {

            case "BFS":
                new BFS(getGameState(), getGoalState(), this.withOpen);
                break;
            case "DFID":
                new DFID(getGameState(), getGoalState(), this.withOpen);
                break;
            case "A*":
                new AStar(getGameState(), getGoalState(), this.withOpen);
                break;
            case "IDA*":
                new IDAStar(getGameState(), getGoalState(), this.withOpen);
                break;
            case "DFBnB":
                new DFBnB(getGameState(), getGoalState(), this.withOpen);
                break;
        }
    }

    private void readFile(String input) {

        try {
            File file = new File(input);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                inputs.add(data);
            }

            scanner.close();

//            for (String s : inputs) {
//                System.out.println(s);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setVariables() {

        setAlgo(inputs.get(COUNTER));
//        System.out.println(this.algo);

        setWithOpen(inputs.get(COUNTER));
//        System.out.println(this.withOpen);

        setBoardSize(inputs.get(COUNTER));
//        System.out.println(this.boardSize);

        char[][] initState = setBoard();
        gameState = new State(null, null, initState, 0, emptyPlace);
//        System.out.print(gameState.toString());

        char[][] finalState = setGoalState(inputs.get(COUNTER));
        goalState = new State(null, null, finalState, 0, emptyPlace);
//        System.out.println("Goal state:");
//        System.out.print(goalState.toString());

//        for (Location l : emptyPlace) {
//            System.out.println("(" + l.getX() + "," + l.getY() + ")");
//        }
    }

    public String getAlgo() {
        return algo;
    }

    private void setAlgo(String algo) {

        COUNTER++;
        this.algo = algo;
    }

    private void setWithOpen(String withOpen) {

        COUNTER++;
        if (withOpen.equals("with open")) {
            this.withOpen = true;
        }
        else if (withOpen.equals("no open")) {
            this.withOpen = false;
        }
    }

    private void setBoardSize(String boardSizeString) {

        COUNTER++;
        if (boardSizeString.equals("big")) {
            this.boardSize = 5;
        }
        else if (boardSizeString.equals("small")) {
            this.boardSize = 3;
        }
        this.emptyPlace = new ArrayList<>();
    }

    public State getGameState() {
        return gameState;
    }

    private char[][] setBoard() {

        char[][] state = new char[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            String[] temp = inputs.get(COUNTER).split(",");
            for (int j = 0; j < this.boardSize; j++) {
                state[i][j] = temp[j].charAt(0);
                if (temp[j].equals("_")) {
                    this.emptyPlace.add(new Location(i,j));
                }
            }
            COUNTER++;
        }

        return state;
    }

    public State getGoalState() {
        return goalState;
    }

    private char[][] setGoalState(String goalState) {
        if (goalState.equals("Goal state:")) {
            COUNTER++;
        }
        char[][] state = new char[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            String[] temp = inputs.get(COUNTER).split(",");
            for (int j = 0; j < this.boardSize; j++) {
                state[i][j] = temp[j].charAt(0);
            }
            COUNTER++;
        }

        return state;
    }
}
