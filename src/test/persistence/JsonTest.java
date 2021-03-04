package persistence;

import model.SudokuAnswerBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    private static final int BOARD_SIZE = 9;

    protected void checkSudokuAnswerBoard(String name, int[][] board, SudokuAnswerBoard sudokuAnswerBoard) {
        assertEquals(name, sudokuAnswerBoard.getName());
        int[][] answerBoard = sudokuAnswerBoard.returnAnswerBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                assertEquals(board[row][column], answerBoard[row][column]);
            }
        }
    }
}
