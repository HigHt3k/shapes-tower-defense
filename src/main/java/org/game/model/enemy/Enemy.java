package org.game.model.enemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {
    private int x, y;
    private int health;
    private int maxHealth;
    private int speed;
    private int resistances; // Represent resistances as a bitmask
    private int damage;
    private int range;
    private int speedX;
    private int speedY;
    private Random random;

    private List<Enemy> allEnemies;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BORDER_MARGIN = 20;

    public Enemy(int x, int y, int health, int speed, int resistances, int damage, int range, List<Enemy> allEnemies) {
        this.x = x;
        this.y = y;
        this.maxHealth = health;
        this.health = health;
        this.speed = speed;
        this.resistances = resistances;
        this.damage = damage;
        this.range = range;
        this.allEnemies = allEnemies;
        this.random = new Random();

        double angle = random.nextDouble() * 2 * Math.PI;
        speedX = (int) (Math.cos(angle) * speed);
        speedY = (int) (Math.sin(angle) * speed);
    }

    public void update() {
        // Update the enemy's position
        x += speedX;
        y += speedY;
        checkScreenBounds();
    }

    public void takeDamage(int damage) {
        // Apply resistances here if necessary
        health -= damage;
        if (health <= 0) {
            // Handle enemy death (e.g., remove from the list, add points, etc.)
        }
    }

    public List<Enemy> getNeighbors(int radius) {
        List<Enemy> neighbors = new ArrayList<>();
        for (Enemy otherEnemy : allEnemies) {
            if (otherEnemy == this) {
                continue;
            }
            int deltaX = otherEnemy.getX() - x;
            int deltaY = otherEnemy.getY() - y;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance <= radius) {
                neighbors.add(otherEnemy);
            }
        }
        return neighbors;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(getX() - 5, getY() - 5, 10, 10);
    }

    private void checkScreenBounds() {
        if (x <= BORDER_MARGIN && speedX < 0) {
            speedX = -speedX;
        }
        if (x >= SCREEN_WIDTH - BORDER_MARGIN && speedX > 0) {
            speedX = -speedX;
        }
        if (y <= BORDER_MARGIN && speedY < 0) {
            speedY = -speedY;
        }
        if (y >= SCREEN_HEIGHT - BORDER_MARGIN && speedY > 0) {
            speedY = -speedY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public int getResistances() {
        return resistances;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }
}
