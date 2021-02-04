package model;

public interface SolveBoard {

    //REQUIRES: Given number must be in between [1, n]
    //EFFECTS: Returns true if a possible number is in the same row, else returns false
    boolean inSameRow(int[][] sudokuBoard, int rowIndex, int num);

    //REQUIRES: Given number must be in between [1, n]
    //EFFECTS: Returns true if a possible number is in the same column, else returns false
    boolean inSameColumn(int[][] sudokuBoard, int columnIndex, int num);

    //REQUIRES: Given number must be in between [1, n]
    //EFFECTS: Returns true if a possible number is in the same subgrid, else returns false
    boolean inSameSubGrid(int[][] sudokuBoard, int rowIndex, int columnIndex, int num);

    //REQUIRES: Given number must be in between [1, n]
    //EFFECTS: Returns true if inSameRow(), inSameColumn and inSameSubGrid all return false
    boolean canAddNum(int[][] sudokuBoard, int rowIndex, int columnIndex, int num);

    //REQUIRES: Board
    //MODIFIES: this
    //EFFECTS: Returns true is a valid solution is found, else returns false.
    boolean solveBoard(int[][] sudokuBoard);

}
