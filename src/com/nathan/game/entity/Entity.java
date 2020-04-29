package com.nathan.game.entity;

import java.util.Random;

import com.nathan.game.entity.mob.Shooter;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.level.Level;

public class Entity {

	private boolean removed = false;
	public Level level;
	protected final Random random = new Random();
	protected int x, y;
	protected int spawn_x, spawn_y;
	protected Sprite sprite;
	
	public Entity() {	
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		level.add(this);
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		if(sprite != null) screen.renderSprite((int) x, (int) y, sprite, true);
	}
	
	public void remove() {
		
		removed = true;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	/*public void respawnShooter(int x, int y) {
		level.add(new Shooter(x, y));
	}*/
}
