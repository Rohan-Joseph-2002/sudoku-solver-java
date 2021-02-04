package model;

public class SudokuSolver9By9 implements SolveBoard {

    public static final int UNASSIGNED = 0;
    public static final int BOARD_SIZE = 9;  //This field ensures single point of control

    private int[][] sudokuBoard;

    //Constructor
    public SudokuSolver9By9(int[][] sudokuBoard) {
        this.sudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {                     //rows
            for (int column = 1; column <= BOARD_SIZE; column++) {        //columns
                this.sudokuBoard[row][column] = sudokuBoard[row][column]; //constructs matrix (i.e. sudoku board)
            }
        }
    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if a possible number is in the same row, else returns false
    @Override
    public boolean inSameRow(int[][] sudokuBoard, int row, int num) {
        return false;   //stub
    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if a possible number is in the same column, else returns false
    @Override
    public boolean inSameColumn(int[][] sudokuBoard, int column, int num) {
        return false;   //stub
    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if a possible number is in the same subgrid, else returns false
    @Override
    public boolean inSameSubGrid(int[][] sudokuBoard, int row, int column, int num) {
        return false;   //stub
    }

    //REQUIRES: Given number must be in between [1, 9]
    //EFFECTS: Returns true if inSameRow(), inSameColumn and inSameSubGrid all return false
    @Override
    public boolean canAddNum(int[][] sudokuBoard, int row, int column, int num) {
        return false;   //stub
    }

    //MODIFIES: this
    //EFFECTS: Returns true is a valid solution is found, else returns false.
    @Override
    public boolean solveBoard(int[][] sudokuBoard) {
        return false;   //stub
    }

}
