package com.nathan.game.level.tile.spawn_level;

import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.level.tile.Tile;

public class SpawnWellTile extends Tile {
	public SpawnWellTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid() {
		return true;
	}
}
