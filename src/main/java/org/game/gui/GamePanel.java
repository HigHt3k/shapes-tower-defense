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
    private JButton restartButton;
    private TowerInfoPanel towerInfoPanel;

    public GamePanel() {
        game = new Game();
        game.start();

        setupMouseListeners();
        setupRestartButton();
        towerInfoPanel = new TowerInfoPanel();
        restartButton.setVisible(false);
        add(restartButton);

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

        if(game.isGameOver()) {
            drawGameOverScreen(g);
        } else {
            for (Tower tower : game.getTowers()) {
                tower.draw(g);
            }
            for (Enemy enemy : game.getEnemies()) {
                enemy.draw(g);
            }

            drawKillCountAndMoney(g);
        }
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
                towerInfoPanel.updateTowerInfo(game.getSelectedTower());
            }
        });
    }

    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2 - 50);

        // Make the restart button visible
        restartButton.setVisible(true);
    }

    private void setupRestartButton() {
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(getWidth() / 2 - 100, getHeight() / 2, 200, 50);
        restartButton.addActionListener(e -> {
            // Reset the game
            game.reset();
            restartButton.setVisible(false);
        });
    }

    public TowerInfoPanel getTowerInfoPanel() {
        return towerInfoPanel;
    }
}