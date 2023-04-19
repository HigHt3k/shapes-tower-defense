package org.game.gui;

import org.game.model.building.tower.Tower;

import javax.swing.*;
import java.awt.*;

public class TowerInfoPanel extends JPanel {
    private Tower selectedTower;

    // UI Components
    private JLabel towerName;
    private JLabel towerRange;
    private JLabel towerDamage;
    private JLabel towerUpgradeCost;
    private JButton upgradeButton;

    public TowerInfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupUIComponents();
    }

    private void setupUIComponents() {
        towerName = new JLabel();
        towerRange = new JLabel();
        towerDamage = new JLabel();
        towerUpgradeCost = new JLabel();
        upgradeButton = new JButton("Upgrade Tower");

        upgradeButton.addActionListener(e -> {
            if (selectedTower != null) {
                selectedTower.upgrade();
            }
            updateTowerInfo(selectedTower);
        });

        add(towerName);
        add(towerRange);
        add(towerDamage);
        add(towerUpgradeCost);
        add(upgradeButton);
    }

    public void updateTowerInfo(Tower tower) {
        selectedTower = tower;

        if (tower != null) {
            towerName.setText("Tower: " + tower.getClass().getSimpleName());
            towerRange.setText("Range: " + tower.getRange());
            towerDamage.setText("Damage: " + tower.getDamage());
            towerUpgradeCost.setText("Upgrade Cost: " + tower.getUpgradeCost());
            upgradeButton.setEnabled(true);
        } else {
            towerRange.setText("");
            towerDamage.setText("");
            towerUpgradeCost.setText("");
            upgradeButton.setEnabled(false);
        }

        revalidate();
        repaint();
    }
}
