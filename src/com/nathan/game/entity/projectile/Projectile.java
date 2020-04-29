package com.nathan.game.entity.projectile;

import java.util.Random;

import com.nathan.game.entity.Entity;
import com.nathan.game.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin; 
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	protected final Random random = new Random();
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		
		this.x = x;
		this.y = y;
	}
	
	protected void move() {
		
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
}
