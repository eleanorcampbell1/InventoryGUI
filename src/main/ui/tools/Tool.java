package ui.tools;

import ui.DepartmentStoreGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.net.URL;

// an abstract Tool
public abstract class Tool {
    protected JButton button;
    protected DepartmentStoreGUI gui;
    private boolean active;

    public Tool(DepartmentStoreGUI gui, JComponent parent) {
        this.gui = gui;
        createButton(parent);
        addToParent(parent);
        active = false;
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // getters
    public boolean isActive() {
        return active;
    }

    // EFFECTS: sets this Tool's active field to true
    public void activate() {
        active = true;
    }

    // EFFECTS: sets this Tool's active field to false
    public void deactivate() {
        active = false;
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton(JComponent parent);

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addTextFieldToParent(JComponent parent, JTextField textField) {
        parent.add(textField);
    }

    // REQUIRES: imageFileName is a valid file name
    // EFFECTS: creates a JOptionPane that informs you that the item has been removed from inventory
    protected void createPopUpPanel(String imageFileName, String message) {
        URL imageURL = Tool.class.getClassLoader().getResource(imageFileName);
        ImageIcon savedPopUp = new ImageIcon(imageURL);
        Image image = savedPopUp.getImage();

        Image newImage = image.getScaledInstance(190, 170, java.awt.Image.SCALE_SMOOTH);
        savedPopUp = new ImageIcon(newImage);

        JOptionPane.showMessageDialog(null, message,
                "Display Image", JOptionPane.INFORMATION_MESSAGE, savedPopUp);
    }

    // MODIFIES: listScrollPane
    // EFFECTS: updates the selected item in listScrollPane
    protected void updateListSelection() {
        ListSelectionEvent lse = new ListSelectionEvent(gui.getList(),
                0, 0, true);
        gui.valueChanged(lse);
    }

    // MODIFIES: listScrollPane
    // EFFECTS: resets the selected item in listScrollPane
    protected void resetListSelection() {
        ListSelectionEvent lse = new ListSelectionEvent(gui.getList(),
                0, 0, true);
        gui.getList().setSelectedIndex(0);
        gui.valueChanged(lse);
    }

}
