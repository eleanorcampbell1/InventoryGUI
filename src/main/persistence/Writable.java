package persistence;

import org.json.JSONObject;

// Code below attributed to the Writable interface in CPSC210/JsonSerializationDemo

public interface Writable {

    /*
    * EFFECTS: returns this as JSON object
     */
    JSONObject toJson();
}
