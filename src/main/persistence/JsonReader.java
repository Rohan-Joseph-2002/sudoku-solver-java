package persistence;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;
    private SudokuAnswerBoards boards;

    //EFFECTS: Constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //EFFECTS: Reads SudokuAnswerBoards from file and returns it
    //         Throws IOException if an error occurs while reading
    public SudokuAnswerBoards read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSudokuAnswerBoards(jsonObject);
    }

    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //EFFECTS: Reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }
        return builder.toString();
    }

    //EFFECTS: Parses the SudokuAnswerBoards from JSON object and returns it
    private SudokuAnswerBoards parseSudokuAnswerBoards(JSONObject jsonObject) {
        String name =  jsonObject.getString("name");
        boards = new SudokuAnswerBoards(name);
        addSudokuAnswerBoards(boards, jsonObject);
        return boards;
    }

    //MODIFIES: boards
    //EFFECTS: Parses through multiple SudokuAnswerBoard from JSON object and
    //         adds them to SudokuAnswerBoards
    private void addSudokuAnswerBoards(SudokuAnswerBoards boards, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("answer boards");
        for (Object json : jsonArray) {
            JSONObject nextAnswerBoard = (JSONObject) json;
            addSudokuAnswerBoard(boards, nextAnswerBoard);
        }
    }

    //MODIFIES: boards
    //EFFECTS: Parses SudokuAnswerBoard from JSON object and adds it to
    //         SudokuAnswerBoards
    private void addSudokuAnswerBoard(SudokuAnswerBoards boards, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray jsonArray = jsonObject.getJSONArray("answer board");
        int length = jsonArray.length();
        int[][] answerBoard = new int[length][length];
        for (int i = 0; i < length; i++) {
            JSONArray row = jsonArray.getJSONArray(i);
            for (int j = 0; j < length; j++) {
                answerBoard[i][j] = row.getInt(j);
            }
        }
        SudokuAnswerBoard board = new SudokuAnswerBoard(name, answerBoard);
        boards.add(board);
    }

}
