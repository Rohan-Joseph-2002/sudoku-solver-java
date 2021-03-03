package model;

import org.json.JSONObject;
import persistence.Writable;

public class SudokuAnswerBoard implements Writable {
    private String name;
    private int[][] solvedBoard;

    //EFFECTS: Creates a SudokuAnswerBoard
    public SudokuAnswerBoard(String name, int[][] board) {
        this.name = name;
        this.solvedBoard = board;
    }

    //EFFECTS: Returns a SudokuAnswerBoard as a int[][]
    public int[][] returnAnswerBoard() {
        return this.solvedBoard;
    }

    //EFFECTS: Returns name of SudokuAnswerBoard
    public String getName() {
        return this.name;
    }

    //EFFECTS: Returns this as JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("answer board", solvedBoard);
        return json;
    }
}
