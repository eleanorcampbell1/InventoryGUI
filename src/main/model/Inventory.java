package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents the store's inventory, made up of Items.
public class Inventory implements Writable {
    private String name;
    private ArrayList<Item> items;

    // EFFECT: Creates an empty inventory of given name.
    public Inventory(String name) {
        this.name = name;
        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // EFFECTS: returns number of items in this inventory
    public int numItems() {
        return items.size();
    }

    // MODIFIES: items
    // EFFECT: return true if it finds and removes Item matching given name
    public boolean removeInventoryItem(String name) {
        Item item = viewItem(name);
        EventLog.getInstance().logEvent(new Event(name + " has been removed from inventory."));
        return items.remove(item);
    }

     // MODIFIES: inventory
     // EFFECTS: produces a list of the items in inventory of the given category
    public List<Item> searchInventoryByAttributes(List<String> givenAttributes) {
        List<Item> searchResults = new ArrayList<>();
        for (Item item: items) {
            if (item.getAttributes().containsAll(givenAttributes)) {
                searchResults.add(item);
            }
        }
        return searchResults;
    }

    // MODIFIES: items
    // EFFECT: adds the given item to items (returns true if item was added and no duplicate was found)
    public boolean addItemToInventory(Item item) {
        if (viewItem(item.getName()) == null) {
            EventLog.getInstance().logEvent(new Event(item.getName() + " has been added to inventory."));
            return items.add(item);
        }
        return false;
    }

    // REQUIRES: a valid item's name
    // EFFECT: displays the item (price, name and attributes), if item is not found, return null.
    public Item viewItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    // EFFECT: returns the size of an Inventory
    public int size() {
        return items.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECT: returns items in this inventory as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : items) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns an unmodifiable list of items in the inventory
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    // EFFECT: returns an equal copy of inventory
    public Inventory clone() {
        Inventory clone = new Inventory(this.getName());
        for (Item item : this.getItems()) {
            clone.addItemToInventory(item);
        }
        return clone;
    }

    // EFFECTS: returns a list of the names of each item in inventory
    public String[] getItemNamesFromInventory() {
        List itemNames = new ArrayList();
        for (Item i : items) {
            String itemName = i.getName();
            itemNames.add(itemName);
        }
        return (String[]) itemNames.toArray(new String[itemNames.size()]);
    }

    // MODIFIES: items
    // EFFECTS: sets the inventory's items to the given itemList
    public void setInventoryItems(List<Item> itemList) {
        ArrayList<Item> arrayListItems = new ArrayList<>();
        for (Item item : itemList) {
            arrayListItems.add(item);
        }
        items = arrayListItems;
    }
}
