package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {


    Inventory testInventory = new Inventory("testInventory");
    Item fancyCouchItem = new Item(50, "Fancy Couch",
            "Comfy", "Upholstered", "Maroon");
    Item womenBlouse = new Item(25, "Pembrooke Blouse", "Clothing",
            "Women's", "Blouse");
    Item womenJeans = new Item(50, "Levi Jeans", "Clothing",
            "Women's", "Jeans");
    Item menDressShirt = new Item(40, "Checkered Shirt", "Clothing",
            "Men's", "Shirt");
    Item queenSizeBed = new Item(200, "Fit For a Queen Bed", "Home Goods",
            "Bed", "Queen");

    @BeforeEach
    void runBefore() {
        testInventory.addItemToInventory(womenBlouse);
        testInventory.addItemToInventory(womenJeans);
        testInventory.addItemToInventory(menDressShirt);
        testInventory.addItemToInventory(queenSizeBed);
    }


    @Test
    // the item removed is actually in the Inventory
    void removeExistingInventoryItemTest() {
        assertNotNull(testInventory.viewItem("Fit For a Queen Bed"));
        testInventory.removeInventoryItem("Fit For a Queen Bed");
        assertNull(testInventory.viewItem("Fit For a Queen Bed"));
    }

    @Test
    // the item is not in the inventory
    void removeNonExistingInventoryItemTest() {
        assertNull(testInventory.viewItem("Fancy Couch"));
        testInventory.removeInventoryItem("Fancy Couch");
        assertNull(testInventory.viewItem("Fancy Couch"));
    }

    @Test
    void addDifferentItemToInventoryTest(){
        assertNull(testInventory.viewItem("Fancy Couch"));
        testInventory.addItemToInventory(fancyCouchItem);
        assertNotNull(testInventory.viewItem("Fancy Couch"));
    }

    @Test
    // item already has identical item in inventory
    void addSameItemToInventoryTest(){
        int originalSize = testInventory.size();

        assertNotNull(testInventory.viewItem("Levi Jeans")); //is this needed?
        testInventory.addItemToInventory(womenJeans);

        int finalSize = testInventory.size();
        assertEquals(originalSize, finalSize);
    }

    @Test
    //both attributes given exist
    void searchInventoryByTwoExistingAttributesTest(){
        List<Item> womenBlouseList = new ArrayList<>();
        womenBlouseList.add(womenBlouse);
        List<String> attributes = new ArrayList<>();
        attributes.add("Women's");
        attributes.add("Blouse");
        assertEquals(womenBlouseList,
                testInventory.searchInventoryByAttributes(attributes));
    }

    @Test
    // only one attribute given exists
    void searchInventoryByOneExistingAttributesTest(){
        List<Item> itemsOfGivenAttribute = new ArrayList<>();
        itemsOfGivenAttribute.add(womenBlouse);
        itemsOfGivenAttribute.add(womenJeans);
        List<String> attributes = new ArrayList<>();
        attributes.add("Women's");
        assertEquals(itemsOfGivenAttribute, testInventory.searchInventoryByAttributes(attributes));
    }

    @Test
    // no attributes given exist
    void searchInventoryByNoExistingAttributesTest(){
        List<Item> emptyList = new ArrayList<>();
        List<String> attributes = new ArrayList<>();
        attributes.add("Long");
        assertEquals(emptyList, testInventory.searchInventoryByAttributes(attributes));
    }

    @Test
    //item of that name exists
    void viewExistingItemTest(){
        testInventory.addItemToInventory(fancyCouchItem);
        assertEquals(fancyCouchItem, testInventory.viewItem("Fancy Couch"));
    }

    @Test
    //item of that name doesn't exist
    void viewNonExistingItemTest(){
        assertNull(testInventory.viewItem("Fancy Couch"));
    }

    @Test
    void numItemsTest() {
        assertEquals(4, testInventory.numItems());
    }

    @Test
    void getItemNamesFromInventoryTest() {
        List<String> itemNames = new ArrayList();
        itemNames.add("Pembrooke Blouse");
        itemNames.add("Levi Jeans");
        itemNames.add("Checkered Shirt");
        itemNames.add("Fit For a Queen Bed");
        String[] itemNamesArray = itemNames.toArray(new String[itemNames.size()]);

        assertTrue(Arrays.equals(itemNamesArray, testInventory.getItemNamesFromInventory()));
    }

    @Test
    void setInventoryItemsTest() {
        Inventory modifiedInventory = new Inventory("Modified Inventory");
        modifiedInventory.addItemToInventory(
                new Item(10, "Earrings", "Gold", "Sparkly", "Fancy"));
        modifiedInventory.addItemToInventory(
                new Item(70, "Table Lamp", "Wooden", "Antique"));

        List<Item> replacementItems = new ArrayList<>();
        replacementItems.add(new Item(10, "Earrings", "Gold", "Sparkly", "Fancy"));
        replacementItems.add(new Item(70, "Table Lamp", "Wooden", "Antique"));
        testInventory.setInventoryItems(replacementItems);

        assertEquals(modifiedInventory.getItems(), testInventory.getItems());
    }

    @Test
    void cloneTest() {
        Inventory clonedInventory = testInventory.clone();
        assertEquals(testInventory.getName(), clonedInventory.getName());
        assertEquals(testInventory.getItems(), clonedInventory.getItems());
    }
}