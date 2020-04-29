package com.nathan.game.entity.mob;

import java.util.List;

import com.nathan.game.graphics.AnimatedSprite;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.graphics.Spritesheet;

public class Chaser extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(Spritesheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(Spritesheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(Spritesheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(Spritesheet.dummy_right, 32, 32, 3);
 
	private AnimatedSprite animSprite = down;
	
	private double xa = 0;
	private double ya = 0;
	private double speed = 0.8;

	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy_down;
;	}
	
	private void move() {
		xa = 0;
		ya = 0;
		
		List<Player> players = level.getPlayers(this, 200);
		if(players.size() > 0) {
			Player player = players.get(0);
			
			if ((int)x < (int)player.getX()) xa+= speed;
		    if ((int)x > (int)player.getX()) xa-= speed;        
		    if ((int)y < (int)player.getY()) ya+= speed;
		    if ((int)y > (int)player.getY()) ya-= speed;
		}
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	public void update() {
		move();
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
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}
}
