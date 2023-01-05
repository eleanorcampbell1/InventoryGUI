package ui;

import model.Inventory;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class DepartmentStoreApp {

    // code in lines 25-76, 102-209 attributed to TellerApp class in CPSC210/TellerApp;
    // lines 31-33, 79-100 attributed to WorkroomApp class in CPSC210/JsonSerializationDemo;
    // elements have been modified to fit my application.

    private Scanner input = new Scanner(System.in);
    private Inventory inventory;
    private String inventoryStore;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the department store application
    public DepartmentStoreApp(String inventoryFileName) throws  FileNotFoundException {
        inventoryStore = inventoryFileName;
        input = new Scanner(System.in);
        inventory = new Inventory("Default Inventory");
        jsonWriter = new JsonWriter(inventoryStore);
        jsonReader = new JsonReader(inventoryStore);
        reloadInventory();
    }


     // MODIFIES: this
     // EFFECT: processes user input
    public void runDepartmentStoreApp() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayCommandMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECT: processes the user commands
    private void processCommand(String command) {
        if (command.equals("pc")) {
            doPriceCheck();
        } else if (command.equals("a")) {
            doAddItem();
        } else if (command.equals("r")) {
            doRemoveItem();
        } else if (command.equals("v")) {
            doViewItem();
        } else if (command.equals("s")) {
            saveInventory();
        } else if (command.equals("rl")) {
            reloadInventory();
        } else {
            System.out.println("Sorry, this isn't a valid command. Try again!");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads inventory from file
    private void reloadInventory() {
        try {
            inventory = jsonReader.read();
            System.out.println("Reloaded " + inventory.getName() + " from " + inventoryStore);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + inventoryStore);
        }
    }

    // EFFECTS: saves the inventory to file
    private void saveInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            System.out.println("Saved " + inventory.getName() + " to " + inventoryStore);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + inventoryStore);
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayCommandMenu() {
        System.out.println("\nHello!");
        System.out.println("\nPlease select an option");
        System.out.println("\tpc - price check");
        System.out.println("\ta - add item");
        System.out.println("\tr - remove item");
        System.out.println("\tv - view item");
        System.out.println("\ts - save inventory");
        System.out.println("\trl - reload inventory from file");
        System.out.println("\tq - quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts a viewing of an item in inventory
    private void doViewItem() {
        System.out.println("Type the name of the item you would like to view.");
        String name = input.nextLine();
        Item itemLookedUp = inventory.viewItem(name);
        if (itemLookedUp == null) {
            System.out.println("There is no item of that name in the inventory.");

        } else {
            System.out.println("\nHere is the item you requested:");
            System.out.println(itemLookedUp);
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a removal of an item from inventory
    private void doRemoveItem() {
        System.out.println("Type the name of the item you would like to remove from inventory.");
        String name = input.nextLine();
        boolean removed = inventory.removeInventoryItem(name);

        if (removed) {
            System.out.println(name + " has been removed from Inventory.");
        } else {
            System.out.println("There is no item of that name in the inventory.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the addition of an item to inventory
    private void doAddItem() {
        System.out.println("Type the name of the item you would like to add to inventory.");
        String name = input.nextLine();

        System.out.println("Set the item's price.");
        float price = inputPrice();
        if (price <= 0) {
            System.out.println("That is not a valid price.");
            input.nextLine();
            return;
        }
        input.nextLine();
        System.out.println("Type the item's attributes (separate using commas).");
        String attributes = input.nextLine();
        String[] attributeArray = attributes.split(",");
        Item addedItem = new Item(price, name, attributeArray);
        boolean itemHasBeenAdded = inventory.addItemToInventory(addedItem);
        if (itemHasBeenAdded) {
            System.out.println("Your item has successfully been added to inventory.");
        } else {
            System.out.println("There is already an item of that name in the inventory.");
        }
    }

    // MODIFIES: price
    // EFFECTS: returns price = -1 if price is not a float
    private float inputPrice() {
        float price;
        try {
            price = input.nextFloat();
        } catch (InputMismatchException ex) {
            price = -1;
        }
        return price;
    }

    // MODIFIES: this
    // EFFECTS: conducts a price check
    private void doPriceCheck() {
        List<String> listOfGivenAttributes = new ArrayList<>();
        System.out.println("Type the attribute you would like to search with.");
        String latestAttribute = input.nextLine();

        while (!latestAttribute.equals("q")) {
            listOfGivenAttributes.add(latestAttribute);
            List<Item> searchedInventory = inventory.searchInventoryByAttributes(listOfGivenAttributes);
            if (searchedInventory.size() == 0) {
                System.out.println("There are no items with those attributes.");
                System.out.println("Press 'q' to quit.");
            } else {
                printSearchedInventory(searchedInventory);
                System.out.println("Enter another attribute to refine your search or type 'q' to quit.");
            }
            latestAttribute = input.nextLine();
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the given inventory
    private void printSearchedInventory(List<Item> anInventory) {
        for (Item item : anInventory) {
            System.out.println(item);
        }
    }
}