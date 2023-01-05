package ui.tools;

import model.Item;
import ui.DepartmentStoreGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Tool that adds an item to inventory based on user's inputs and displays on listScrollPane
public class AddTool extends Tool {
    private String addImageURL = "pics/pixeladded.png";
    private String errorImageURL = "pics/redx.png";

    public AddTool(DepartmentStoreGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an add button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an ActionListener which is then added to this object's JButton
    @Override
    protected void addListener() {
        button.addActionListener(new AddToolClickHandler());
    }

    private class AddToolClickHandler implements ActionListener {

        JTextField name = new JTextField();
        JTextField price = new JTextField();
        JTextField attributes = new JTextField();

        Object[] message = {"Name:", name, "Price (do not include dollar sign):",
                price, "Attributes (separate wih commas):", attributes};

        // EFFECTS: sets active tool to the Add Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showConfirmDialog(null, message, "Add Item",
                    JOptionPane.OK_CANCEL_OPTION);
            String[] attributeArray = attributes.getText().split(",");

            if (option == JOptionPane.OK_OPTION && textFieldsNotEmpty()) {
                try {
                    Item inputItem = new Item(Float.parseFloat(price.getText()), name.getText(), attributeArray);
                    gui.getInventory().addItemToInventory(inputItem);
                    updateListSelection();
                    createPopUpPanel(addImageURL, "Item has been added to Inventory.");
                } catch (NumberFormatException nfe) {
                    createPopUpPanel(errorImageURL, "That is not a valid price.");
                }
                gui.getList().updateUI();
            }
            if (option == JOptionPane.OK_OPTION && !textFieldsNotEmpty()) {
                createPopUpPanel(errorImageURL, "All fields must be filled to add an item.");
            }
            name.setText("");
            price.setText("");
            attributes.setText("");
        }

        // EFFECTS: returns true if price, name and attributes are not empty;
        //          otherwise return false
        private boolean textFieldsNotEmpty() {
            return !price.getText().isEmpty() && !name.getText().isEmpty()
                    && !attributes.getText().isEmpty();
        }
    }
}
