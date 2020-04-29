package com.nathan.game.entity.mob;

import com.nathan.game.entity.items.potions.ManaPotion;
import com.nathan.game.graphics.AnimatedSprite;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.graphics.Spritesheet;

public class Dummy extends Mob {
	
	private AnimatedSprite down = new AnimatedSprite(Spritesheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(Spritesheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(Spritesheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(Spritesheet.dummy_right, 32, 32, 3);
 
	private AnimatedSprite animSprite = down;
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	private int originalX, originalY;
	
	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		
		xpReward = 5;
		
		originalX = this.x;
		originalY = this.y;
		
		sprite = Sprite.dummy_down;
	}
	
	public void update() {
		if(health <= 0.0) {
			level.add(new ManaPotion(x, y));
			level.add(new Dummy(23, 68));
			
			remove();
		}
		time++;
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
		
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite);
	}

}
