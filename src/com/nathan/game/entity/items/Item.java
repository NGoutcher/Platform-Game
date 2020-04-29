package com.nathan.game.entity.items;

import java.awt.image.BufferedImage;

import com.nathan.game.entity.Entity;
import com.nathan.game.entity.mob.Player;

public class Item extends Entity {

	public int worth;
	public String name;
	public Player owner = null;
	public BufferedImage icon = null;

	public String iconPath;

	public int dmgMult = 1;

	private String description;

	public boolean unique = false;

	public int time = 0;

	public int despawnTime = 600;
	
	public Item(int x, int y, String iconPath) {
		this.x = x; //<< 4;
		this.y = y; //<< 4;
		this.iconPath = iconPath;
		//this.icon = sprite.getImage();
	}

	public Item() {
	}

	public int getWorth() {
		return worth;
	}

	public String getDescription(){return description;}
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public void setWorth(int worth) {
		this.worth = worth;
	}
	
	public String getName() {
		return name;
	}

	public void update() {
		if(level.tileCollision((int)(x),(int)( y), 8, 2, 2)) {

		}
		if(level.playerItemCollision((int)(x),(int)(y), 8, 2, 2, this)) {
			remove();
		}
		time++;
		if(time % despawnTime == 0) {
			System.out.println("DESPAWN Time: " + time + " despawn: " + despawnTime);
			remove();
		}
	}

	public boolean hasDmgMult() {
		return false;
	}

	public int getDmgMult() {
		return dmgMult;
	}

	public boolean isUnique() {
		return unique;
	}

	public String getIconPath() {
		return iconPath;
	}

	public boolean checkUnique(Player player) {

		for(int i =0; i < player.inventory.size(); i++) {
			if(player.inventory.get(i).getName().equals(this.getName()) && isUnique()) {
				return true;
			}
		}

		return false;
	}
}
