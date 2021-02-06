package ui;

import model.SudokuSolver9By9;

import java.util.Scanner;

public class SudokuSolver {

    private static final int BOARD_SIZE_9BY9 = 9;
    private static final String UNASSIGNED_REPRESENTATION = "-";

    private Scanner scanner;
    private int[][] questionSudokuBoard;
    private int[][] answerSudokuBoard;

    private int[] row1;
    private int[] row2;
    private int[] row3;
    private int[] row4;
    private int[] row5;
    private int[] row6;
    private int[] row7;
    private int[] row8;
    private int[] row9;

    //Constructor
    public SudokuSolver() {
        runSudokuSolver();

    }

    //MODIFIES: this
    //EFFECTS:
    public void runSudokuSolver() {
        scanner = new Scanner(System.in);
        questionSudokuBoard = new int[9][9];

        questionSudokuBoard[0] = getRowOne();
        questionSudokuBoard[1] = getRowTwo();
        questionSudokuBoard[2] = getRowThree();
        questionSudokuBoard[3] = getRowFour();
        questionSudokuBoard[4] = getRowFive();
        questionSudokuBoard[5] = getRowSix();
        questionSudokuBoard[6] = getRowSeven();
        questionSudokuBoard[7] = getRowEight();
        questionSudokuBoard[8] = getRowNine();

        SudokuSolver9By9 solve9By9 = new SudokuSolver9By9(questionSudokuBoard);

        System.out.print("\n Here is your question board: \n");

        displayQuestionBoard(questionSudokuBoard);

        if (solve9By9.solveBoard(questionSudokuBoard)) {
            System.out.print("\n Here is the solution: \n");
            answerSudokuBoard = solve9By9.getSolvedBoard();
            displaySolvedBoard(answerSudokuBoard);
            System.out.print("\n Thank you for using my sudoku solver! \n");
        } else {
            System.out.print("Unfortunately, a solution doesn't exist :( \n");
            System.out.print("Please try another question sudoku. \n");
        }

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
        row1 = new int[BOARD_SIZE_9BY9];
        promptMessage(1);

        return getRowInts(row1);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the second row of a sudoku board
    private int[] getRowTwo() {
        row2 = new int[BOARD_SIZE_9BY9];
        promptMessage(2);
        return getRowInts(row2);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the third row of a sudoku board
    private int[] getRowThree() {
        row3 = new int[BOARD_SIZE_9BY9];
        promptMessage(3);
        return getRowInts(row3);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the fourth row of a sudoku board
    private int[] getRowFour() {
        row4 = new int[BOARD_SIZE_9BY9];
        promptMessage(4);
        return getRowInts(row4);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the fifth row of a sudoku board
    private int[] getRowFive() {
        row5 = new int[BOARD_SIZE_9BY9];
        promptMessage(5);
        return getRowInts(row5);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the sixth row of a sudoku board
    private int[] getRowSix() {
        row6 = new int[BOARD_SIZE_9BY9];
        promptMessage(6);
        return getRowInts(row6);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the seventh row of a sudoku board
    private int[] getRowSeven() {
        row7 = new int[BOARD_SIZE_9BY9];
        promptMessage(7);
        return getRowInts(row7);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the eighth row of a sudoku board
    private int[] getRowEight() {
        row8 = new int[BOARD_SIZE_9BY9];
        promptMessage(8);
        return getRowInts(row8);

    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Gets user input for the ninth row of a sudoku board
    private int[] getRowNine() {
        row9 = new int[BOARD_SIZE_9BY9];
        promptMessage(9);
        return getRowInts(row9);

    }

    //REQUIRES: Number in [1, 9]
    //EFFECTS: Prints out prompt message depending on the sudoku row
    private void promptMessage(int num) {
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
    private int[] getRowInts(int[] row) {
        for (int i = 0; i < BOARD_SIZE_9BY9; i++) {
            row[i] = scanner.nextInt();
        }
        return row;
    }

}







