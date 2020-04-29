package com.nathan.game.entity.mob.inventory;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.nathan.game.entity.items.Item;
import com.nathan.game.graphics.Screen;

public class Inventory {

	public List<Item> inventory = new ArrayList<Item>();
	public int x;
	public int y;
	
	public Inventory(int x, int y) {
		this.x = x; //<< 4;
		this.y = y; //<< 4;
	}
	public void add(Item e) {
		inventory.add(e);
	}
	
	public void removeAt(int position) {
		inventory.remove(position);
	}
	
	public int getSize() {
		return inventory.size();
	}
	
	public void render(Screen screen) {
		if(inventory != null) screen.renderInventory((int) x, (int) y, this, true);
	}
	
	public Image getIcon(int pos) {
		return inventory.get(pos).icon;
	}
	
}
