package persistence;


import model.Inventory;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Code below attributed to the JsonWriter class in CPSC210/JsonSerializationDemo, with elements
// changed to fit my application

// Represents a writer that writes JSON representation of inventory to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    /*
    * EFFECTS: constructs writer to write to destination file
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
    * MODIFIES: this
    * EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
     */
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
    * MODIFIES: this
    * EFFECTS: writes JSON representation of inventory to file
     */
    public void write(Inventory inventory) {
        JSONObject json = inventory.toJson();
        saveToFile(json.toString(TAB));
    }

    /*
    * MODIFIES: this
    * EFFECTS: closes writer
     */
    public void close() {
        writer.close();
    }

    /*
    * MODIFIES: this
    * EFFECTS: writes string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }

}
