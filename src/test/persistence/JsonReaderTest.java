package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Inventory inventory = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testEmptyReaderInventory() {
        JsonReader reader = new JsonReader("./data/emptyReaderInventory.json");
        try {
            Inventory inventory = reader.read();
            assertEquals("Default Inventory", inventory.getName());
            assertEquals(0, inventory.numItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testRegularReaderInventory() {
        JsonReader reader = new JsonReader("./data/regularReaderInventory.json");
        try {
            Inventory inventory = reader.read();
            assertEquals("Default Inventory", inventory.getName());
            List<Item> items = inventory.getItems();
            assertEquals(2, items.size());

            List<String> item1Attributes = new ArrayList<>();
            item1Attributes.add("Clothing");
            item1Attributes.add("Women's");
            item1Attributes.add("Blouse");

            List<String> item2Attributes = new ArrayList<>();
            item2Attributes.add("Clothing");
            item2Attributes.add("Women's");
            item2Attributes.add("Jeans");

            checkItem(items.get(0), 25F, "Pembrooke Blouse", item1Attributes);
            checkItem(items.get(1), 50F, "Levi Jeans", item2Attributes);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
