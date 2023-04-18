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

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel() {
        game = new Game();
        game.start();

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
        game.addTower(tower);
        repaint();
    }

}