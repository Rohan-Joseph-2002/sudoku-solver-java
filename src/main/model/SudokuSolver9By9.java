package model;

public class SudokuSolver9By9 implements SolveBoard {

    public static final int UNASSIGNED = 0;  //Unassigned numbers are represented with a 0
    public static final int BOARD_SIZE = 9;  //Length of the sudoku board's rows and columns

    private final int[][] sudokuBoard;
    private int[][] solvedBoard;

    //REQUIRES: A valid Sudoku Board AND BOARD_SIZE
    //MODIFIES: this
    //EFFECTS: Creates a 9 by 9 Matrix
    public SudokuSolver9By9(int[][] sudokuBoard) {
        this.sudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];

        //NOTE: 0 indexing is used in the arrays in this implementation
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            System.arraycopy(sudokuBoard[rowIndex],
                    0, this.sudokuBoard[rowIndex],
                    0, BOARD_SIZE);
        }
    }

    //MODIFIES: this
    //EFFECTS: Returns true if a valid solution to a question sudoku board is found, else returns false.
    @Override
    public boolean solveBoard(int[][] sudokuBoard) {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                //NOTE: Checks to find an UNASSIGNED number position
                if (sudokuBoard[rowIndex][columnIndex] == UNASSIGNED) {
                    //NOTE: Tries every possible number to check if it can be assigned here
                    return getAssignment(sudokuBoard, rowIndex, columnIndex);
                }
            }
        }
        this.solvedBoard = sudokuBoard;
        return true;   //NOTE: Sudoku is solved
    }

    //REQUIRES: rowIndex and columnIndex must be one of [0, 8]
    //MODIFIES: this
    //EFFECTS: Checks to see if the number can be assigned, assigns if true, or does nothing.
    @Override
    public boolean getAssignment(int[][] sudokuBoard, int rowIndex, int columnIndex) {
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
        return false;
    }

    //EFFECTS: Gets solved sudoku board
    @Override
    public int[][] getSolvedBoard() {
        return this.solvedBoard;
    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if inSameRow(), inSameColumn and inSameSubGrid all return false
    @Override
    public boolean canAddNum(int[][] sudokuBoard, int rowIndex, int columnIndex, int num) {
        int subRow = rowIndex - rowIndex % 3;          //NOTE: constrains iterating rows within a 3 by 3 subgrid
        int subColumn = columnIndex - columnIndex % 3; //NOTE: constrains iterating columns within a 3 by 3 subgrid

        return !inSameRow(sudokuBoard, rowIndex, num)
                && !inSameColumn(sudokuBoard, columnIndex, num)
                && !inSameSubGrid(sudokuBoard, subRow, subColumn, num);
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

}
