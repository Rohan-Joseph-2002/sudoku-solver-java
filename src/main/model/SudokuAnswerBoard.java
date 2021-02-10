package model;

public class SudokuAnswerBoard {
    private int[][] solvedBoard;
    private boolean isSolvable;

    //EFFECTS: Creates a SudokuAnswerBoard
    public SudokuAnswerBoard(int[][] board) {
        this.solvedBoard = board;
    }

}
