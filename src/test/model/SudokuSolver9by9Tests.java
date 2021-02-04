package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuSolver9by9Tests {

    private static final int BOARD_SIZE = 9;

    @BeforeEach
    public void setup() {
        int[][] testBoard = {
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
        int[] testRow = testBoard[0];
        int[] testColumn = getColumn(testBoard, 0);

        int[][] testBoard2 = {
                {5,3,4,6,7,8,9,1,2},
                {6,7,2,1,9,5,3,4,8},
                {1,9,8,3,4,2,5,6,7},
                {8,5,9,7,0,1,4,2,3},
                {4,2,6,8,5,3,7,9,1},
                {7,1,3,9,0,4,8,5,6},
                {9,6,1,5,3,7,2,8,4},
                {2,0,7,4,1,0,6,3,5},
                {3,4,5,2,8,6,1,7,9}
        };
        int[] testRow2 = testBoard2[7];
        int[] testColumn2 = getColumn(testBoard2, 7);
        int[] testSubGridRow = testBoard2[2];
        int[] testSubGridColumn = getColumn(testBoard2, 4);

        int[][] questionTestBoardNotSolvable = {
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

        int[][] questionTestBoardSolvable = {
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

        int[][] solutionBoard = {
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

    }

    @Test
    public void testConstructor() {
        //stub
    }

    @Test
    public void testInSameRow() {
        //stub
    }

    @Test
    public void testNotInSameRow() {
        //stub
    }

    @Test
    public void testNotInSameColumn() {
        //stub
    }

    @Test
    public void testInSameColumn() {
        //stub
    }

    @Test
    public void testNotInSameSubGrid() {
        //stub
    }

    @Test
    public void testInSameSubGrid() {
        //stub
    }

    @Test
    public void testSolveBoardNotSolvable() {
        //stub
    }

    @Test
    public void testSolveBoardSolvable() {
        //stub
    }

    public int[] getColumn(int[][] sudokuBoard, int columnIndexPosition) {
        int[] column = new int[sudokuBoard[0].length];

        for (int row = 0; row < BOARD_SIZE; row++) {
            column[row] = sudokuBoard[row][columnIndexPosition];
        }
        return column;
    }

}