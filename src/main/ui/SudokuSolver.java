package ui;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;
import model.SudokuSolver9By9;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SudokuSolver {

    private static final int BOARD_SIZE_9BY9 = 9;
    private static final String UNASSIGNED_REPRESENTATION = "-";
    private static final String JSON_STORAGE = "./data/answers.json";

    private Scanner scanner;
    private boolean shouldRun = true;
    private int[][] questionSudokuBoard;
    private int[][] answerSudokuBoard;
    private SudokuSolver9By9 solve9By9;
    private SudokuAnswerBoards listOfAnswerBoards;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: Runs the Sudoku Solver
    public SudokuSolver() {
        scanner = new Scanner(System.in);
        listOfAnswerBoards = new SudokuAnswerBoards("Sudoku Board Answers");
        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);
        runSudokuSolver();
    }

    //MODIFIES: this
    //EFFECTS: Runs the Sudoku Solver
    public void runSudokuSolver() {
        String userInput =  null;
        scanner = new Scanner(System.in);

        while (shouldRun) {
            displayOptions();
            userInput = scanner.next();

            if (userInput.equalsIgnoreCase("q")) {
                shouldRun = false;
            } else {
                processCommand(userInput);
            }
        }
        System.out.print("\n Thank you for using my sudoku solver! \n");
    }

    //EFFECTS: Displays a series of options for the user
    private void displayOptions() {
        System.out.println("\n Please select from:");
        System.out.println("\t i -> Input and Solve Sudoku Question Board");
        System.out.println("\t d -> Display previous Sudoku Answer Boards");
        System.out.println("\t l -> Load from File");
        System.out.println("\t s -> Save to File");
        System.out.println("\t q -> Quit Sudoku Solver");
    }

    //MODIFIES: this
    //EFFECTS: Processes user commands
    private void processCommand(String userInput) {
        if (userInput.equalsIgnoreCase("i")) {
            runQuestionSolver();
        } else if (userInput.equalsIgnoreCase("d")) {
            displayAnswerBoards();
        } else if (userInput.equalsIgnoreCase("l")) {
            saveAnswerBoards();
        } else if (userInput.equalsIgnoreCase("s")) {
            loadAnswerBoards();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    //EFFECTS: Takes input, runs it through the solver, and displays the solution, or returns a false statement.
    private void runQuestionSolver() {
        questionSudokuBoard = new int[9][9];
        getQuestionSudokuBoard(questionSudokuBoard);
        solve9By9 = new SudokuSolver9By9(questionSudokuBoard);
        System.out.print("\n Here is your question board: \n");
        displayQuestionBoard(questionSudokuBoard);
        if (solve9By9.solveBoard(questionSudokuBoard)) {
            getSolvedSudokuBoard(solve9By9);
        } else {
            System.out.print("\n Unfortunately, a solution doesn't exist :( \n");
        }
    }

    //EFFECTS: Prints all the SudokuAnswerBoards in the List to the console
    private void displayAnswerBoards() {
        List<SudokuAnswerBoard> boards = listOfAnswerBoards.returnList();
        for (SudokuAnswerBoard board : boards) {
            System.out.println(board);
        }
    }

    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //EFFECTS: Saves the SudokuAnswerBoards to File
    private void saveAnswerBoards() {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(listOfAnswerBoards);
            jsonWriter.closeWriter();
            System.out.println("Saved " + listOfAnswerBoards.getName() + " to " + JSON_STORAGE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORAGE);
        }
    }

    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //MODIFIES: this
    //EFFECTS: Loads SudokuAnswerBoards from File
    private void loadAnswerBoards() {
        try {
            listOfAnswerBoards = jsonReader.read();
            System.out.println("Loaded " + listOfAnswerBoards.getName() + " from " + JSON_STORAGE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }


    //EFFECTS: Gets question board from user input
    private void getQuestionSudokuBoard(int[][] questionSudokuBoard) {
        questionSudokuBoard[0] = getRowOne();
        questionSudokuBoard[1] = getRowTwo();
        questionSudokuBoard[2] = getRowThree();
        questionSudokuBoard[3] = getRowFour();
        questionSudokuBoard[4] = getRowFive();
        questionSudokuBoard[5] = getRowSix();
        questionSudokuBoard[6] = getRowSeven();
        questionSudokuBoard[7] = getRowEight();
        questionSudokuBoard[8] = getRowNine();
    }

    //EFFECTS: Gets and displays solved sudoku board
    private void getSolvedSudokuBoard(SudokuSolver9By9 solve9By9) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n Here is the solution: \n");
        answerSudokuBoard = solve9By9.getSolvedBoard();
        displaySolvedBoard(answerSudokuBoard);
        System.out.print("\n Please enter a name for your Sudoku Answer Board: ");
        String answerBoardName = scanner.nextLine();
        listOfAnswerBoards.add(new SudokuAnswerBoard(answerBoardName, answerSudokuBoard));
    }

    //EFFECTS: Displays question sudoku board.
    private void displayQuestionBoard(int[][] questionBoard) {
        getBoardDisplay(questionBoard);
    }

    //EFFECTS: Displays solved sudoku board.
    private void displaySolvedBoard(int[][] answerBoard) {
        getBoardDisplay(answerBoard);
    }

    //EFFECTS: Displays a given matrix in a 9 by 9 board
    public void getBoardDisplay(int[][] board) {
        getRowDivider();
        for (int rowIndex = 0; rowIndex < BOARD_SIZE_9BY9; rowIndex++) {
            if (rowIndex % 3 == 0 && rowIndex != 0) {
                getRowDivider();
            }
            for (int columnIndex = 0; columnIndex < BOARD_SIZE_9BY9; columnIndex++) {
                if (columnIndex % 3 == 0) {
                    getColumnDivider();
                }
                if (board[rowIndex][columnIndex] == 0) {
                    System.out.print(" " + UNASSIGNED_REPRESENTATION + " ");
                } else {
                    System.out.print(" " + board[rowIndex][columnIndex] + " ");
                }
            }
            getColumnDivider();
            System.out.println();
        }
        getRowDivider();
    }

    //EFFECTS: Displays row divider => Allows the design of the divider to be changed
    private void getRowDivider() {
        System.out.println("...............................");
    }

    //EFFECTS: Displays column divider => Allows the design of the divider to be changed
    private void getColumnDivider() {
        System.out.print("|");
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the first row of a sudoku board
    private int[] getRowOne() {
        int[] row1 = new int[BOARD_SIZE_9BY9];
        promptMessage(1);
        return getRowInt(row1);
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the second row of a sudoku board
    private int[] getRowTwo() {
        int[] row2 = new int[BOARD_SIZE_9BY9];
        promptMessage(2);
        return getRowInt(row2);
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the third row of a sudoku board
    private int[] getRowThree() {
        int[] row3 = new int[BOARD_SIZE_9BY9];
        promptMessage(3);
        return getRowInt(row3);
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the fourth row of a sudoku board
    private int[] getRowFour() {
        int[] row4 = new int[BOARD_SIZE_9BY9];
        promptMessage(4);
        return getRowInt(row4);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the fifth row of a sudoku board
    private int[] getRowFive() {
        int[] row5 = new int[BOARD_SIZE_9BY9];
        promptMessage(5);
        return getRowInt(row5);
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the sixth row of a sudoku board
    private int[] getRowSix() {
        int[] row6 = new int[BOARD_SIZE_9BY9];
        promptMessage(6);
        return getRowInt(row6);
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the seventh row of a sudoku board
    private int[] getRowSeven() {
        int[] row7 = new int[BOARD_SIZE_9BY9];
        promptMessage(7);
        return getRowInt(row7);
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the eighth row of a sudoku board
    private int[] getRowEight() {
        int[] row8 = new int[BOARD_SIZE_9BY9];
        promptMessage(8);
        return getRowInt(row8);
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the ninth row of a sudoku board
    private int[] getRowNine() {
        int[] row9 = new int[BOARD_SIZE_9BY9];
        promptMessage(9);
        return getRowInt(row9);
    }

    //REQUIRES: Number in [1, 9]
    //EFFECTS: Prints out prompt message depending on the sudoku row
    private void promptMessage(int num) {
        System.out.println();
        System.out.print("Please enter the numbers in the " + getRowNum(num) + " row of a 9 x 9 sudoku board: \n");
        System.out.print("Please represent empty spaces/unassigned numbers with a 0! \n");
        System.out.print("Press enter after each entry! \n");
    }


    //REQUIRES: Number in [1, 9]
    //EFFECTS: return corresponding string to given row number.
    private String getRowNum(int num) {
        if (num == 1) {
            return "first";
        } else if (num == 2) {
            return "second";
        } else if (num == 3) {
            return "third";
        } else if (num == 4) {
            return "fourth";
        } else if (num == 5) {
            return "fifth";
        } else if (num == 6) {
            return "sixth";
        } else if (num == 7) {
            return "seventh";
        } else if (num == 8) {
            return "eighth";
        } else if (num == 9) {
            return "ninth";
        }
        return null;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for every row of a sudoku board
    private int[] getRowInt(int[] row) {
        for (int i = 0; i < BOARD_SIZE_9BY9; i++) {
            row[i] = scanner.nextInt();
        }
        return row;
    }

}







