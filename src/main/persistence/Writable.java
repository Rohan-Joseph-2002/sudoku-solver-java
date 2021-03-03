package persistence;

import org.json.JSONObject;

public interface Writable {
    //SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //EFFECTS: Returns this as a JSON Object
    JSONObject toJson();
}
