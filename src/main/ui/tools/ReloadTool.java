package ui.tools;

import ui.DepartmentStoreGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Tool that reloads the last saved inventory and displays it to listScrollPane
public class ReloadTool extends Tool {

    private String reloadImageURL = "pics/pixelreloaded.png";

    public ReloadTool(DepartmentStoreGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a reload button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Reload");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an ActionListener which is then added to this object's JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ReloadTool.ReloadToolClickHandler());
    }

    private class ReloadToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the Reload Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.reloadInventory();

            createPopUpPanel(reloadImageURL, "Inventory has been reloaded from file.");

            gui.getList().updateUI();
            updateListSelection();
        }
    }
}
