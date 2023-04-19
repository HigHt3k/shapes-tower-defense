package org.game.gui;

import org.game.profile.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private TowerMenu towerMenu;
    private GamePanel gamePanel;
    private TowerInfoPanel towerInfoPanel;
    private ProfilePanel profilePanel;
    private MainMenu mainMenu;

    public MainFrame() {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout());

        towerMenu = new TowerMenu();
        gamePanel = new GamePanel();

        towerInfoPanel = gamePanel.getTowerInfoPanel();

        // Create a placeholder profile panel (update this with a real profile when creating/loading a profile)
        profilePanel = new ProfilePanel(new Profile());

        // Create the main menu
        mainMenu = new MainMenu(profilePanel);

        add(towerMenu, BorderLayout.WEST);
        add(gamePanel, BorderLayout.CENTER);
        add(towerInfoPanel, BorderLayout.EAST);
        add(profilePanel, BorderLayout.NORTH);
        add(mainMenu, BorderLayout.SOUTH);

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
