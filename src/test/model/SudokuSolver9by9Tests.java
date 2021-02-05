package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuSolver9by9Tests {

    private SudokuSolver9By9 testBoard1;

    private final int[][] board1 = {
            {0,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };
    private int testRowBoard1;
    private int testColumnBoard1;
    private int testSubGridRowBoard1;
    private int testSubGridColumnBoard1;

    private SudokuSolver9By9 testBoard2;
    private final int[][] board2 = {
            {5,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,0,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,0,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,0,0,4,8,5,6},
            {9,6,1,5,3,0,2,8,4},
            {2,0,7,4,1,0,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };
    private int testRowBoard2;
    private int testColumnBoard2;
    private int testSubGridRowBoard2;
    private int testSubGridColumnBoard2;

    private SudokuSolver9By9 testBoardNotSolvable;
    private final int[][] board3 = {
            {2,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
    };

    private SudokuSolver9By9 testBoardSolvable;
    private final int[][] board4 = {
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
    };

    private SudokuSolver9By9 testSolutionBoard;
    private int[][] solution = {
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
        testBoard1 = new SudokuSolver9By9(board1);
        testRowBoard1 = 0;
        testColumnBoard1 = 0;
        testSubGridRowBoard1 = 0;
        testSubGridColumnBoard1 = 0;

        testBoard2 = new SudokuSolver9By9(board2);
        testRowBoard2 = 7;
        testColumnBoard2 = 5;
        testSubGridRowBoard2 = 5;
        testSubGridColumnBoard2 = 6;

        testBoardNotSolvable = new SudokuSolver9By9(board3);

        testBoardSolvable = new SudokuSolver9By9(board4);

        testSolutionBoard = new SudokuSolver9By9(solution);

    }

    @Test
    public void testNotInSameRow() {
        assertFalse(testBoard1.inSameRow(board1, testRowBoard1,5));
        assertFalse(testBoard2.inSameRow(board2, testRowBoard2, 9));

    }

    @Test
    public void testInSameRow() {
        assertTrue(testBoard1.inSameRow(board1, testRowBoard1, 2));
        assertTrue(testBoard2.inSameRow(board2, testRowBoard2, 7));

    }

    @Test
    public void testNotInSameColumn() {
        assertFalse(testBoard1.inSameColumn(board1, testColumnBoard1, 5));
        assertFalse(testBoard2.inSameColumn(board2, testColumnBoard2, 9));

    }

    @Test
    public void testInSameColumn() {
        assertTrue(testBoard1.inSameColumn(board1, testColumnBoard1, 3));
        assertTrue(testBoard2.inSameColumn(board2, testColumnBoard2, 8));

    }

    @Test
    public void testNotInSameSubGrid() {
        assertFalse(testBoard1.inSameSubGrid(board1, testSubGridRowBoard1, testSubGridColumnBoard1, 5));
        assertFalse(testBoard2.inSameSubGrid(board2, testSubGridRowBoard2, testSubGridColumnBoard2, 9));

    }

    @Test
    public void testInSameSubGrid() {
        assertTrue(testBoard1.inSameSubGrid(board1, testSubGridRowBoard1, testSubGridColumnBoard1, 7));
        assertTrue(testBoard2.inSameSubGrid(board2, testSubGridRowBoard2, testSubGridColumnBoard2, 6));

    }

    @Test
    public void testCanNotAddNum() {
        assertFalse(testBoard1.canAddNum(board1, 0, 0, 7));
        assertFalse(testBoard2.canAddNum(board2, 7, 1, 6));

    }

    @Test
    public void testCanAddNum() {
        assertTrue(testBoard1.canAddNum(board1, 0, 0, 5));
        assertTrue(testBoard2.canAddNum(board2, 7, 1, 8));

    }

    @Test
    public void testSolveBoardNotSolvable() {
        assertFalse(testBoardNotSolvable.solveBoard(board3));

    }

    @Test
    public void testSolveBoardSolvable() {
        assertTrue(testBoardSolvable.solveBoard(board4));

    }

}