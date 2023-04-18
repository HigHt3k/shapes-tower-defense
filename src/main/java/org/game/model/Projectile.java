package org.game.model;

import org.game.model.enemy.Enemy;

import java.awt.*;

public class Projectile {

    private int x, y;
    private int speedX, speedY;
    private int damage;
    private Enemy target;

    public Projectile(int x, int y, int speed, int damage, Enemy target) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.target = target;

        // Calculate the direction vector
        int dx = target.getX() - x;
        int dy = target.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction vector and scale by speed
        speedX = (int) (dx / distance * speed);
        speedY = (int) (dy / distance * speed);
    }

    public void update() {
        x += speedX;
        y += speedY;

        // Check if the projectile hit the target
        if (Math.abs(x - target.getX()) < 5 && Math.abs(y - target.getY()) < 5) {
            target.takeDamage(damage);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x - 2, y - 2, 4, 4);
    }

    public boolean isHit() {
        return Math.abs(x - target.getX()) < 5 && Math.abs(y - target.getY()) < 5;
    }
}
