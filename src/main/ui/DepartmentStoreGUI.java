package ui;

import model.Event;
import model.EventLog;
import model.Inventory;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DepartmentStoreGUI extends JFrame implements ListSelectionListener, WindowListener {
    private final int width = 750;
    private final int height = 450;
    JLabel itemIDHeader = new JLabel("Item Information");
    JLabel inventoryListTitle = new JLabel("Store Inventory");
    JLabel itemID = new JLabel();
    JList list;
    String[] itemNames;
    JSplitPane splitPane;
    private List<Tool> tools = new ArrayList<>();
    private Tool activeTool;
    protected JScrollPane itemScrollPane;
    JScrollPane listScrollPane;
    ListModel<String> model;

    JPanel priceCheckArea;
    JPanel toolArea;

    private Scanner input = new Scanner(System.in);
    protected Inventory inventory;
    private String inventoryStore;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String JSON_STORE = "./data/inventory.json";

    // Code below attributed to Oracle Java Doc "Using Swing Components: Examples - SplitPaneDemo", 1995, 2008
    // (https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html)
    // and Lazic B. on YouTube (https://www.youtube.com/watch?v=XMIvwWRrgig)

    // sets up two scroll panes
    public DepartmentStoreGUI(String inventoryFileName) {
        super("Department Store App");
        initiatePersistence(inventoryFileName);
        itemNames = inventory.getItemNamesFromInventory();

        list = new JList();
        model = makeListModel();
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        listScrollPane = new JScrollPane(list);
        setColumnHeader(listScrollPane, inventoryListTitle);

        itemScrollPane = new JScrollPane(itemID);
        setColumnHeader(itemScrollPane, itemIDHeader);

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(width, height));
        createTools();
        createPriceCheckTool();
        addToolContainer();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, itemScrollPane);
        splitPane.setDividerLocation(350);
        add(splitPane, BorderLayout.CENTER);
        addWindowListener(this);
        updateLabel(itemNames[list.getSelectedIndex()]);
    }

    
    // MODIFIES: scrollPane
    // EFFECTS: creates viewport and formats label for given ScrollPane using given JLabel;
    private void setColumnHeader(JScrollPane scrollPane, JLabel label) {
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setHorizontalAlignment(JLabel.CENTER);
        scrollPane.setColumnHeaderView(label);
    }

    // MODIFIES: this, inventoryStore, input, inventory, jsonWriter, jsonReader
    // EFFECTS: initializes inventoryStore, input, inventory, jsonWriter, jsonReader;
    //          loads inventory from file
    private void initiatePersistence(String inventoryFileName) {
        inventoryStore = inventoryFileName;
        input = new Scanner(System.in);
        inventory = new Inventory("Default Inventory");
        jsonWriter = new JsonWriter(inventoryStore);
        jsonReader = new JsonReader(inventoryStore);
        reloadInventory();
    }

    // MODIFIES: model
    // EFFECTS: assigns a new ListModel to model and implements the AbstractListModel
    private ListModel<String> makeListModel() {
        model = new AbstractListModel() {
            @Override
            public int getSize() {
                return inventory.getItems().size();
            }

            @Override
            public Object getElementAt(int index) {
                return inventory.getItems().get(index).getName();
            }
        };
        return model;
    }
    
    // MODIFIES: this
    // EFFECTS: sets the given tool as the activeTool
    public void setActiveTool(Tool tool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        tool.activate();
        activeTool = tool;
    }

    // MODIFIES: this
    // EFFECTS: adds priceCheckArea and toolArea to the gui
    private void addToolContainer() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setSize(new Dimension(0, 0));
        add(container, BorderLayout.SOUTH);

        container.add(priceCheckArea);
        container.add(toolArea);
    }

    // MODIFIES: priceCheckArea
    // EFFECTS: a helper method that declares and instantiates the PriceCheck tool
    private void createPriceCheckTool() {
        priceCheckArea = new JPanel();
        priceCheckArea.setLayout(new GridLayout(0, 2));
        priceCheckArea.setSize(new Dimension(0, 0));

        PriceCheckTool priceCheckTool = new PriceCheckTool(this, priceCheckArea);
        tools.add(priceCheckTool);

        setActiveTool(priceCheckTool);
    }

    // MODIFIES: toolArea
    // EFFECTS:  a helper method which declares and instantiates all tools
    private void createTools() {
        toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(0, 0));

        AddTool addTool = new AddTool(this, toolArea);
        tools.add(addTool);

        ReloadTool reloadTool = new ReloadTool(this, toolArea);
        tools.add(reloadTool);

        RemoveTool removeTool = new RemoveTool(this, toolArea);
        tools.add(removeTool);

        SaveTool saveTool = new SaveTool(this, toolArea);
        tools.add(saveTool);

        setActiveTool(addTool);
    }

    // MODIFIES: itemScrollPane
    // EFFECTS: called when a new item is selected in listScrollPane
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList)e.getSource();
        updateLabel(inventory.getItemNamesFromInventory()[Math.max(0, list.getSelectedIndex())]);
    }

    // MODIFIES: itemID
    // EFFECTS: updates the item's label in the itemScrollPane
    private void updateLabel(String itemName) {
        String text = createItemID(itemName);
        itemID.setText(text);
    }

    // EFFECTS: displays the selected item's name, price and attributes
    private String createItemID(String itemName) {
        Item item = inventory.viewItem(itemName);

        float priceFloat = item.getPrice();
        String priceString = String.format("%.02f", priceFloat);

        String itemIDText = "<html><b>Name: </b>" + item.getName() + "<br/><br/><br/>";
        itemIDText = itemIDText + "<b>Price:</b> $" + priceString + "<br/><br/><br/>";
        itemIDText = itemIDText + "<b>Attributes:</b> <br/>";

        for (String attribute : item.getAttributes()) {
            itemIDText = itemIDText + "• " + attribute + "<br/>";
        }
        itemIDText = itemIDText + "<html>";

        return itemIDText;

    }

    // getters
    public JLabel getItemID() {
        return itemID;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public JList getList() {
        return list;
    }

    // MODIFIES: this
    // EFFECTS: loads inventory from file
    public void reloadInventory() {
        try {
            inventory = jsonReader.read();
            //System.out.println("Reloaded " + inventory.getName() + " from " + inventoryStore);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + inventoryStore);
        }
    }

    // EFFECTS: saves the inventory to file
    public void saveInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            System.out.println("Saved " + inventory.getName() + " to " + inventoryStore);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + inventoryStore);
        }
    }

    public static void main(String[] args) {
        DepartmentStoreGUI splitPane = new DepartmentStoreGUI(JSON_STORE);
        splitPane.validate();
        splitPane.setLocationRelativeTo(null);
        splitPane.setVisible(true);
        splitPane.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
