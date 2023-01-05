package persistence;

import model.Inventory;
import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Code below attributed to the JsonReader class in CPSC210/JsonSerializationDemo, with elements
// changed to fit my application

// Represents a reader that reads inventory from JSON data stored in file
public class JsonReader {
    private String source;

    /*
    * EFFECTS: constructs reader to read from source file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /*
    * EFFECTS: reads inventory from file and returns it;
    * throws IOException if an error occurs reading data from file
     */
    public Inventory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    /*
    * EFFECTS: reads source file as string and returns it
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    /*
    * EFFECTS: parses inventory from JSON object and returns it
     */
    private Inventory parseInventory(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Inventory inventory = new Inventory(name);
        addItems(inventory, jsonObject);
        return inventory;
    }

    /*
    * MODIFIES: inventory
    * EFFECTS: parses items from JSON object and adds them to inventory
     */
    private void addItems(Inventory inventory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inventory, nextItem);
        }
    }

    /*
    * MODIFIES: inventory
    * EFFECTS: parses item from JSON object and adds it to inventory
     */
    private void addItem(Inventory inventory, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        float price = jsonObject.getFloat("price");
        JSONArray attributeJsonArray = jsonObject.getJSONArray("attributes");
        String[] attributes = new String[attributeJsonArray.length()];
        int i = 0;
        for (Object attribute : attributeJsonArray) {
            attributes[i] = (String) attribute;
            i++;
        }
        Item item = new Item(price, name, attributes);
        inventory.addItemToInventory(item);
    }
}
