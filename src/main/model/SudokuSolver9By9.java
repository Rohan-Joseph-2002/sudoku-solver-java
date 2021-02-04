package model;

public class SudokuSolver9By9 implements SolveBoard {

    public static final int UNASSIGNED = 0;  //Unassigned numbers are represented with 0s
    public static final int BOARD_SIZE = 9;  //Length of the sudoku board's rows and columns

    private int[][] sudokuBoard;

    //Constructor
    public SudokuSolver9By9(int[][] sudokuBoard) {
        this.sudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];

        //NOTE: 0 indexing is used in the arrays in this implementation
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {                              //rows
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {                 //columns
                this.sudokuBoard[rowIndex][columnIndex] = sudokuBoard[rowIndex][columnIndex];
            }
        }

    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if a possible number is in the same row, else returns false
    @Override
    public boolean inSameRow(int[][] sudokuBoard, int rowIndex, int num) {
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            if (sudokuBoard[rowIndex][columnIndex] == num) {
                return true;
            }
        }
        return false;

    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if a possible number is in the same column, else returns false
    @Override
    public boolean inSameColumn(int[][] sudokuBoard, int columnIndex, int num) {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            if (sudokuBoard[rowIndex][columnIndex] == num) {
                return true;
            }
        }
        return false;

    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if a possible number is in the same subgrid, else returns false
    @Override
    public boolean inSameSubGrid(int[][] sudokuBoard, int rowIndex, int columnIndex, int num) {
        for (int subRowIndex = 0; subRowIndex < 3; subRowIndex++) {
            for (int subColumnIndex = 0; subColumnIndex < 3; subColumnIndex++) {
                if (sudokuBoard[subRowIndex + rowIndex][subColumnIndex + columnIndex] == num) {
                    return true;
                }
            }
        }
        return false;

    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if inSameRow(), inSameColumn and inSameSubGrid all return false
    @Override
    public boolean canAddNum(int[][] sudokuBoard, int rowIndex, int columnIndex, int num) {
        int[][] board = sudokuBoard;
        int r = rowIndex;
        int subR = r - r % 3;   //NOTE: constrains iterating rows within a 3 by 3 subgrid
        int c = columnIndex;
        int subC = c - c % 3;   //NOTE: constrains iterating columns within a 3 by 3 subgrid
        int n = num;

        return !inSameRow(board, r, n) && !inSameColumn(board, c, n) && !inSameSubGrid(board, subR, subC, n);

    }

    //MODIFIES: this
    //EFFECTS: Returns true if a valid solution to a question sudoku board is found, else returns false.
    @Override
    public boolean solveBoard(int[][] sudokuBoard) {
        //NOTE: Iterating through rows
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            //NOTE: Iterating through columns
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                //NOTE: Check to find an UNASSIGNED number position
                if (sudokuBoard[rowIndex][columnIndex] == UNASSIGNED) {
                    //NOTE: Try every possible number to check if it can be assigned here
                    for (int num = 1; num <= BOARD_SIZE; num++) {
                        if (canAddNum(sudokuBoard, rowIndex, columnIndex, num)) {
                            //NOTE: If number can be assigned, assign number to position
                            sudokuBoard[rowIndex][columnIndex] = num;
                            //NOTE: Recursive Backtracking
                            if (solveBoard(sudokuBoard)) {
                                return true;
                                //NOTE: If there is no solution, keep the position UNASSIGNED and move on.
                            } else {
                                sudokuBoard[rowIndex][columnIndex] = UNASSIGNED;
                            }
                        }
                    }
                    return false; //NOTE: Return false is number is assigned to a position
                }
            }
        }
        return true;   //NOTE: Sudoku is solved

    }

}
