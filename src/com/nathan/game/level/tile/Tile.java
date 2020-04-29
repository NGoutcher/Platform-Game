package com.nathan.game.level.tile;

import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;
import com.nathan.game.level.tile.spawn_level.SpawnGrassTile;
import com.nathan.game.level.tile.spawn_level.SpawnGravelTile;
import com.nathan.game.level.tile.spawn_level.SpawnWellTile;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile grass1 = new GrassTile(Sprite.grass1);
	public static Tile grass2 = new GrassTile(Sprite.grass2);
	public static Tile grass3 = new GrassTile(Sprite.grass3);
	public static Tile grass4 = new GrassTile(Sprite.grass4);
	
	public static Tile rock1 = new RockTile(Sprite.rock1);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	public static Tile fence_horiz = new FenceTile(Sprite.spawn_fence_horiz);
	public static Tile fence_horizdownright = new FenceTile(Sprite.spawn_fence_horizdownright);
	public static Tile fence_horizdownleft = new FenceTile(Sprite.spawn_fence_horizdownleft);
	public static Tile fence_downleft = new FenceTile(Sprite.spawn_fence_downleft);
	public static Tile fence_downright = new FenceTile(Sprite.spawn_fence_downright);
	public static Tile fence_horizupright = new FenceTile(Sprite.spawn_fence_horizupright);
	public static Tile fence_horizupleft = new FenceTile(Sprite.spawn_fence_horizupleft);
	
	
	//Spawn Tiles
	public static Tile spawn_grass1 = new SpawnGrassTile(Sprite.spawn_grass1);
	public static Tile spawn_grass2 = new SpawnGrassTile(Sprite.spawn_grass2);
	public static Tile spawn_grass3 = new SpawnGrassTile(Sprite.spawn_grass3);
		
	public static Tile spawn_gravel1 = new SpawnGravelTile(Sprite.spawn_gravel1);
	public static Tile spawn_gravel2 = new SpawnGravelTile(Sprite.spawn_gravel2);
	public static Tile spawn_gravel3 = new SpawnGravelTile(Sprite.spawn_gravel3);
		
	public static Tile spawn_well1 = new SpawnWellTile(Sprite.spawn_well1);
	public static Tile spawn_well2 = new SpawnWellTile(Sprite.spawn_well2);
	
	public static Tile wall1 = new WallTile(Sprite.wall1);
	public static Tile wall2 = new WallTile(Sprite.wall2);
	
	public static Tile floor1 = new FloorTile(Sprite.floor1);
	
	
	public final static int col_spawn_grass1 = 0xFF047D11;
	public final static int col_spawn_grass2 = 0xFF046B0E;
	public final static int col_spawn_grass3 = 0xFF023D07;
	public final static int col_spawn_grass4 = 0xFF063A0A;
	public final static int col_spawn_gravel1 = 0xFF777C78;
	public final static int col_spawn_gravel2 = 0xFF367C3D;
	public final static int col_spawn_well1 = 0xFF0F103F; //unused
	public final static int col_spawn_well2 = 0xFF242899; //unused
	
	public final static int col_wall1 = 0xFFB4B490;
	public final static int col_wall2 = 0xFF7A7A61;
	
	public final static int col_fence_horiz = 0xFF663931;
	public final static int col_fence_horizdownright = 0xFF661F13;
	public final static int col_fence_horizdownleft = 0xFF45283C;
	public final static int col_fence_downleft = 0xFF441A38;
	public final static int col_fence_downright = 0xFF190915;
	public final static int col_fence_horizupright = 0xFF610915;
	public final static int col_fence_horizupleft = 0xFF60131F;
	
	public final static int col_floor1 = 0xFF7B5529;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		
	}
	
	public boolean solid() {
		return false;
	}
	//20, 65
}
