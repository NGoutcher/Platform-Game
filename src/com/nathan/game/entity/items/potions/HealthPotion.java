package com.nathan.game.entity.items.potions;

import com.nathan.game.entity.items.Item;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;

public class HealthPotion extends Item {

	public double healAmount;
	
	public HealthPotion(int x, int y) {
		super(x, y, "res/textures/icons/weapons/weakstafficon.png");
		sprite = Sprite.small_health_potion;
		
		name = "Health Potion";
		setDescription("adds 20 HP when used.");
	}

	public HealthPotion() {
		name = "Health Potion";
		setDescription("adds 20 HP when used.");
	}

	public void use() {
		owner.setHealthRegen(healAmount);
	}
	
	public void render(Screen screen) {
		screen.renderItem((int) (x - 16), (int) (y - 16), sprite);
	}
	
}
