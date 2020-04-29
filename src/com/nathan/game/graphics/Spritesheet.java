package com.nathan.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;
	public BufferedImage image;
	
	public static Spritesheet tiles = new Spritesheet("/Textures/sheets/spritesheet.png", 256);
	public static Spritesheet spawn_level = new Spritesheet("/Textures/sheets/spawnsheet.png", 48);
	public static Spritesheet projectile_wizard = new Spritesheet("/Textures/sheets/projectiles/wizard.png", 48);
	
	public static Spritesheet player = new Spritesheet("/Textures/sheets/charactersheets/spritesheet.png", 256);
	public static Spritesheet player_down = new Spritesheet(player, 0, 0, 1, 3, 32);
	public static Spritesheet player_up = new Spritesheet(player, 2, 0, 1, 3, 32);
	public static Spritesheet player_left = new Spritesheet(player, 1, 0, 1, 3, 32);
	public static Spritesheet player_right = new Spritesheet(player, 3, 0, 1, 3, 32);
	
	public static Spritesheet priest_mob = new Spritesheet("/Textures/sheets/charactersheets/priest_sheet.png", 256);
	public static Spritesheet dummy_down = new Spritesheet(priest_mob, 0, 0, 1, 3, 32);
	public static Spritesheet dummy_up = new Spritesheet(priest_mob, 2, 0, 1, 3, 32);
	public static Spritesheet dummy_left = new Spritesheet(priest_mob, 1, 0, 1, 3, 32);
	public static Spritesheet dummy_right = new Spritesheet(priest_mob, 3, 0, 1, 3, 32);
	
	public static Spritesheet goblin = new Spritesheet("/Textures/sheets/charactersheets/goblin.png", 256);
	public static Spritesheet goblin_down = new Spritesheet(goblin, 0, 0, 1, 3, 32);
	public static Spritesheet goblin_up = new Spritesheet(goblin, 2, 0, 1, 3, 32);
	public static Spritesheet goblin_left = new Spritesheet(goblin, 1, 0, 1, 3, 32);
	public static Spritesheet goblin_right = new Spritesheet(goblin, 3, 0, 1, 3, 32);

	public static Spritesheet goodMage = new Spritesheet("/Textures/sheets/charactersheets/goodmage.png", 256);
	public static Spritesheet goodMage_down = new Spritesheet(goodMage, 0, 0, 1, 3, 32);
	public static Spritesheet goodMage_up = new Spritesheet(goodMage, 2, 0, 1, 3, 32);
	public static Spritesheet goodMage_left = new Spritesheet(goodMage, 1, 0, 1, 3, 32);
	public static Spritesheet goodMage_right = new Spritesheet(goodMage, 3, 0, 1, 3, 32);


	public static Spritesheet potions = new Spritesheet("/Textures/sheets/potions.png", 256);
	public static Spritesheet weapons = new Spritesheet("/Textures/sheets/weapons.png", 256);
	private Sprite[] sprites;
	
	public Spritesheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
	}
	
	public Spritesheet(Spritesheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if (width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	public Spritesheet(String path, int size) {
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		load();
	}

	public Sprite[] getSprites() {
		return sprites;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}
	
	private void load() {
		try {
			image = ImageIO.read(Spritesheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Failed!");
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
