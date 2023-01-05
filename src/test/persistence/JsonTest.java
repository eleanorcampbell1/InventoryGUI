package persistence;

import model.Item;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(Item item, Float price, String name, List<String> attributes) {
        assertEquals(price, item.getPrice());
        assertEquals(name, item.getName());
        assertEquals(attributes, item.getAttributes());
    }
}
