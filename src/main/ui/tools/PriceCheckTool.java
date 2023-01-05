package ui.tools;

import model.Inventory;
import ui.DepartmentStoreGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

// Tool that filters inventory in listScrollPane based on user input
public class PriceCheckTool extends Tool {
    JTextField textField;
    Inventory originalInventory;
    boolean initialButtonPush = true;
    String message = "\nFiltering the inventory based on you inputs..."
            + "\nTo return to original inventory, clear text field and push Price Check button again.";

    public PriceCheckTool(DepartmentStoreGUI gui, JComponent parent) {
        super(gui, parent);
        textField = new JTextField();
        addTextFieldToParent(parent, textField);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a price check button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Price Check");
        customizeButton(button);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an ActionListener which is then added to this object's JButton
    @Override
    protected void addListener() {
        button.addActionListener(new PriceCheckTool.PriceCheckToolClickHandler());
    }

    private class PriceCheckToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the PriceCheck Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = textField.getText();
            if (initialButtonPush) {
                originalInventory = gui.getInventory().clone();
            }
            if (input.isEmpty()) {
                gui.getInventory().setInventoryItems(originalInventory.getItems());
                initialButtonPush = true;
            } else {
                initialButtonPush = false;
                String[] attributesInputArray = input.split(", ");
                List<String> attributeInputList = Arrays.asList(attributesInputArray);

                gui.getInventory().setInventoryItems(originalInventory.searchInventoryByAttributes(attributeInputList));
                JOptionPane.showMessageDialog(null,
                        message);
            }
            gui.getList().updateUI();
            gui.getItemID().updateUI();
            resetListSelection();
        }
    }
}
