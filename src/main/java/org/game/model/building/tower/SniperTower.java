package org.game.model.building.tower;

import org.game.model.Projectile;
import org.game.model.enemy.Enemy;

public class SniperTower extends Tower {
    public SniperTower(int x, int y) {
        super(x, y, 500, 200, 200,120, 100,100, 0.15, 0.2, 0.3);
    }

    @Override
    protected void shoot(Enemy target) {
        target.takeDamage(damage);
    }
}