package org.game.model.building.tower;

import org.game.model.Projectile;
import org.game.model.building.Building;
import org.game.model.enemy.Enemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Tower extends Building {
    protected int x, y;
    protected int range;
    protected int damage;
    protected int cost;
    protected int upgradeCost;
    protected int upgradeLevel;
    protected double upgradeDamageMultiplier;
    protected double upgradeRangeMultiplier;
    protected double upgradeCostMultiplier;
    protected double upgradeAttackSpeedMultiplier;
    protected double upgradeCriticalHitChanceMultiplier;
    protected int maxCooldown;
    protected int cooldown;
    protected int projectileSpeed;

    protected List<Projectile> projectiles;

    public Tower(int x, int y, int range, int damage, int cost, int maxCooldown, int projectileSpeed, int upgradeCost,
                 double upgradeRangeMultiplier, double upgradeDamageMultiplier, double upgradeCostMultiplier) {
        this.x = x;
        this.y = y;
        this.range = range;
        this.damage = damage;
        this.cost = cost;
        this.upgradeCost = upgradeCost;
        this.upgradeLevel = 1;
        this.upgradeRangeMultiplier = upgradeRangeMultiplier;
        this.upgradeCostMultiplier = upgradeCostMultiplier;
        this.upgradeDamageMultiplier = upgradeDamageMultiplier;
        this.maxCooldown = maxCooldown;
        this.cooldown = 0;
        this.projectileSpeed = projectileSpeed;

        projectiles = new ArrayList<>();
    }

    public void update(List<Enemy> enemies) {
        cooldown--;

        if (cooldown <= 0) {
            Enemy target = findTarget(enemies);
            if (target != null) {
                shoot(target);
                cooldown = maxCooldown;
            }
        }

        for (Projectile projectile : projectiles) {
            projectile.update();
        }

        projectiles.removeIf(Projectile::isHit);
    }

    public void upgrade() {
        upgradeLevel++;
        range += range * upgradeRangeMultiplier;
        damage += damage * upgradeDamageMultiplier;
        cost += cost * upgradeCostMultiplier;
    }

    private boolean isEnemyInRange(Enemy enemy) {
        int deltaX = enemy.getX() - x;
        int deltaY = enemy.getY() - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance <= range;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected abstract void shoot(Enemy target);

    protected Enemy findTarget(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            int dx = enemy.getX() - x;
            int dy = enemy.getY() - y;
            int distanceSquared = dx * dx + dy * dy;

            if (distanceSquared <= range * range) {
                return enemy;
            }
        }
        return null;
    }

    public void draw(Graphics g) {
        // Draw the tower
        g.setColor(Color.BLUE);
        g.fillRect(getX() - 10, getY() - 10, 20, 20);

        // Draw the projectiles
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
    }
}
