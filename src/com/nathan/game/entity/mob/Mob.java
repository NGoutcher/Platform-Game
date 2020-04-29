package com.nathan.game.entity.mob;

import com.nathan.game.entity.Entity;
import com.nathan.game.entity.projectile.Projectile;
import com.nathan.game.entity.projectile.WizardProjectile;
import com.nathan.game.entity.projectile.WizardProjectileWater;
import com.nathan.game.graphics.Screen;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
	protected double health = 100;
	protected double mana = 100;
	protected boolean friendly = false;
	protected double xpReward = 0;
	
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	protected Direction dir;
	
	public void move(double xa, double ya) {
		if(xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if(xa > 0) dir = Direction.RIGHT;
		if(xa < 0) dir = Direction.LEFT;
		if(ya > 0) dir = Direction.DOWN;
		if(ya < 0) dir = Direction.UP;
		
		while(xa != 0) {
			if(Math.abs(xa) > 1) {
				if(!collision(abs(xa), ya)) {
					this.x+= abs(xa);
				}
				xa-= abs(xa);
			} else {
				if(!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}

		while(ya != 0) {
			if(Math.abs(ya) > 1) {
				if(!collision(xa, abs(ya))) {
					this.y+= abs(ya);
				}
				ya-= abs(ya);
			} else {
				if(!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}	
		
		
	}
	
	private int abs(double value) {
		if(value < 0) return -1;
		return 1;
	}
	
	public abstract void update();
	
	protected void shoot(int x, int y, double dir, int button, Mob from, double damage) {
		Projectile p = null;
		if(button == 1) {
			p = new WizardProjectile(x, y, dir, this, damage, level.getClientPlayer().getX(), level.getClientPlayer().getY());
		} else if(button == 3) {
			p = new WizardProjectileWater(x, y, dir, this, damage, level.getClientPlayer().getX(), level.getClientPlayer().getY());
		}
		try {
			level.add(p);
		} catch (NullPointerException e) {
			System.out.println("Mouse button != left or right");
		}
	}
	
	protected void shoot_mob(int x, int y, double dir, Mob from, double damage) {
		Projectile p = new WizardProjectile(x, y, dir, this, damage, level.getClientPlayer().getX(), level.getClientPlayer().getY());
		level.add(p);
	}
	
	protected boolean collision(double xa, double ya) {
		boolean solid = false;
		for(int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 26 + 5) / 16;
			double yt = ((y + ya) - c / 2 * 25 + 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if(c % 2 == 0) ix = (int) Math.floor(xt);
			if(c / 2 == 0) iy = (int) Math.floor(yt);
			if(level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}
	
	public abstract void render(Screen screen);
	
	public boolean isFriendly() {
		friendly = true;
		return friendly;
	}
	
	public void setHealthDamage(double damage) {
		health -= damage;
	}
	
	public void setHealthRegen(double amount) {
		health += amount;
	}

	public void setManaRegen(double amount) {
		mana += amount;
	}
	
	public boolean isDead() {
		if(health == 0) {
			return true;
		}
		return false;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getXpReward() {
		return xpReward;
	}
}
