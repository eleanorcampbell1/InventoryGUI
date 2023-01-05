package ui.tools;

import ui.DepartmentStoreGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Tool that saves the inventory displayed in listScrollPane to JsonFile
public class SaveTool extends Tool {

    private String saveImageURL = "pics/pixelsave.png";

    public SaveTool(DepartmentStoreGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a move button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an ActionListener which is then added to this object's JButton
    @Override
    protected void addListener() {
        button.addActionListener(new SaveTool.SaveToolClickHandler());
    }

    private class SaveToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the Save Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.saveInventory();
            updateListSelection();
            createPopUpPanel(saveImageURL, "Inventory has been saved to file.");

        }

    }
}
