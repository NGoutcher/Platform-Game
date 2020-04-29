package com.nathan.game.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.nathan.game.util.Vector2i;

public class UIProgressBar extends UIComponent {

	private Vector2i size;
	private double progress; // 0 - 1
	
	private Color foregroundColor;
	
	public UIProgressBar(Vector2i position, Vector2i size) {
		super(position);
		this.size = size;
		
		foregroundColor = new Color(0xff00ff);
	}

	public void setForegroundColor(int color) {
		this.foregroundColor = new Color(color);
	}
	
	public void setProgress(double progress) {
		if (progress < 0.0) {
			progress = 0.0;
		}
		if(progress > 1.0) {
			progress = 1.0;
		}
			
		
		this.progress = progress;
	}
	
	public double getProgress() {
		return progress;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		
		g.setColor(foregroundColor);
		g.fillRect(position.x + offset.x, position.y + offset.y,(int) (progress * size.x), size.y);
	}
}
