package com.nathan.game.entity.mob;

import java.util.List;
import java.util.Random;

import com.nathan.game.entity.Entity;
import com.nathan.game.entity.items.potions.HealthPotion;
import com.nathan.game.entity.items.potions.ManaPotion;
import com.nathan.game.entity.items.weapons.Staff;
import com.nathan.game.entity.projectile.WizardProjectile;
import com.nathan.game.graphics.AnimatedSprite;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.graphics.Spritesheet;
import com.nathan.game.util.Vector2i;

public class Shooter extends Mob {

	
	private AnimatedSprite down = new AnimatedSprite(Spritesheet.goblin_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(Spritesheet.goblin_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(Spritesheet.goblin_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(Spritesheet.goblin_right, 32, 32, 3);
 
	private AnimatedSprite animSprite = down;

	private AnimatedSprite down1 = new AnimatedSprite(Spritesheet.player_down, 32, 32, 3);
	private AnimatedSprite up1 = new AnimatedSprite(Spritesheet.player_up, 32, 32, 3);
	private AnimatedSprite left1 = new AnimatedSprite(Spritesheet.player_left, 32, 32, 3);
	private AnimatedSprite right1 = new AnimatedSprite(Spritesheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite1 = down1;
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	private int fireRate = 0;
	public double damage = 5;
	private int originalX, originalY;
	private Entity rand = null;
	private int option;
	
	public Shooter(int x, int y, int option) {
		this.x = x << 4;
		this.y = y << 4;
		
		this.xpReward = 40;
		
		originalX = this.x;
		originalY = this.y;
		
		sprite = Sprite.goblin_down;
		this.option = option;
		if(option == 1) {
			down = down1;
			up = up1;
			left = left1;
			right = right1;

			animSprite = down;

			this.xpReward = 80;
			this.health = 200;
		}
	}
	public void update() {
		if(health <= 0.0) {
			//level.add(new HealthPotion(x, y));
			Random random = new Random();
			int temp = random.nextInt(101);
			if(temp >= 0 && temp < 40) {
				level.add(new HealthPotion(x, y));
			} else if(temp > 39 && temp < 79) {
				level.add(new ManaPotion(x, y));
			} else {
				level.add(new Staff(x, y, "Weak Staff", 2));
			}

			int[] tempCoords = level.randomSpawn();
			level.add(new Shooter(tempCoords[0], tempCoords[1], this.option));
			//level.getClientPlayer().grantXp(this.xpReward);
			remove();
		}
		time++;
		if(fireRate > 0) fireRate--;
		if(time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if(random.nextInt(5) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
		if(ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		}
		else if(ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		else if(xa > 0) {
			animSprite = left;
			dir = Direction.RIGHT;
		}
		else if(xa < 0) {
			animSprite = right;
			dir = Direction.LEFT;
		}
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		shootRandom();
	}
	
	private void shootRandom() {
		if(fireRate <= 0) {
			List<Entity> entities = level.getEntities(this, 500);
			entities.add(level.getClientPlayer());
			if (time % (30 + random.nextInt(91)) == 0) {
				int index = random.nextInt(entities.size());
				rand = entities.get(index);
			}

			if (rand != null) {
				double dx = rand.getX() - x;
				double dy = rand.getY() - y;
				double dir = Math.atan2(dy, dx);
				shoot_mob(x, y, dir, this, damage);
				fireRate = 50;
			}	
		}	
	}
	
	
	private void shootClosest() {
		if(fireRate <= 0) {
			List<Entity> entities = level.getEntities(this, 50);
			entities.add(level.getClientPlayer());
			
			double min = 0;
			Entity closest = null;
			
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				double distance = Vector2i.getDistance(new Vector2i(x, y), new Vector2i(e.getX(), e.getY()));
				if( i == 0 || distance < min) {
					min = distance;
					closest = e;
				}
			}
			
			if(closest != null) {
				double dx = closest.getX() - x;
				double dy = closest.getY() - y;
				double dir = Math.atan2(dy, dx);
		
				shoot_mob(x, y, dir, this, damage);
				fireRate = WizardProjectile.FIRE_RATE;
			}
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}

}
