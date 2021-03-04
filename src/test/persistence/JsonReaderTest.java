package persistence;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
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

    @BeforeEach
    public void setup() {
        listOfAnswerBoards = new SudokuAnswerBoards("Sudoku Answer Boards - Reader Test");
    }

    @Test
    public void testReaderNonExistentFile() {
        JsonReader jsonReader = new JsonReader("./data/noFile.json");
        try {
            listOfAnswerBoards = jsonReader.read();
            fail("IOException is expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testReaderEmptySudokuAnswerBoards() {
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptySudokuAnswerBoards.json");
        try {
            listOfAnswerBoards = jsonReader.read();
            assertEquals("Sudoku Answer Boards - Reader Test", listOfAnswerBoards.getName());
            assertEquals(0, listOfAnswerBoards.getLength());
        } catch (IOException e) {
            fail("Unable to read file");
        }
    }

    @Test
    public void testReaderGeneralSudokuAnswerBoards() {
        JsonReader jsonReader = new JsonReader("./data/testReaderGeneralSudokuAnswerBoards.json");
        try {
            listOfAnswerBoards = jsonReader.read();
            assertEquals("Sudoku Answer Boards - Reader Test", listOfAnswerBoards.getName());
            List<SudokuAnswerBoard> answerBoards = listOfAnswerBoards.getListOfAnswerBoards();
            assertEquals(4,answerBoards.size());
            List<int[][]> boardList = returnAnswerBoards(answerBoards);
            checkSudokuAnswerBoard("test1", boardList.get(0), answerBoards.get(0));
            checkSudokuAnswerBoard("test2", boardList.get(1), answerBoards.get(1));
            checkSudokuAnswerBoard("test3", boardList.get(2), answerBoards.get(2));
            checkSudokuAnswerBoard("test4", boardList.get(3), answerBoards.get(3));
        } catch (IOException e) {
            fail("Unable to read file");
        }
    }

    private List<int[][]> returnAnswerBoards(List<SudokuAnswerBoard> answerBoards) {
        List <int[][]> boardList = new ArrayList<>();
        for (SudokuAnswerBoard board : answerBoards) {
            boardList.add(board.returnAnswerBoard());
        }
        return boardList;
    }

}
