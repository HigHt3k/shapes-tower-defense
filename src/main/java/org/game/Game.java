package org.game;

import org.game.model.building.tower.Tower;
import org.game.model.enemy.Enemy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private List<Tower> towers;
    private List<Enemy> enemies;
    private boolean running;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int ENEMY_SPAWN_MARGIN = 50;
    private Random random;

    private boolean waveInProgress;
    private Timer gameLoopTimer;

    public Game() {
        towers = new ArrayList<>();
        enemies = new ArrayList<>();
        running = false;
        random = new Random();

        waveInProgress = false;

        // Set up the game loop timer
        gameLoopTimer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }

    public void start() {
        running = true;
        gameLoopTimer.start();
    }

    public void update() {
        if (running) {
            // Update towers and enemies
            for (Tower tower : towers) {
                tower.update(enemies);
            }
            for (Enemy enemy : enemies) {
                enemy.update();
            }

            // Remove dead enemies
            enemies.removeIf(enemy -> enemy.getHealth() <= 0);

            // Check if the wave is cleared and spawn a new wave
            if (!waveInProgress && enemies.isEmpty()) {
                spawnWave(10);
                waveInProgress = true;
            } else if (waveInProgress && enemies.isEmpty()) {
                waveInProgress = false;
            }
        }
    }

    private void draw() {
        // Implement your rendering logic here
    }

    private void sleep() {
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addTower(Tower tower) {
        towers.add(tower);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void spawnEnemy() {
        int side = random.nextInt(4);
        int x, y;

        switch (side) {
            case 0: // Top border
                x = random.nextInt(SCREEN_WIDTH - 2 * ENEMY_SPAWN_MARGIN) + ENEMY_SPAWN_MARGIN;
                y = random.nextInt(ENEMY_SPAWN_MARGIN);
                break;
            case 1: // Right border
                x = SCREEN_WIDTH - random.nextInt(ENEMY_SPAWN_MARGIN);
                y = random.nextInt(SCREEN_HEIGHT - 2 * ENEMY_SPAWN_MARGIN) + ENEMY_SPAWN_MARGIN;
                break;
            case 2: // Bottom border
                x = random.nextInt(SCREEN_WIDTH - 2 * ENEMY_SPAWN_MARGIN) + ENEMY_SPAWN_MARGIN;
                y = SCREEN_HEIGHT - random.nextInt(ENEMY_SPAWN_MARGIN);
                break;
            default: // Left border
                x = random.nextInt(ENEMY_SPAWN_MARGIN);
                y = random.nextInt(SCREEN_HEIGHT - 2 * ENEMY_SPAWN_MARGIN) + ENEMY_SPAWN_MARGIN;
                break;
        }

        // Create a new enemy and add it to the list
        Enemy enemy = new Enemy(x, y, 100, 2, 0, 10, 100, enemies);
        enemies.add(enemy);
    }

    public void spawnWave(int enemyCount) {
        for (int i = 0; i < enemyCount; i++) {
            spawnEnemy();
        }
    }

    public Timer getGameLoopTimer() {
        return gameLoopTimer;
    }
}
