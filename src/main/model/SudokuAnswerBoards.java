package model;

import java.util.ArrayList;
import java.util.List;

public class SudokuAnswerBoards {
    private final List<SudokuAnswerBoard> sudokuAnswerBoards;

    //EFFECTS: Creates a new array list of SudokuAnswerBoard(s)
    public SudokuAnswerBoards() {
        this.sudokuAnswerBoards = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Adds a SudokuAnswerBoard to SudokuAnswerBoards
    public void add(SudokuAnswerBoard answerBoard) {
        sudokuAnswerBoards.add(answerBoard);
    }

    //EFFECTS: Returns the length of the SudokuAnswerBoards array list
    public int getLength() {
        return sudokuAnswerBoards.size();
    }

}
