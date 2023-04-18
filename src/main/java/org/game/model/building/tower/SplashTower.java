package org.game.model.building.tower;

import org.game.model.Projectile;
import org.game.model.enemy.Enemy;

public class SplashTower extends Tower {
    private int splashRadius;

    public SplashTower(int x, int y) {
        super(x, y, 100, 30, 150, 60, 10, 75, 0.05, 0.05, 0.2);
        this.splashRadius = 20;
    }

    @Override
    protected void shoot(Enemy target) {
        Projectile projectile = new Projectile(x, y, projectileSpeed, damage, target);
        projectiles.add(projectile);
    }
}
