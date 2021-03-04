package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuAnswerBoardTests {

    private static final int BOARD_SIZE = 9;

    private SudokuAnswerBoard answerBoard1;

    private final int[][] board1 = {
            {5,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };

    @BeforeEach
    public void setup() {
        answerBoard1 = new SudokuAnswerBoard("test1", board1);
    }

    @Test
    public void testReturnSolvedBoard() {
        int[][] answerBoard = answerBoard1.returnAnswerBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                assertEquals(board1[row][column], answerBoard[row][column]);
            }
        }
    }

    @Test
    public void testGetName() {
        assertEquals("test1", answerBoard1.getName());
    }

}
