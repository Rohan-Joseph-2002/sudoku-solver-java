package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Class - SudokuAnswerBoards
public class SudokuAnswerBoards implements Writable {
    private String name;
    private List<SudokuAnswerBoard> sudokuAnswerBoards;

    //EFFECTS: Creates a new array list of SudokuAnswerBoard(s)
    public SudokuAnswerBoards(String name) {
        this.name = name;
        this.sudokuAnswerBoards = new ArrayList<>();
    }

    //EFFECTS: Returns name
    public String getName() {
        return this.name;
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

    //EFFECTS: Returns List of SudokuAnswerBoard
    public List<SudokuAnswerBoard> getListOfAnswerBoards() {
        return sudokuAnswerBoards;
    }

    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //EFFECTS: Returns this as a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("answer boards", answerBoardToJson());
        return json;
    }

    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //EFFECTS:
    private JSONArray answerBoardToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SudokuAnswerBoard board : sudokuAnswerBoards) {
            jsonArray.put(board.toJson());
        }

        return jsonArray;
    }
}
