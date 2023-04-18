package org.game.model.building.tower;

import org.game.model.Projectile;
import org.game.model.enemy.Enemy;

public class BasicTower extends Tower {
    public BasicTower(int x, int y) {
        super(x, y, 100, 50, 50, 30,20, 20, 0.1, 0.1, 0.1);
    }

    @Override
    protected void shoot(Enemy target) {
        Projectile projectile = new Projectile(x, y, projectileSpeed, damage, target);
        projectiles.add(projectile);
    }
}