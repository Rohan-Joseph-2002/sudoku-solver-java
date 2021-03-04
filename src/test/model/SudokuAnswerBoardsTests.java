package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuAnswerBoardsTests {

    private SudokuAnswerBoard answerBoard1;
    private SudokuAnswerBoard answerBoard2;
    private SudokuAnswerBoards listOfAnswerBoards;

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

    private final int[][] board2 = {
            {8,2,7,1,5,4,3,9,6},
            {9,6,5,3,2,7,1,4,8},
            {3,4,1,6,8,9,7,5,2},
            {5,9,3,4,6,8,2,7,1},
            {4,7,2,5,1,3,6,8,9},
            {6,1,8,9,7,2,4,3,5},
            {7,8,6,2,3,5,9,1,4},
            {1,5,4,7,9,6,8,2,3},
            {2,3,9,8,4,1,5,6,7}
    };

    private List<SudokuAnswerBoard> sudokuAnswerBoards;

    @BeforeEach
    public void setup() {
        answerBoard1 = new SudokuAnswerBoard("b1", board1);
        answerBoard2 = new SudokuAnswerBoard("b2", board2);
        sudokuAnswerBoards = new ArrayList<>();
        listOfAnswerBoards = new SudokuAnswerBoards("test");
    }

    @Test
    public void testGetName() {
        assertEquals("test", listOfAnswerBoards.getName());
    }

    @Test
    public void testAddEmptyList() {
        assertEquals(0, listOfAnswerBoards.getLength());
    }

    @Test
    public void testAddOneBoard() {
        listOfAnswerBoards.add(answerBoard1);
        assertEquals(1, listOfAnswerBoards.getLength());
    }

    @Test
    public void testAddMultipleBoards() {
        listOfAnswerBoards.add(answerBoard1);
        listOfAnswerBoards.add(answerBoard1);
        assertEquals(2, listOfAnswerBoards.getLength());
    }

    @Test
    public void testGetListOfAnswerBoardsEmptyLists() {
        assertEquals(sudokuAnswerBoards, listOfAnswerBoards.getListOfAnswerBoards());
    }

    @Test
    public void testGetListOfAnswerBoardsMultipleBoards() {
        sudokuAnswerBoards.add(answerBoard1);
        sudokuAnswerBoards.add(answerBoard2);
        listOfAnswerBoards.add(answerBoard1);
        listOfAnswerBoards.add(answerBoard2);
        assertEquals(sudokuAnswerBoards, listOfAnswerBoards.getListOfAnswerBoards());
    }

}
