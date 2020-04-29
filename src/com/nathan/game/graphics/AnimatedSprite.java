package com.nathan.game.graphics;

public class AnimatedSprite extends Sprite{

	private int frame = 0;
	private Sprite sprite;
	private int rate = 5;
	private int time = 0;
	private int length = -1;
	
	public AnimatedSprite(Spritesheet sheet, int width, int height, int length) {
		super(sheet, width, height, icon);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if (length > sheet.getSprites().length) System.err.println("Error! Length of animation is too long!");
	}
	
	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= length - 1) frame = 0;
			else frame++;
			sprite = sheet.getSprites()[frame];
		}
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setFrameRate(int frames) {
		rate = frames;
	}

	public void setFrame(int i) {
		if(i > sheet.getSprites().length - 1) {
			System.err.println("Index out of bounds in " + this);
		}
		sprite = sheet.getSprites()[i];
	}
}
