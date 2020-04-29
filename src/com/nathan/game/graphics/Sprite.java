package com.nathan.game.graphics;

import java.awt.image.BufferedImage;

public class Sprite {

	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private int width;
	private int height;
	protected Spritesheet sheet;
	protected static BufferedImage icon = null;
	
	//Generic Sprites
	public static Sprite grass1 = new Sprite(16, 0, 0, Spritesheet.tiles, icon);
	public static Sprite grass2 = new Sprite(16, 1, 0, Spritesheet.tiles, icon);
	public static Sprite grass3 = new Sprite(16, 2, 0, Spritesheet.tiles, icon);
	public static Sprite grass4 = new Sprite(16, 3, 0, Spritesheet.tiles, icon);
	
	public static Sprite rock1 = new Sprite(16, 0, 1, Spritesheet.tiles, icon);
	public static Sprite voidSprite = new Sprite(16, 0x000000);
	
	
	//Spawn Level Sprites
	public static Sprite spawn_grass1 = new Sprite(16, 0, 0, Spritesheet.spawn_level, icon);
	public static Sprite spawn_grass2 = new Sprite(16, 1, 0, Spritesheet.spawn_level, icon);
	public static Sprite spawn_grass3 = new Sprite(16, 2, 0, Spritesheet.spawn_level, icon);
	
	public static Sprite spawn_gravel1 = new Sprite(16, 0, 1, Spritesheet.spawn_level, icon);
	public static Sprite spawn_gravel2 = new Sprite(16, 1, 1, Spritesheet.spawn_level, icon);
	public static Sprite spawn_gravel3 = new Sprite(16, 2, 1, Spritesheet.spawn_level, icon);
	
	public static Sprite spawn_well1 = new Sprite(16, 0, 2, Spritesheet.spawn_level, icon);
	public static Sprite spawn_well2 = new Sprite(16, 1, 2, Spritesheet.spawn_level, icon);
	
	public static Sprite spawn_fence_horiz = new Sprite(16, 4, 0, Spritesheet.tiles, icon);
	public static Sprite spawn_fence_horizdownright = new Sprite(16, 6, 0, Spritesheet.tiles, icon);
	public static Sprite spawn_fence_horizdownleft = new Sprite(16, 7, 0, Spritesheet.tiles, icon);
	public static Sprite spawn_fence_downright = new Sprite(16, 6, 1, Spritesheet.tiles, icon);
	public static Sprite spawn_fence_downleft = new Sprite(16, 7, 1, Spritesheet.tiles, icon);
	public static Sprite spawn_fence_horizupright = new Sprite(16, 8, 1, Spritesheet.tiles, icon);
	public static Sprite spawn_fence_horizupleft = new Sprite(16, 9, 1, Spritesheet.tiles, icon);
	
	public static Sprite wall1 = new Sprite(16, 0, 5, Spritesheet.tiles, icon);
	public static Sprite wall2 = new Sprite(16, 1, 5, Spritesheet.tiles, icon);
	
	public static Sprite floor1 = new Sprite(16, 0, 6, Spritesheet.tiles, icon);
	
	//Player Sprites
	public static Sprite player_back = new Sprite(32, 0, 0, Spritesheet.player, icon);
	public static Sprite player_back1 = new Sprite(32, 0, 2, Spritesheet.player, icon);
	public static Sprite player_back2 = new Sprite(32, 0, 3, Spritesheet.player, icon);
	
	public static Sprite player_forward = new Sprite(32, 2, 0, Spritesheet.player, icon);
	public static Sprite player_forward1 = new Sprite(32, 2, 2, Spritesheet.player, icon);
	public static Sprite player_forward2 = new Sprite(32, 2, 3, Spritesheet.player, icon);
	
	public static Sprite player_left = new Sprite(32, 3, 0, Spritesheet.player, icon);
	public static Sprite player_left1 = new Sprite(32, 3, 1, Spritesheet.player, icon);
	public static Sprite player_left2 = new Sprite(32, 3, 2, Spritesheet.player, icon);
	
	public static Sprite player_right = new Sprite(32, 1, 0, Spritesheet.player, icon);
	public static Sprite player_right1 = new Sprite(32, 1, 1, Spritesheet.player, icon);
	public static Sprite player_right2 = new Sprite(32, 1, 2, Spritesheet.player, icon);

	public static Sprite goodMage_forward = new Sprite(32,2,0,Spritesheet.goodMage, icon);
	
	//Mob Sprites
	public static Sprite dummy_down = new Sprite(32, 0, 0, Spritesheet.dummy_down, icon);
	
	public static Sprite goblin_down = new Sprite(32, 0, 0, Spritesheet.goblin_down, icon);
	
	//Projectile Sprites
	public static Sprite projectile_wizard = new Sprite(16, 0, 0, Spritesheet.projectile_wizard, icon);
	public static Sprite projectile_wizard_water = new Sprite(16, 1, 0, Spritesheet.projectile_wizard, icon);
	
	//Particle
	public static Sprite particle_normal_red = new Sprite(3, 0xff3333);
	public static Sprite particle_normal_yellow = new Sprite(3, 0xede928);
	public static Sprite particle_normal_orange = new Sprite(3, 0xedab28);
	
	public static Sprite particle_normal_blue = new Sprite(3, 0x0055dd); 
	public static Sprite particle_normal_lightblue = new Sprite(3, 0x0088dd);
	public static Sprite particle_normal_lightlightblue = new Sprite(3, 0x00b0dd);
	
	public static Sprite particle_normal_mediumyellow = new Sprite(3, 0xffcc66);
	public static Sprite particle_normal_lightyellow = new Sprite(3, 0xffdd99);
	public static Sprite particle_normal_darkyellow = new Sprite(3, 0xffaa00);
	
	//Item Sprites
	public static Sprite small_health_potion = new Sprite(16, 0, 0, Spritesheet.potions, icon);
	public static Sprite small_mana_potion = new Sprite(16, 0, 1, Spritesheet.potions, icon);
	public static Sprite weak_staff = new Sprite(16, 0, 0, Spritesheet.weapons, icon);

	protected Sprite(Spritesheet sheet, int width, int height, BufferedImage icon) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		this.icon = icon;
	}
	
	public Sprite(int size, int x, int y, Spritesheet sheet, BufferedImage icon) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		this.icon = icon;
		load();
	}
	
	public Sprite(int width, int height, int colour, BufferedImage icon) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.icon = icon;
		setColour(colour);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height; 
	}
	
	public Sprite(int size, int colour) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}

	private void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}
	
	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}
	
	public static Sprite[] split(Spritesheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {

				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}

				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}

		return sprites;
	}
	
	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		
		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);
		
		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xFFFF0AE2;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}			
			x0 += ny_x;
			y0 += ny_y;
		}
		
		return result;
	}
	
	private static double rot_x(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}

	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}
	
	public BufferedImage getImage() {
		return this.icon;
	}
}
