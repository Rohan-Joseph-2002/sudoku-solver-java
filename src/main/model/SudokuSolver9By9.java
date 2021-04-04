package model;

import exceptions.InvalidBoardException;
import exceptions.NumberBoundsException;

//Class - SudokuSolver9By9
public class SudokuSolver9By9 {

    public static final int UNASSIGNED = 0;  //Unassigned numbers are represented with a 0
    public static final int BOARD_SIZE = 9;  //Length of the sudoku board's rows and columns

    private int[][] sudokuBoard;

    //MODIFIES: this
    //EFFECTS: Creates a 9 by 9 Matrix
    public SudokuSolver9By9(int[][] board) {
        int[][] sudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];

        //NOTE: 0 indexing is used in the arrays in this implementation
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            System.arraycopy(board[rowIndex],
                    0, sudokuBoard[rowIndex],
                    0, BOARD_SIZE);
        }
    }

    //MODIFIES: this
    //EFFECTS: If !validSudokuBoard(board) throw new InvalidBoardException, else
    // Returns true if a valid solution to a question sudoku board is found, else returns false.
    public boolean solveBoard(int[][] board) throws InvalidBoardException, NumberBoundsException {
        if (validSudokuBoard(board)) {
            for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
                for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                    int boardNum = board[rowIndex][columnIndex];
                    if (boardNum == UNASSIGNED) {
                        //NOTE: Tries every possible number to check if it can be assigned here
                        return getAssignment(board, rowIndex, columnIndex);
                    }
                }
            }
            this.sudokuBoard = board;
            return true;
        } else {
            this.sudokuBoard = board;
            throw new InvalidBoardException();
        }
    }

    //EFFECTS: Returns true if a given sudoku board is valid. Else returns false.
    public boolean validSudokuBoard(int[][] board) {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                int boardNum = board[rowIndex][columnIndex];
                //boardNum < 0, because 0 is used to represent an empty space
                if (boardNum < 0 || boardNum > 9) {
                    return false;
                }
            }
        }
        return true;
    }

    //MODIFIES: this
    //EFFECTS: Checks to see if the number can be assigned, assigns if true, or does nothing.
    private boolean getAssignment(int[][] board, int r, int c) throws NumberBoundsException, InvalidBoardException {
        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (canAddNum(board, r, c, num)) {
                //NOTE: If number can be assigned, assign number to position
                board[r][c] = num;
                //NOTE: Recursive Backtracking
                if (solveBoard(board)) {
                    return true;
                    //NOTE: If there is no solution, keep the position UNASSIGNED and move on.
                } else {
                    board[r][c] = UNASSIGNED;
                }
            }
        }
        return false;
    }

    //EFFECTS: Gets solved sudoku board
    public int[][] getBoard() {
        return this.sudokuBoard;
    }

    //EFFECTS: Returns true if inSameRow(), inSameColumn and inSameSubGrid all return false
    public boolean canAddNum(int[][] board, int rowIndex, int columnIndex, int num) throws NumberBoundsException {
        int subRow = rowIndex - rowIndex % 3;          //NOTE: constrains iterating rows within a 3 by 3 subgrid
        int subColumn = columnIndex - columnIndex % 3; //NOTE: constrains iterating columns within a 3 by 3 subgrid

        return !inSameRow(board, rowIndex, num)
                && !inSameColumn(board, columnIndex, num)
                && !inSameSubGrid(board, subRow, subColumn, num);
    }

    //EFFECTS: If num < 1 or num > 9, throw new NumberBoundsException, else
    // Returns true if a possible number is in the same row, else returns false
    public boolean inSameRow(int[][] board, int rowIndex, int num) throws NumberBoundsException {
        if (num < 1 || num > 9) {
            throw new NumberBoundsException();
        } else {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                if (board[rowIndex][columnIndex] == num) {
                    return true;
                }
            }
            return false;
        }
    }

    //EFFECTS: If num < 1 or num > 9, throw new NumberBoundsException, else
    // Returns true if a possible number is in the same column, else returns false
    public boolean inSameColumn(int[][] board, int columnIndex, int num) throws NumberBoundsException {
        if (num < 1 || num > 9) {
            throw new NumberBoundsException();
        } else {
            for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
                if (board[rowIndex][columnIndex] == num) {
                    return true;
                }
            }
            return false;
        }
    }

    //EFFECTS: If num < 1 or num > 9, throw new NumberBoundsException, else
    // Returns true if a possible number is in the same subgrid, else returns false
    public boolean inSameSubGrid(int[][] board, int rowIndex, int columnIndex, int num) throws NumberBoundsException {
        if (num < 1 || num > 9) {
            throw new NumberBoundsException();
        } else {
            for (int subRowIndex = 0; subRowIndex < 3; subRowIndex++) {
                for (int subColumnIndex = 0; subColumnIndex < 3; subColumnIndex++) {
                    if (board[subRowIndex + rowIndex][subColumnIndex + columnIndex] == num) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

}