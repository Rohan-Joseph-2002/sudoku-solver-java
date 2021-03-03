package persistence;

import model.SudokuAnswerBoards;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: Constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: Opens writer
    //         If destination file cannot be found, throws
    //         FileNotFoundException
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //MODIFIES: this
    //EFFECTS: Writes JSON representation of SudokuAnswerBoards to file
    public void write(SudokuAnswerBoards boards) {
        JSONObject json = boards.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: Closes writer
    public void closeWriter() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: Writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
