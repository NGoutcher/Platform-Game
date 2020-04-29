package com.nathan.game.level.tile;

import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;

public class WallTile extends Tile{

	public WallTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

	public boolean solid() {
		return true;
	}
}
