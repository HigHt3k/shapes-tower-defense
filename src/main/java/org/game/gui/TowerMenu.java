package org.game.gui;

import javax.swing.*;
import java.awt.*;

public class TowerMenu extends JPanel {
    private JList<String> towerList;
    private DefaultListModel<String> towerListModel;

    public TowerMenu() {
        setPreferredSize(new Dimension(200, 600));

        towerListModel = new DefaultListModel<>();
        towerListModel.addElement("Basic Tower");
        towerListModel.addElement("Area Tower");
        towerListModel.addElement("Sniper Tower");
        towerListModel.addElement("");

        towerList = new JList<>(towerListModel);
        towerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        towerList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane listScroller = new JScrollPane(towerList);
        listScroller.setPreferredSize(new Dimension(180, 80));

        add(listScroller);
    }

    public String getSelectedTower() {
        return towerList.getSelectedValue();
    }
}