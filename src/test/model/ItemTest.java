package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    String existingAttribute1 = "Pink";
    String nonExistingAttribute = "Green";
    Item testItem = new Item(25, "Really Cute Shirt", "Women's", "Pink",
            "Cotton");
    List<String> testItemAttributes = new ArrayList<>();


    @Test
    void hasAttributesTest() {
        assertTrue(testItem.hasAttribute(existingAttribute1));
    }

    @Test
    void hasNoAttributesTest() {
        assertFalse(testItem.hasAttribute(nonExistingAttribute));
    }

    @Test
    void toStringTest() {
        assertEquals("Really Cute Shirt - $25.00 [Women's, Pink, Cotton]", testItem.toString());
    }

    @Test
    void differentItemObjectDoesEqualTest() {
        Item equalItem = new Item(30, "Really Cute Shirt", "Women's", "Pink",
                "Cotton");
        assertTrue(equalItem.equals(testItem));
    }

    @Test
    void sameClassItemNotEqualTest() {
        Item notEqualItem = new Item(25, "Really Ugly Shirt", "Women's", "Pink",
                "Cotton");
        assertFalse(notEqualItem.equals(testItem));
    }

    @Test
    void sameItemObjectDoesEqualTest() {
        assertTrue(testItem.equals(testItem));
    }

    @Test
    void sameClassItemDoesEqualTest() {
        Item equalItem = new Item(30, "really cute shirt", "Women's", "Pink",
                "Cotton");
        assertTrue(equalItem.equals(testItem));
    }

    @Test
    void differentClassObjectTest() {
        Inventory inventory = new Inventory("test Inventory");
        assertFalse(testItem.equals(inventory));
    }

}
