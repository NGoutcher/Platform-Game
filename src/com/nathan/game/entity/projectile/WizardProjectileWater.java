package com.nathan.game.entity.projectile;

import com.nathan.game.entity.Entity;
import com.nathan.game.entity.mob.Mob;
import com.nathan.game.entity.spawner.ParticleSpawner;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.sound.EffectPlayer;

public class WizardProjectileWater extends Projectile {
	
	public static final int FIRE_RATE = 10;
	public static final int type = 1;
	public Mob shooter = null;
	public double damage = 5;
	public int radius = 200;
	
	public WizardProjectileWater(double x, double y, double dir, Mob from, double damage, int playerX, int playerY) {
		super(x, y, dir);
		range = 200;
		speed = 2;
		this.damage = damage;
		this.shooter = from;
		sprite = Sprite.rotate(Sprite.projectile_wizard_water, angle);
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		
		int ex = (int) from.getX();
		int ey = (int) from.getY();
		try {
		
		int dx = Math.abs(playerX - ex);
		int dy = Math.abs(playerY - ey);
		
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		
		if(distance <= radius) {
			EffectPlayer shoot = new EffectPlayer(distance, radius, "Explosion5");
		}
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}

	private int time = 0;
	
	public void update() {
		if(level.tileCollision((int)(x + nx),(int)( y + ny), 8, 2, 2)) {
			level.add(new ParticleSpawner((int)x, (int)y, 44, 20, level, type));
			int ex = (int) x;
			int ey = (int) y;
			
			int x = (int) level.getClientPlayer().getX();
			int y = (int) level.getClientPlayer().getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));

			EffectPlayer collision = new EffectPlayer(distance, radius, "Explosion10");
			remove();
		}
		if(level.mobCollision((int)(x + nx),(int)( y + ny), 8, 2, 2, shooter, damage)) {
			level.add(new ParticleSpawner((int)x, (int)y, 44, 20, level, type));
			remove();
		}
		if(level.playerCollision((int)(x + nx),(int)( y + ny), 8, 2, 2, shooter, damage)) {
			level.add(new ParticleSpawner((int)x, (int)y, 44, 20, level, type));
			remove();
		}
		time++;
		
		move();
	}
	
	protected void move() {
			x += nx;
			y += ny;
			
		if(distance() > range) remove();
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}
	public void render(Screen screen) {
		screen.renderProjectile((int)x - 12, (int)y - 2, this);
	}
	
	public Entity getType() {
		return this;
	}
}
