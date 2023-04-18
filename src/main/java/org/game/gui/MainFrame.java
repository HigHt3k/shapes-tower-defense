package org.game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private TowerMenu towerMenu;
    private GamePanel gamePanel;

    public MainFrame() {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout());

        towerMenu = new TowerMenu();
        gamePanel = new GamePanel();

        add(towerMenu, BorderLayout.WEST);
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (towerMenu.getSelectedTower() != null) {
                    gamePanel.addTower(e.getX(), e.getY(), towerMenu.getSelectedTower());
                }
            }
        });
    }
}