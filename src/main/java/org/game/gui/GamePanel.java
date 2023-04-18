package org.game.gui;

import org.game.Game;
import org.game.model.building.tower.BasicTower;
import org.game.model.building.tower.SniperTower;
import org.game.model.building.tower.SplashTower;
import org.game.model.building.tower.Tower;
import org.game.model.enemy.Enemy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel() {
        game = new Game();
        game.start();

        setupMouseListeners();

        game.getGameLoopTimer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Tower tower : game.getTowers()) {
            tower.draw(g);
        }
        for (Enemy enemy : game.getEnemies()) {
            enemy.draw(g);
        }

        drawKillCountAndMoney(g);
    }

    public void addTower(int x, int y, String towerType) {
        Tower tower;
        switch (towerType) {
            case "Basic Tower":
                tower = new BasicTower(x, y);
                break;
            case "Area Tower":
                tower = new SplashTower(x, y);
                break;
            case "Sniper Tower":
                tower = new SniperTower(x, y);
                break;
            default:
                return;
        }
        if(tower.getCost() <= game.getAvailableMoney()) {
            game.addTower(tower);
            game.removeFromMoney(tower.getCost());
        }
        repaint();
    }

    private void drawKillCountAndMoney(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        String killCountText = "Total kills: " + game.getTotalKills();
        String availableMoneyText = "Available money: " + game.getAvailableMoney();

        g.drawString(killCountText, 10, 20);
        g.drawString(availableMoneyText, 10, 40);
    }

    private void setupMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.selectTower(e.getX(), e.getY());
            }
        });

        // Example: Upgrade the selected tower with a right-click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    game.upgradeSelectedTower();
                }
            }
        });
    }
}