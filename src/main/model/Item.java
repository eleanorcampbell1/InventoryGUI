package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Represents a product sold by the store, having a price (in dollars), name and attributes.
public class Item implements Writable {

    private float price;                                      // item price
    private String name;                                     // item name
    private List<String> attributes = new ArrayList<>();    // item attributes


    // REQUIRES: itemName is of non-zero length and itemPrice > 0
    // EFFECTS: create a new Item, price gets itemPrice; name gets itemName; attributes gets attributes
    public Item(float itemPrice, String itemName, String... attributes) {
        price = itemPrice;
        name = itemName;
        this.attributes.addAll(Arrays.asList(attributes));
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    // lines 32-36 from code on JavaTPoint: https://www.javatpoint.com/java-string-equals

    // REQUIRES: anObject be an Object
    // EFFECTS: Compares two objects based on equality and not identity, returns true if so, false otherwise
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Item) {
            Item anotherItem = (Item) anObject;
            return anotherItem.getName().equalsIgnoreCase(getName());
        }
        return false;
    }

    // EFFECT: returns a string representation of an Item
    public String toString() {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return name + " - $" + numberFormat.format(price) + " " + attributes;
    }

    // EFFECTS: returns true if the given attribute is found in the Item
    public boolean hasAttribute(String attribute) {
        return attributes.contains(attribute);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("price", price);
        json.put("name", name);
        json.put("attributes", attributes);
        return json;
    }
}
