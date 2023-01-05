package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Inventory inventory = new Inventory("Default Inventory");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testEmptyWriterInventory() {
        try {
            Inventory inventory = new Inventory("Default Inventory");
            JsonWriter writer = new JsonWriter("./data/emptyWriterInventory.json");
            writer.open();
            writer.write(inventory);
            writer.close();

            JsonReader reader = new JsonReader("./data/emptyWriterInventory.json");
            inventory = reader.read();
            assertEquals("Default Inventory", inventory.getName());
            assertEquals(0, inventory.numItems());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testRegularWriterInventory() {
        try {
            Inventory inventory = new Inventory("Default Inventory");
            inventory.addItemToInventory(new Item(25, "Pembrooke Blouse",
                    "Clothing", "Women's", "Blouse"));
            inventory.addItemToInventory(new Item(50, "Levi Jeans",
                    "Clothing", "Women's", "Jeans"));
            JsonWriter writer = new JsonWriter("./data/regularWriterInventory.json");
            writer.open();
            writer.write(inventory);
            writer.close();

            JsonReader reader = new JsonReader("./data/regularWriterInventory.json");
            inventory = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
