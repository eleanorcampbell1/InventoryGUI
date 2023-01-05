package ui.tools;

import ui.DepartmentStoreGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Tool that removes the selected item from listScrollPane
public class RemoveTool extends Tool {

    private String removedImageURL = "pics/pixelremoved.png";
    private String errorImageURL = "pics/redx.png";

    public RemoveTool(DepartmentStoreGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a remove button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Remove");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an ActionListener which is then added to this object's JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            // EFFECTS: sets active tool to the Remove Tool
            //          called by the framework when the tool is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = gui.getList().getSelectedIndex();
                if (index >= 0) {
                    String itemName = (String) gui.getList().getModel().getElementAt(index);
                    gui.getInventory().removeInventoryItem(itemName);
                    gui.getList().updateUI();
                    resetListSelection();
                    createPopUpPanel(removedImageURL,
                            "Item has been removed from Inventory.");
                } else {
                    createPopUpPanel(errorImageURL,
                            "You have not selected an item to delete.");
                }
            }
        });
    }
}
